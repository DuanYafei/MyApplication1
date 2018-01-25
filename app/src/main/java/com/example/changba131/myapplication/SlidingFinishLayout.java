package com.example.changba131.myapplication;

/**
 * Created by dyf on 17/8/7.
 */

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * 自定义可以滑动的FrameLayout,当我们要使用
 * 此功能的时候，需要将该Activity的顶层布局设置为SildingFinishLayout，
 * 调用setTouchView()方法来设置需要滑动的View
 * <p>
 * 使用示例
 * SildingFinishLayout mSliding = (SildingFinishLayout)findViewById(R.id.lockScreen_sliding_finish);
 * mSliding.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
 *
 * @Override public void onSildingFinish() {
 * Activity.this.finish();
 * }
 * });
 * <p>
 * *
 */
public class SlidingFinishLayout extends FrameLayout implements
        OnTouchListener {
    public static final int ORIENTATION_LEFT = 1;
    public static final int ORIENTATION_TOP = 1 << 1;

    private int mOrientation = ORIENTATION_LEFT;
    /**
     * 移动的布局
     */
    private View mMoveView;
    /**
     * 处理滑动逻辑的View
     */
    private View mTouchView;
    /**
     * 滑动的最小距离
     */
    private int mTouchSlop;
    /**
     * 按下点的X坐标
     */
    private int downX;
    /**
     * 按下点的Y坐标
     */
    private int downY;
    /**
     * 临时存储X坐标
     */
    private int tempX;
    /**
     * 临时存储Y坐标
     */
    private int tempY;

    /**
     * 滑动类
     */
    private Scroller mScroller;

    private float mScale;

    /**
     * 关闭的占高比例
     */
    private float mProportion;
    /**
     * SildingFinishLayout的宽度
     */
    private int viewWidth;
    /**
     * SildingFinishLayout的高度
     */
    private int viewHeight;
    /**
     * 记录是否正在滑动
     */
    private boolean isSliding;
    private boolean isFinish;
    private OnSlidingFinishListener onSildingFinishListener = EmptyObjectUtil.createEmptyObject(OnSlidingFinishListener.class);
    private ShowMiniPlayerListener showMiniPlayerListener = EmptyObjectUtil.createEmptyObject(ShowMiniPlayerListener.class);
    private EmptyActScrollListener emptyActScrollListener = EmptyObjectUtil.createEmptyObject(EmptyActScrollListener.class);


    public SlidingFinishLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingFinishLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
//        setTouchView(this);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
        isSliding = false;
        mScale = 0.9f;
        mProportion = 0.75f;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 获取SildingFinishLayout所在布局的父布局
            mMoveView = this;
            viewWidth = this.getWidth();
            viewHeight = this.getHeight();
        }
    }

    private float getScale(int curY) {
        return (1f - (1f - mScale) * Math.abs(curY) / (float) viewHeight);
    }

    private float getAlpha(int curY) {
        return (1f - Math.abs(curY) / ((float) viewHeight * mProportion));
    }

    /**
     * 设置OnSildingFinishListener, 在onSildingFinish()方法中finish Activity
     *
     * @param listener
     */
    public void setOnSildingFinishListener(
            OnSlidingFinishListener listener) {
        if (listener == null) {
            return;
        }
        this.onSildingFinishListener = listener;
    }

    public void setShowMiniPlayerListener(ShowMiniPlayerListener listener) {
        if (listener == null) {
            return;
        }
        this.showMiniPlayerListener = listener;
    }

    public void setEmptyActScrollListener(EmptyActScrollListener listener) {
        if (listener == null) {
            return;
        }
        this.emptyActScrollListener = listener;
        listener.changeBackground(true, false);

    }

