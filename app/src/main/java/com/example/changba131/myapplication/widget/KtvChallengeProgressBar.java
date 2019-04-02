package com.example.changba131.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import com.example.changba131.myapplication.R;
import com.example.changba131.myapplication.util.ImageUtil;

/**
 * @author duanyafei
 */
public class KtvChallengeProgressBar extends View {

    public static final int MODE_NORMAL = 0;
    public static final int MODE_PK = 1;

    private int mMode = MODE_NORMAL;
    private int mProgress;

    private Paint mPaintBase;
    private RectF mBaseRect;
    private RectF mRoundRect;

    private Path mBasePath;
    private Path mDrawPath;
    private Path mDrawPathB;
    private int mRadius;
    private int padding = dpToPixel(2);

    private @ColorInt
    int BLACK = Color.BLACK;
    private @ColorInt
    int BgColor = Color.parseColor("#18FFFFFF");


    public KtvChallengeProgressBar(Context context) {
        this(context, null);
    }

    public KtvChallengeProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public KtvChallengeProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintBase = new Paint();
        mPaintBase.setColor(BgColor);
    }

    private LinearGradient getAShader() {
        return new LinearGradient(0, 0, 100, 100, Color.parseColor("#ff5046"), Color.parseColor("#ff274c"), Shader.TileMode.MIRROR);
    }

    private LinearGradient getBShader() {
        return new LinearGradient(0, 0, 100, 100, Color.parseColor("#51d5ff"), Color.parseColor("#28abff"), Shader.TileMode.MIRROR);
    }

    public void setMode(int mMode) {
        this.mMode = mMode;
        mBasePath = null;
        if (mMode == MODE_PK) {
            mProgress = 50;
            initModePkConfig();
        } else {
            mProgress = 0;
            initModeNormalConfig();
        }
        invalidate();
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRadius = getMeasuredHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMode == MODE_NORMAL) {
            initModeNormalConfig();
            //画基础背景部分
            mPaintBase.setShader(null);
            mPaintBase.setColor(BgColor);
            canvas.drawPath(mBasePath, mPaintBase);
            //计算进度设置对应Path
            calculateProgressNormal();
            mPaintBase.setShader(getAShader());
            mPaintBase.setColor(Color.WHITE);
            canvas.drawPath(mDrawPath, mPaintBase);
        } else if (mMode == MODE_PK) {
            initModePkConfig();
            //画基础背景部分
            mPaintBase.setShader(null);
            mPaintBase.setColor(BgColor);
            canvas.drawPath(mBasePath, mPaintBase);
            //计算进度设置对应Path
            if (calculateProgressPk()) {
                mPaintBase.setShader(getBShader());
                mPaintBase.setColor(Color.WHITE);
                canvas.drawPath(mDrawPathB, mPaintBase);
                mPaintBase.setShader(getAShader());
                mPaintBase.setColor(Color.WHITE);
                canvas.drawPath(mDrawPath, mPaintBase);
            } else {
                mPaintBase.setShader(getAShader());
                mPaintBase.setColor(Color.WHITE);
                canvas.drawPath(mDrawPath, mPaintBase);
                mPaintBase.setShader(getBShader());
                mPaintBase.setColor(Color.WHITE);
                canvas.drawPath(mDrawPathB, mPaintBase);
            }
        }
        //画礼物的图标
        int radiusBlackRound = (getHeight() - padding * 2) / 2;
        mPaintBase.setColor(BLACK);
        mPaintBase.setShader(null);
        canvas.drawCircle(padding + radiusBlackRound + mRoundRect.left, padding + radiusBlackRound, radiusBlackRound, mPaintBase);
        canvas.drawBitmap(ImageUtil.drawableToBitmap(getContext().getResources().getDrawable(R.drawable.ktv_icon_gift_progressbar)), padding + mRoundRect.left, padding, null);
    }

    private void initModeNormalConfig() {
        if (mBaseRect == null || mRoundRect == null || mBasePath == null) {
            mBaseRect = new RectF(0, padding, getWidth(), getHeight() - padding);
            mRoundRect = new RectF(0, 0, getHeight(), getHeight());
            mBasePath = new Path();
            mBasePath.addArc(mRoundRect, 0, 360);
            mBasePath.addRoundRect(mBaseRect, mRadius, mRadius, Path.Direction.CW);
        }
    }

    private void initModePkConfig() {
        if (mBaseRect == null || mRoundRect == null || mBasePath == null) {
            mBaseRect = new RectF(0, padding, getWidth(), getHeight() - padding);
            mRoundRect = new RectF((getWidth() - getHeight()) / 2, 0, (getWidth() + getHeight()) / 2, getHeight());
            mBasePath = new Path();
            mBasePath.addArc(mRoundRect, 0, 360);
            mBasePath.addRoundRect(mBaseRect, mRadius, mRadius, Path.Direction.CW);
        }
    }

    //weepAngle = 33.75*progress;
    // startAngle = (360 - 33.75*progress)/2;

    private void calculateProgressNormal() {
        if (mProgress <= 8) {
            int weepAngle = (int) (33.75 * mProgress);
            int startAngle = (int) ((360 - 33.75 * mProgress) / 2);
            mDrawPath = new Path();
            mDrawPath.addArc(mRoundRect, startAngle, weepAngle);
        } else {
            mDrawPath = new Path();
            mDrawPath.addArc(mRoundRect, 45, 270);
            RectF rectF = new RectF(mRadius, padding, getWidth() * mProgress / 100, getHeight() - padding);
            if (mProgress <= 95) {
                mDrawPath.addRect(rectF, Path.Direction.CW);
            } else {
                mDrawPath.addRoundRect(rectF, mRadius, mRadius, Path.Direction.CW);
            }
        }
    }

    /**
     * @return 返回A是否在上层能覆盖B
     */
    private boolean calculateProgressPk() {
        mDrawPath = new Path();
        mDrawPathB = new Path();

        RectF rectFA = null;
        RectF rectFB = null;

        //基础圆角进度  这个时候A比B少 让A就多画点让圆角正常，用B覆盖A多的部分。下边反之。
        if (mProgress >= 50) {
            rectFA = new RectF(0, padding, getWidth() * mProgress / 100, getHeight() - padding);
            rectFB = new RectF(getWidth() / 2, padding, getWidth(), getHeight() - padding);
        } else {
            rectFA = new RectF(0, padding, getWidth() / 2, getHeight() - padding);
            rectFB = new RectF(getWidth() * mProgress / 100, padding, getWidth(), getHeight() - padding);
        }

        mDrawPath.addRoundRect(rectFA, mRadius, mRadius, Path.Direction.CW);
        mDrawPathB.addRoundRect(rectFB, mRadius, mRadius, Path.Direction.CW);

        if (mProgress >= 5 && mProgress <= 50) {
            //交界处是竖线加了个方形
            rectFB.right -= mRadius;
            mDrawPathB.addRect(rectFB, Path.Direction.CW);
        } else if (50 < mProgress && mProgress <= 95) {
            rectFA.left += mRadius;
            mDrawPath.addRect(rectFA, Path.Direction.CW);
        }

        //中间部分圆环进度
        if (45 < mProgress && mProgress < 55) {
            int diff = mProgress - 45;
            int weepAngle = (int) (33.75 * diff);
            int startAngle = (int) ((360 - 33.75 * diff) / 2);
            mDrawPath.addArc(mRoundRect, startAngle, weepAngle);
            mDrawPathB.addArc(mRoundRect, -startAngle, 360 - weepAngle);
        } else if (mProgress >= 55) {
            mDrawPath.addArc(mRoundRect, 0, 360);
        } else {
            mDrawPathB.addArc(mRoundRect, 0, 360);
        }
        return mProgress >= 50;
    }


    public int dpToPixel(int dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
