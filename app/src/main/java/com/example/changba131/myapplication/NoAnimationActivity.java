package com.example.changba131.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.testlib.swipebackparent.MySwipeBackActivity;


public class NoAnimationActivity extends MySwipeBackActivity {

    BackPressed backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_animation);
        NoAnimationFragment fragment = new NoAnimationFragment();
        backPressed = fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_content, fragment)
                .commit();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
    }


    @Override
    public void onBackPressed() {
        backPressed.onBack();
//        finish();
    }

    interface BackPressed {
        void onBack();
    }
}
