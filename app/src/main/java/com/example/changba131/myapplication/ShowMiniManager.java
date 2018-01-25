package com.example.changba131.myapplication;

/**
 * Created by dyf on 18/1/17.
 */

public class ShowMiniManager {
    private static SlidingFinishLayout.ShowMiniPlayerListener showMiniPlayerListener = EmptyObjectUtil.createEmptyObject(SlidingFinishLayout.ShowMiniPlayerListener.class);

    public static void setShowMiniPlayerListener(SlidingFinishLayout.ShowMiniPlayerListener listener) {
        showMiniPlayerListener = listener;
    }

    public static SlidingFinishLayout.ShowMiniPlayerListener getListener() {
        return showMiniPlayerListener;
    }
}
