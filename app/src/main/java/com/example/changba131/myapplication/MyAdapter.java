package com.example.changba131.myapplication;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by dyf on 17/9/15.
 */

public class MyAdapter extends PagerAdapter {
    private String[] mTexts;

    public void setData(String[] strings) {
        mTexts = strings;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTexts == null ? 0 : mTexts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View contentView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_layout, null);
        container.addView(contentView);
        TextView textView = (TextView) contentView.findViewById(R.id.text);
        textView.setText(mTexts[position]);
        return contentView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
