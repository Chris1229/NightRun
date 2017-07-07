package com.chris.common.image;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * 作者：by chris
 * 日期：on 2017/6/23 - 下午3:26.
 * 邮箱：android_cjx@163.com
 */

public class RoundedCornersTransformation implements Transformation<Bitmap> {
    private BitmapPool mBitmapPool;
    private int mRadius;
    private int mDiameter;
    private int mMargin;
    private CornerType mCornerType;

    public static enum CornerType
    {
        ALL,  TOP_LEFT,  TOP_RIGHT,  BOTTOM_LEFT,  BOTTOM_RIGHT,  TOP,  BOTTOM,  LEFT,  RIGHT,  OTHER_TOP_LEFT,  OTHER_TOP_RIGHT,  OTHER_BOTTOM_LEFT,  OTHER_BOTTOM_RIGHT,  DIAGONAL_FROM_TOP_LEFT,  DIAGONAL_FROM_TOP_RIGHT;

        private CornerType() {}
    }

    public RoundedCornersTransformation(Context context, int radius, int margin)
    {
        this(context, radius, margin, CornerType.ALL);
    }

    public RoundedCornersTransformation(BitmapPool pool, int radius, int margin)
    {
        this(pool, radius, margin, CornerType.ALL);
    }

    public RoundedCornersTransformation(Context context, int radius, int margin, CornerType cornerType)
    {
        this(Glide.get(context).getBitmapPool(), radius, margin, cornerType);
    }

