package com.chris.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;


import com.chris.common.KeelApplication;

import java.io.InputStream;

public class SceneAnimation {
    private ImageView mImageView;
    private int[] mFrameRess;
    private int[] mDurations;
    private int mDuration;

    private int mLastFrameNo;
    private long mBreakDelay;
    private boolean startAuto;

    public SceneAnimation(ImageView pImageView, int[] pFrameRess,
                          int[] pDurations) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDurations = pDurations;
        mLastFrameNo = pFrameRess.length - 1;

        mImageView.setImageBitmap(readBitMap(KeelApplication.getApp(), mFrameRess[0]));
        play(1);
    }

    public SceneAnimation(ImageView pImageView, int[] pFrameRess, int pDuration) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length - 1;

        mImageView.setImageBitmap(readBitMap(KeelApplication.getApp(), mFrameRess[0]));
        playConstant(1);
    }

    public SceneAnimation(ImageView pImageView, int[] pFrameRess, int pDuration, boolean isStartAuto) {
        startAuto = isStartAuto;
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length - 1;

        mImageView.setImageBitmap(readBitMap(KeelApplication.getApp(), mFrameRess[0]));
    }

    public SceneAnimation(ImageView pImageView, int[] pFrameRess,
                          int pDuration, long pBreakDelay) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length - 1;
        mBreakDelay = pBreakDelay;

        mImageView.setImageBitmap(readBitMap(KeelApplication.getApp(), mFrameRess[0]));
        playConstant(1);
    }

    public void playByStop(final int pFrameNo) {
        if (mImageView != null) {
            mImageView.setVisibility(View.VISIBLE);
            mImageView.postDelayed(new Runnable() {
                public void run() {
                    if (mImageView == null) {
                        return;
                    }
                    mImageView.setImageBitmap(readBitMap(KeelApplication.getApp(), mFrameRess[pFrameNo]));

                    if (startAuto) {
                        if (pFrameNo == mLastFrameNo)
                            playByStop(0);
                        else
                            playByStop(pFrameNo + 1);
                    }
                }
            }, pFrameNo == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay
                    : mDuration);
        }
    }

    private void play(final int pFrameNo) {
        mImageView.postDelayed(new Runnable() {
            public void run() {
                if (mImageView == null) {
                    return;
                }
                mImageView.setImageBitmap(readBitMap(KeelApplication.getApp(), mFrameRess[pFrameNo]));

                if (pFrameNo == mLastFrameNo)
                    play(0);
                else
                    play(pFrameNo + 1);
            }
        }, mDurations[pFrameNo]);
    }

    private void playConstant(final int pFrameNo) {
        if (mImageView != null){
            mImageView.postDelayed(new Runnable() {
                public void run() {
                    if (mImageView == null) {
                        return;
                    }
                    mImageView.setImageBitmap(readBitMap(KeelApplication.getApp(), mFrameRess[pFrameNo]));

                    if (pFrameNo == mLastFrameNo)
                        playConstant(0);
                    else
                        playConstant(pFrameNo + 1);
                }
            }, pFrameNo == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay
                    : mDuration);
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return Bitmap
     */
    private Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public void stop() {
        if (mImageView != null) {
            mImageView.setVisibility(View.GONE);
        }
        startAuto = false;
        mImageView = null;
    }

}