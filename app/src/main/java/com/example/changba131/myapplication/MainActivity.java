package com.example.changba131.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class MainActivity extends Activity implements SlidingFinishLayout.ShowMiniPlayerListener {
    private View mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FrameLayout frameLayout = new FrameLayout(this);
//        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        frameLayout.setLayoutParams(layoutParams);
        setContentView(R.layout.activity_main);
//        ViewPager viewPager = new ViewPager(this);
//        viewPager.setLayoutParams(layoutParams);
//        frameLayout.addView(viewPager);
//        MyAdapter myAdapter = new MyAdapter();
//        String[] str = {"1","2","3","4","5","6","7","8"};
//        myAdapter.setData(str);
//        viewPager.setAdapter(myAdapter);
//        viewPager.setCurrentItem(0);
        initNoAnimationButton();
        ShowMiniManager.setShowMiniPlayerListener(this);
    }

    private void initNoAnimationButton() {
        mContainer = findViewById(R.id.mini_player);
        mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoAnimationActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void showMiniPlayer() {
        startAnimation(true);
    }

    @Override
    public void closeMiniPlayer() {

    }


    private void startAnimation(boolean flag) {
        final int height = 135;
        if (flag) {
            Log.e("YYYYY:", mContainer.getTranslationY() + "");
            ObjectAnimator animatorT = ObjectAnimator.ofFloat(mContainer, "translationY", -2 * height, 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(100);
            animatorSet.setInterpolator(new LinearInterpolator());
//            animatorSet.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    super.onAnimationEnd(animation);
//                }
//
//            });
            animatorSet.play(animatorT);
            animatorSet.start();
        } else {
//            mParent.setPadding(0, 0, 0, height);
        }
    }

}