    public RoundedCornersTransformation(BitmapPool pool, int radius, int margin, CornerType cornerType)
    {
        this.mBitmapPool = pool;
        this.mRadius = radius;
        this.mDiameter = (this.mRadius * 2);
        this.mMargin = margin;
        this.mCornerType = cornerType;
    }

    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight)
    {
        Bitmap source = (Bitmap)resource.get();

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = this.mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        drawRoundRect(canvas, paint, width, height);
        return BitmapResource.obtain(bitmap, this.mBitmapPool);
    }

    private void drawRoundRect(Canvas canvas, Paint paint, float width, float height)
    {
        float right = width - this.mMargin;
        float bottom = height - this.mMargin;
        switch (this.mCornerType)
        {
            case ALL:
                canvas.drawRoundRect(new RectF(this.mMargin, this.mMargin, right, bottom), this.mRadius, this.mRadius, paint);
                break;
            case TOP_LEFT:
                drawTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case TOP_RIGHT:
                drawTopRightRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM_LEFT:
                drawBottomLeftRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM_RIGHT:
                drawBottomRightRoundRect(canvas, paint, right, bottom);
                break;
            case TOP:
                drawTopRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM:
                drawBottomRoundRect(canvas, paint, right, bottom);
                break;
            case LEFT:
                drawLeftRoundRect(canvas, paint, right, bottom);
                break;
            case RIGHT:
                drawRightRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_TOP_LEFT:
                drawOtherTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_TOP_RIGHT:
                drawOtherTopRightRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_BOTTOM_LEFT:
                drawOtherBottomLeftRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_BOTTOM_RIGHT:
                drawOtherBottomRightRoundRect(canvas, paint, right, bottom);
                break;
            case DIAGONAL_FROM_TOP_LEFT:
                drawDiagonalFromTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case DIAGONAL_FROM_TOP_RIGHT:
                drawDiagonalFromTopRightRoundRect(canvas, paint, right, bottom);
                break;
            default:
                canvas.drawRoundRect(new RectF(this.mMargin, this.mMargin, right, bottom), this.mRadius, this.mRadius, paint);
        }
    }

    private void drawTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(this.mMargin, this.mMargin, this.mMargin + this.mDiameter, this.mMargin + this.mDiameter), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin, this.mMargin + this.mRadius, this.mMargin + this.mRadius, bottom), paint);
        canvas.drawRect(new RectF(this.mMargin + this.mRadius, this.mMargin, right, bottom), paint);
    }

    private void drawTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(right - this.mDiameter, this.mMargin, right, this.mMargin + this.mDiameter), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin, this.mMargin, right - this.mRadius, bottom), paint);
        canvas.drawRect(new RectF(right - this.mRadius, this.mMargin + this.mRadius, right, bottom), paint);
    }

    private void drawBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(this.mMargin, bottom - this.mDiameter, this.mMargin + this.mDiameter, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin, this.mMargin, this.mMargin + this.mDiameter, bottom - this.mRadius), paint);
        canvas.drawRect(new RectF(this.mMargin + this.mRadius, this.mMargin, right, bottom), paint);
    }

    private void drawBottomRightRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(right - this.mDiameter, bottom - this.mDiameter, right, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin, this.mMargin, right - this.mRadius, bottom), paint);
        canvas.drawRect(new RectF(right - this.mRadius, this.mMargin, right, bottom - this.mRadius), paint);
    }

    private void drawTopRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(this.mMargin, this.mMargin, right, this.mMargin + this.mDiameter), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin, this.mMargin + this.mRadius, right, bottom), paint);
    }

    private void drawBottomRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(this.mMargin, bottom - this.mDiameter, right, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin, this.mMargin, right, bottom - this.mRadius), paint);
    }

    private void drawLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(this.mMargin, this.mMargin, this.mMargin + this.mDiameter, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin + this.mRadius, this.mMargin, right, bottom), paint);
    }

    private void drawRightRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(right - this.mDiameter, this.mMargin, right, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin, this.mMargin, right - this.mRadius, bottom), paint);
    }

    private void drawOtherTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(this.mMargin, bottom - this.mDiameter, right, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRoundRect(new RectF(right - this.mDiameter, this.mMargin, right, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin, this.mMargin, right - this.mRadius, bottom - this.mRadius), paint);
    }

    private void drawOtherTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(this.mMargin, this.mMargin, this.mMargin + this.mDiameter, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRoundRect(new RectF(this.mMargin, bottom - this.mDiameter, right, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin + this.mRadius, this.mMargin, right, bottom - this.mRadius), paint);
    }

    private void drawOtherBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(this.mMargin, this.mMargin, right, this.mMargin + this.mDiameter), this.mRadius, this.mRadius, paint);

        canvas.drawRoundRect(new RectF(right - this.mDiameter, this.mMargin, right, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin, this.mMargin + this.mRadius, right - this.mRadius, bottom), paint);
    }

    private void drawOtherBottomRightRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(this.mMargin, this.mMargin, right, this.mMargin + this.mDiameter), this.mRadius, this.mRadius, paint);

        canvas.drawRoundRect(new RectF(this.mMargin, this.mMargin, this.mMargin + this.mDiameter, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin + this.mRadius, this.mMargin + this.mRadius, right, bottom), paint);
    }

    private void drawDiagonalFromTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(this.mMargin, this.mMargin, this.mMargin + this.mDiameter, this.mMargin + this.mDiameter), this.mRadius, this.mRadius, paint);

        canvas.drawRoundRect(new RectF(right - this.mDiameter, bottom - this.mDiameter, right, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin, this.mMargin + this.mRadius, right - this.mDiameter, bottom), paint);
        canvas.drawRect(new RectF(this.mMargin + this.mDiameter, this.mMargin, right, bottom - this.mRadius), paint);
    }

    private void drawDiagonalFromTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom)
    {
        canvas.drawRoundRect(new RectF(right - this.mDiameter, this.mMargin, right, this.mMargin + this.mDiameter), this.mRadius, this.mRadius, paint);

        canvas.drawRoundRect(new RectF(this.mMargin, bottom - this.mDiameter, this.mMargin + this.mDiameter, bottom), this.mRadius, this.mRadius, paint);

        canvas.drawRect(new RectF(this.mMargin, this.mMargin, right - this.mRadius, bottom - this.mRadius), paint);
        canvas.drawRect(new RectF(this.mMargin + this.mRadius, this.mMargin + this.mRadius, right, bottom), paint);
    }

    public String getId()
    {
        return "RoundedTransformation(radius=" + this.mRadius + ", margin=" + this.mMargin + ", diameter=" + this.mDiameter + ", cornerType=" + this.mCornerType.name() + ")";
    }
}
