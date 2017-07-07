package com.chris.common.image;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chris.common.R;

import java.util.Iterator;

/**
 * 作者：by chris
 * 日期：on 2017/6/20 - 下午5:11.
 * 邮箱：android_cjx@163.com
 */

public class ImageUtils {


    /**
     * 加载网络图片,默认图不需要传
     * @param context
     * @param url
     * @param view
     */
    public static void loadImage(Activity context, String url, ImageView view){

        if (context == null) {
            return;
        }
        if(!TextUtils.isEmpty(url)){
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.room_thumbnails)
                    .error(R.drawable.room_thumbnails)
                    .into(view);
        }else{
            view.setImageResource(R.drawable.room_thumbnails);
        }

    }

    public static void loadImage(Fragment fragment, String url, ImageView view){

        if(fragment.getActivity() ==null){
            return;
        }

        if(!TextUtils.isEmpty(url)){
            Glide.with(fragment.getActivity())
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.room_thumbnails)
                    .error(R.drawable.room_thumbnails)
                    .into(view);
        }else{

            view.setImageResource(R.drawable.room_thumbnails);
        }

    }

    public static void loadImage(Context context, String url, ImageView view){

        if (context == null) {
            return;
        }

        try {
            if(!TextUtils.isEmpty(url)){
                Glide.with(context)
                        .load(url)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.room_thumbnails)
                        .error(R.drawable.room_thumbnails)
                        .into(view);
            }else{

                view.setImageResource(R.drawable.room_thumbnails);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 加载网络图片设置默认图片加载失败图片
     * @param context
     * @param url
     * @param defaultId
     * @param view
     */
    public static void loadImage(Activity context, String url, int defaultId , ImageView view){

        if (context == null) {
            return;
        }
        if(!TextUtils.isEmpty(url)){
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(defaultId)
                    .error(defaultId)
                    .into(view);
        }else{
            view.setImageResource(defaultId);
        }

    }


    public static void loadImage(Fragment fragment, String url, int defaultId , ImageView view){

        if (fragment.getActivity() == null) {
            return;
        }
        if(!TextUtils.isEmpty(url)){
            Glide.with(fragment.getActivity())
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(defaultId)
                    .error(defaultId)
                    .into(view);
        }else{
            view.setImageResource(defaultId);
        }

    }

    public static void loadImage(Context context, String url, int defaultId , ImageView view){

        if ( context == null) {
            return;
        }
        if(!TextUtils.isEmpty(url)){
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(defaultId)
                    .error(defaultId)
                    .into(view);
        }else{
            view.setImageResource(defaultId);
        }

    }

    /**
     *加载圆角图片
     * @param context
     * @param url 需要展示的图片地址
     * @param radius 图片圆角半径
     * @param defaultId 默认图片
     * @param view 图片展示view
     */

    public static void loadRoundImage(Context context, String url, int radius, int defaultId, ImageView view){
        if (context == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, radius, 0,
                            RoundedCornersTransformation.CornerType.ALL))
                    .dontAnimate()
                    .placeholder(defaultId)
                    .error(defaultId)
                    .crossFade()
                    .into(view);
        } else {
            view.setImageResource(defaultId);
        }
    }

    public static void loadRoundImage(Fragment fragment, String url, int radius, int defaultId, ImageView view){
        if (fragment.getActivity() == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
           loadRoundImage(fragment.getContext(),url,radius,defaultId,view);
        } else {
            view.setImageResource(defaultId);
        }
    }

    public static void loadRoundImage(Activity context, String url, int radius, int defaultId, ImageView view){
        if (context == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, radius, 0,
                            RoundedCornersTransformation.CornerType.ALL))
                    .dontAnimate()
                    .placeholder(defaultId)
                    .error(defaultId)
                    .crossFade()
                    .into(view);
        } else {
            view.setImageResource(defaultId);
        }
    }

    /**
     *加载圆形图片
     * @param context
     * @param url 图片地址
     * @param defaultId 默认图片
     * @param view 展示view
     */

    public static void loadCircleImage(Context context, String url, int defaultId, ImageView view) {
        if (context == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .bitmapTransform(new CenterCrop(context), new CropCircleTransformation(context))
                    .dontAnimate()
                    .placeholder(defaultId)
                    .error(defaultId)
                    .crossFade()
                    .into(view);
        } else {
            view.setImageResource(defaultId);
        }
    }

    public static void loadCircleImage(Fragment fragment, String url, int defaultId, ImageView view) {
        if (fragment.getActivity() == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            loadCircleImage(fragment.getActivity(),url,defaultId,view);
        } else {
            view.setImageResource(defaultId);
        }
    }

    public static void loadCircleImage(Activity context, String url, int defaultId, ImageView view) {
        if (context == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .bitmapTransform(new CenterCrop(context), new CropCircleTransformation(context))
                    .dontAnimate()
                    .placeholder(defaultId)
                    .error(defaultId)
                    .crossFade()
                    .into(view);
        } else {
            view.setImageResource(defaultId);
        }
    }

}
