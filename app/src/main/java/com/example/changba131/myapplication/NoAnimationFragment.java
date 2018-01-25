package com.example.changba131.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

/**
 * Created by dyf on 18/1/12.
 */

public class NoAnimationFragment extends Fragment implements NoAnimationActivity.BackPressed {
    private SlidingFinishLayout slidingFinishLayout;
    private View mParent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mParent = inflater.inflate(R.layout.fragment_no_animation, container, false);
        initSlidingLayout(mParent);
        return mParent;
    }

    private void initSlidingLayout(View view) {


        slidingFinishLayout = (SlidingFinishLayout) view.findViewById(R.id.sliding);
        slidingFinishLayout.setTouchView(view.findViewById(R.id.surface));
        slidingFinishLayout.setOnSildingFinishListener(new SlidingFinishLayout.OnSlidingFinishListener() {
            @Override
            public void onSlidingFinish() {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }

            @Override
            public void setBg(boolean show) {
                ((View) slidingFinishLayout.getParent()).setBackgroundResource(R.color.colorPrimary);
            }
        });
        slidingFinishLayout.setShowMiniPlayerListener(ShowMiniManager.getListener());
        slidingFinishLayout.setOrientation(SlidingFinishLayout.ORIENTATION_TOP);
    }

    @Override
    public void onBack() {
        slidingFinishLayout.scrollFinish();
    }
}