//    /**
//     * 播放页用来改变大小
//     */
//    private ChangbaCocos2dx mChangbaCocos2dx;
//
//    public void setChangbaCocos(ChangbaCocos2dx changbaCocos) {
//        mChangbaCocos2dx = changbaCocos;
//    }

    public boolean isSliding() {
        return isSliding;
    }

    /**
     * 设置Touch的View
     *
     * @param mTouchView
     */
    public void setTouchView(View mTouchView) {
        this.mTouchView = mTouchView;
        mTouchView.setOnTouchListener(this);
    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    public View getTouchView() {
        return mTouchView;
    }

    /**
     * 滚动出界面
     */
    private void scrollOut() {
        int delta = 0, startX = 0, startY = 0, dx = 0, dy = 0;

        switch (mOrientation) {
            case ORIENTATION_LEFT:
                delta = viewWidth + mMoveView.getScrollX();
                startX = mMoveView.getScrollX();
                startY = 0;
                dx = -delta + 1;
                dy = 0;
                break;
            case ORIENTATION_TOP:
                delta = viewHeight + mMoveView.getScrollY();
                startX = 0;
                startY = mMoveView.getScrollY();
                dx = 0;
                dy = -delta + 1;
                break;
            default:
                break;

        }
        int time = 0;
        time = Math.abs(delta);
        if (mOrientation == SlidingFinishLayout.ORIENTATION_TOP) {
            time = 500;
        }
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        mScroller.startScroll(startX, startY, dx, dy, time);
        postInvalidate();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (showMiniPlayerListener != null && !hasShowMini) {
                    hasShowMini = true;
                    showMiniPlayerListener.showMiniPlayer();
                }
            }
        }, 200);
    }

    boolean hasShowMini = false;

    public void scrollFinish() {
        onSildingFinishListener.setBg(true);
        hasSetBg = true;
        isSliding = true;
        isFinish = true;
        scrollOut();
//        if (isTouchOnAbsListView()) {
//            if (mChangbaCocos2dx == null) {
//                return;
//            }
//            View view = ((AbsListView) mTouchView).getChildAt(0);
//            if (view == null) {
//                return;
//            }
//            if (!isListOnFirstItem() || view.getTop() < -viewWidth / 2) {
//                removeCocosView();
//            }
//        }
    }

    private void removeCocosView() {
//        if (mChangbaCocos2dx != null) {
//            mChangbaCocos2dx.onPauseForPlayer();
//            ViewParent parent = mChangbaCocos2dx.getGLSurfaceView().getParent();
//            if (parent instanceof ViewGroup) {
//                ((ViewGroup) parent).removeView(mChangbaCocos2dx.getGLSurfaceView());
//            }
//        }
    }

    private boolean isListOnFirstItem() {
        if (isTouchOnAbsListView()) {
            return ((AbsListView) mTouchView).getFirstVisiblePosition() == 0;
        } else {
            return true;
        }
    }

    /**
     * 滚动到起始位置
     */
    private void scrollOrigin() {
        int delta = 0, startX = 0, startY = 0, dx = 0, dy = 0;
        switch (mOrientation) {
            case ORIENTATION_LEFT:
                delta = mMoveView.getScrollX();
                startX = mMoveView.getScrollX();
                startY = 0;
                dx = -delta;
                dy = 0;
                break;
            case ORIENTATION_TOP:
                delta = mMoveView.getScrollY();
                startX = 0;
                startY = mMoveView.getScrollY();
                dx = 0;
                dy = -delta;
                break;
            default:
                break;

        }
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        mScroller.startScroll(startX, startY, dx, dy, Math.abs(delta));
        postInvalidate();
    }

    /**
     * touch的View是否是AbsListView， 例如ListView, GridView等其子类
     *
     * @return
     */
    private boolean isTouchOnAbsListView() {
        return mTouchView instanceof AbsListView;
    }

    /**
     * touch的view是否是ScrollView或者其子类
     *
     * @return
     */
    private boolean isTouchOnScrollView() {
        return mTouchView instanceof ScrollView;
    }

    private boolean hasSetBg;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = tempX = (int) event.getRawX();
                downY = tempY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();
