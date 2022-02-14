package com.example.changba131.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.changba131.myapplication.R;
import com.example.changba131.myapplication.util.ImageUtil;

public class RoundCornerFrameLayout extends FrameLayout {

  private static final String TAG = "RoundCornerFrameLayout";

  private static final int RADIUS = 60;

  private Paint mImagePaint = new Paint();
  private Paint mRoundPaint = new Paint();

  public RoundCornerFrameLayout(@NonNull Context context) {
    this(context, null);
  }

  public RoundCornerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RoundCornerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mRoundPaint.setColor(Color.WHITE);
    mRoundPaint.setAntiAlias(true);
    mRoundPaint.setStyle(Paint.Style.FILL);
    mRoundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
    drawContent(canvas);
  }

  private void drawContent(Canvas canvas) {
    Rect rect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());

    Log.d(TAG, "drawContent, rect:" + rect);
    int sc = canvas.saveLayer(new RectF(rect), null, Canvas.ALL_SAVE_FLAG);

    Bitmap dst = BitmapFactory.decodeResource(getResources(), R.drawable.personalize_round);

    canvas.drawBitmap(dst, null, rect, mImagePaint);


    int width = canvas.getWidth(), height = canvas.getHeight();
    Bitmap src = getRoundCornerBitmap(width, height, RADIUS);
    canvas.drawBitmap(src, null, rect, mRoundPaint);

    canvas.restoreToCount(sc);
    Log.d(TAG, "drawContent() called with: canvas = [" + canvas + "]");
  }

  private Bitmap getRoundCornerBitmap(int width, int height, int radius) {
    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
    Canvas canvas = new Canvas(bitmap);

    int topLeftRadius = radius, topRightRadius = radius, bottomRightRadius = radius, bottomLeftRadius = radius;
    Path path = new Path();
    path.moveTo(0, topLeftRadius);
    path.arcTo(new RectF(0, 0, topLeftRadius * 2, topLeftRadius * 2), -180, 90);
    path.lineTo(width - topRightRadius, 0);
    path.arcTo(new RectF(width - 2 * topRightRadius, 0, width, topRightRadius * 2), -90, 90);
    path.lineTo(width, height - bottomRightRadius);
    path.arcTo(new RectF(width - 2 * bottomRightRadius, height - 2 * bottomRightRadius, width, height), 0, 90);
    path.lineTo(bottomLeftRadius, height);
    path.arcTo(new RectF(0, height - 2 * bottomLeftRadius, bottomLeftRadius * 2, height), 90, 90);
    path.close();
    canvas.drawPath(path, new Paint());

    return bitmap;
  }

}
