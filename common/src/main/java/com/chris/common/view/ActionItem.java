package com.chris.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * 作者：by chris
 * 日期：on 2017/8/8 - 下午3:06.
 * 邮箱：android_cjx@163.com
 */

public class ActionItem {
    //定义图片对象
    public Drawable mDrawable;
    //定义文本对象
    public String mTitle;

    public String color;

    public ActionItem(Drawable drawable, String title) {
        this.mDrawable = drawable;
        this.mTitle = title;
    }

    public ActionItem(Drawable drawable, String title, String color) {
        this.mDrawable = drawable;
        this.mTitle = title;
        this.color = color;
    }

    public ActionItem(Context context, int titleId, int drawableId) {
        this.mTitle = context.getResources().getString(titleId);
        this.mDrawable = context.getResources().getDrawable(drawableId);
    }

    public ActionItem(Context context, String title, int drawableId) {
        this.mTitle = title;
        if(drawableId != 0){
            this.mDrawable = context.getResources().getDrawable(drawableId);
        }
    }

    public String getmTitle() {
        return mTitle;
    }
}