//                KTVLog.d("downY=" + downY + " ,moveY = " + moveY);
                int deltaX = tempX - moveX;
                int deltaY = tempY - moveY;
                tempX = moveX;
                tempY = moveY;
                boolean shouldScroll = false;
                switch (mOrientation) {
                    case ORIENTATION_LEFT:
                        if (moveX - downX > mTouchSlop
                                && Math.abs(moveY - downY) < mTouchSlop) {
                            if (moveX - downX < 0) {
                                break;
                            }
                            isSliding = true;
                        }
                        if (moveX - downX >= 0) {
                            shouldScroll = true;
                        }
                        deltaY = 0;
                        break;
                    case ORIENTATION_TOP:
                        if (downY < viewHeight / 3 &&
                                moveY - downY > mTouchSlop
                                && Math.abs(moveX - downX) < mTouchSlop) {
                            if (isTouchOnAbsListView()) {
                                View view = ((AbsListView) mTouchView).getChildAt(0);
                                isSliding = view == null || isListOnFirstItem() && view.getTop() == 0;
                            } else {
                                isSliding = true;
                            }
                        }
                        if (moveY - downY >= 0) {
                            shouldScroll = true;
                            if (isSliding) {
                                if (!hasSetBg) {
                                    onSildingFinishListener.setBg(true);
                                    hasSetBg = true;
                                }
                                float scale = getScale(moveY);
                                float alpha = getAlpha(moveY);
                                changeScaleAlpha(scale, alpha);
                                emptyActScrollListener.changeBackground(false, false);

                                if (moveY > viewHeight * mProportion) {
                                    event.setAction(MotionEvent.ACTION_UP);
                                    onTouch(v, event);
                                }
                            }
                        }
                        deltaX = 0;
                        break;
                    default:
                        break;
                }
                if (isSliding) {
                    // 若touchView是AbsListView，
                    // 则当手指滑动，取消item的点击事件，不然我们滑动也伴随着item点击事件的发生
                    if (isTouchOnAbsListView()) {
                        MotionEvent cancelEvent = MotionEvent.obtain(event);
                        cancelEvent
                                .setAction(MotionEvent.ACTION_CANCEL
                                        | (event.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                        v.onTouchEvent(cancelEvent);
                    }
                    if (shouldScroll) {
                        mMoveView.scrollBy(deltaX, deltaY);
                    }
                    // 屏蔽在滑动过程中ListView ScrollView等自己的滑动事件
                    if (isTouchOnScrollView() || isTouchOnAbsListView()) {
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isSliding) {
                    isSliding = false;
                    //滑动关闭的界限
                    if (mMoveView.getScrollX() <= -viewWidth / 4 || mMoveView.getScrollY() <= -viewHeight / 6) {
                        isFinish = true;
                        scrollOut();
                    } else {
                        scrollOrigin();
                        isFinish = false;
                    }
                }
                break;
            default:
                break;
        }

        // 假如touch的view是AbsListView或者ScrollView 我们处理完上面自己的逻辑之后
        // 再交给AbsListView, ScrollView自己处理其自己的逻辑
        if (isTouchOnScrollView() || isTouchOnAbsListView()) {
            return v.onTouchEvent(event);
        }

        // 其他的情况直接返回true
        return true;
    }

    private void changeScaleAlpha(float scale, float alpha) {
//        this.setAlpha(alpha);
//        this.setScaleX(scale);
//        this.setScaleY(scale);
//        if (mChangbaCocos2dx != null) {
//            mChangbaCocos2dx.resetGLView((int) (viewWidth * scale), (int) (viewWidth * scale));
//            mChangbaCocos2dx.getGLSurfaceView().setAlpha(alpha);
//        }
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            int currY = mScroller.getCurrY();
            int tmpY = Math.abs(currY);
            mMoveView.scrollTo(currX, currY);
            postInvalidate();
            if (mOrientation == ORIENTATION_TOP) {
                float scale = getScale(currY);
                float alpha = getAlpha(currY);
                changeScaleAlpha(scale, alpha);

                emptyActScrollListener.changeBackground(false, false);

                if (tmpY > viewHeight * mProportion) {
                    if (!hasShowMini) {
                        hasShowMini = true;
                        showMiniPlayerListener.showMiniPlayer();
                    }
                    mMoveView.setVisibility(GONE);
//                    removeCocosView();
                    mScroller.forceFinished(true);
                }
            }
            if (mScroller.isFinished()) {
                //播放页ScrollOrigin回去要显示背景
                if (tmpY < viewHeight / 2) {
                    emptyActScrollListener.changeBackground(true, false);
                }
                finish();
            }
        }
    }

    private void finish() {
        if (isFinish) {
            onSildingFinishListener.onSlidingFinish();
            emptyActScrollListener.changeBackground(false, true);
//            releaseListener();
        }
    }

    private void releaseListener() {
        onSildingFinishListener = null;
        emptyActScrollListener = null;
        showMiniPlayerListener = null;
    }


    public interface OnSlidingFinishListener {
        /**
         * 滑动结束
         */
        void onSlidingFinish();

        void setBg(boolean show);
    }

    public interface ShowMiniPlayerListener {
        /**
         * 通知显示MiniPlayer
         */
        void showMiniPlayer();

        /**
         * 通知关闭MiniPlayer
         */
        void closeMiniPlayer();
    }

    public interface EmptyActScrollListener {

        void changeBackground(boolean show, boolean finish);

    }

}
