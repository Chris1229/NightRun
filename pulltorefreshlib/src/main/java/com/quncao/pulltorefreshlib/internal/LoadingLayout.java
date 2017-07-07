/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.quncao.pulltorefreshlib.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quncao.pulltorefreshlib.ILoadingLayout;
import com.quncao.pulltorefreshlib.PullToRefreshBase;
import com.quncao.pulltorefreshlib.PullToRefreshBase.Mode;
import com.quncao.pulltorefreshlib.PullToRefreshBase.Orientation;
import com.quncao.pulltorefreshlib.R;

import static com.quncao.pulltorefreshlib.PullToRefreshBase.Mode.PULL_FROM_START;

@SuppressLint("ViewConstructor")
public abstract class LoadingLayout extends FrameLayout implements ILoadingLayout {
    private int[] imgs = { R.drawable.circle_white_s_000, R.drawable.circle_white_s_001, R.drawable.circle_white_s_002, R.drawable.circle_white_s_003,
            R.drawable.circle_white_s_004, R.drawable.circle_white_s_005, R.drawable.circle_white_s_006, R.drawable.circle_white_s_007,
            R.drawable.circle_white_s_008, R.drawable.circle_white_s_009, R.drawable.circle_white_s_010, R.drawable.circle_white_s_011,
            R.drawable.circle_white_s_012, R.drawable.circle_white_s_013, R.drawable.circle_white_s_014, R.drawable.circle_white_s_015,
            R.drawable.circle_white_s_016, R.drawable.circle_white_s_017, R.drawable.circle_white_s_018, R.drawable.circle_white_s_019,
            R.drawable.circle_white_s_020, R.drawable.circle_white_s_021, R.drawable.circle_white_s_022, R.drawable.circle_white_s_023,
            R.drawable.circle_white_s_024, R.drawable.circle_white_s_025, R.drawable.circle_white_s_026, R.drawable.circle_white_s_027,
            R.drawable.circle_white_s_028, R.drawable.circle_white_s_029, R.drawable.circle_white_s_030, R.drawable.circle_white_s_031,
            R.drawable.circle_white_s_032, R.drawable.circle_white_s_033, R.drawable.circle_white_s_034, R.drawable.circle_white_s_035,
            R.drawable.circle_white_s_036, R.drawable.circle_white_s_037, R.drawable.circle_white_s_038, R.drawable.circle_white_s_039,
            R.drawable.circle_white_s_040, R.drawable.circle_white_s_041, R.drawable.circle_white_s_042, R.drawable.circle_white_s_043
    };

    /**
     * 使用 BigFun 统一风格 20160714
     */
    public static final boolean CUSTOM = true;

    static final String LOG_TAG = "PullToRefresh-LoadingLayout";

    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();

    private ViewGroup mInnerLayout;

    protected final ImageView mHeaderImage;
    protected final ProgressBar mHeaderProgress;

    private boolean mUseIntrinsicAnimation;

    private final TextView mHeaderText;
    private final TextView mSubHeaderText;

    protected final Mode mMode;
    protected final Orientation mScrollDirection;

    private CharSequence mPullLabel;
    private CharSequence mRefreshingLabel;
    private CharSequence mReleaseLabel;

    public static final boolean USE_LARK_STYLE = true;
    private View mLarkLoadingGifRoot;
    private ImageView mPullingGifView;
    private ImageView mLoadingGifView;
//	private View mSloganView;

    //    private AnimationDrawable mPullingAnim;
    private AnimationDrawable mLoadingAnim;

    private void initGifViews() {
        if (!USE_LARK_STYLE)
            return;

        mLarkLoadingGifRoot = findViewById(R.id.lark_loading_root);
        mPullingGifView = (ImageView) findViewById(R.id.pulling_gif);
        mLoadingGifView = (ImageView) findViewById(R.id.loading_gif);
//		mSloganView = findViewById(R.id.lark_slogan);

//        setGif(mPullingGifView, R.drawable.pulling_frame_anim); //R.drawable.football);
        setGif(mLoadingGifView, R.drawable.loading_frame_anim); //R.drawable.jump);

        try {
//            mPullingAnim = (AnimationDrawable) mPullingGifView.getDrawable();
            mLoadingAnim = (AnimationDrawable) mLoadingGifView.getDrawable();

//            mPullingAnim.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGif(ImageView gifView, int gif) {
        if (gifView != null) {
//			Glide.with(getContext()).load(gif).asGif().into(gifView);
            gifView.setImageResource(gif);
        }
    }

//	public View getSlogan(){
//		return mSloganView;
//	}

    public View getLarkLoadingRootView() {
        return mInnerLayout;
    }

//	public int getSloganHeight(){
//		if(!USE_LARK_STYLE)
//			return 0 ;
//		return mSloganView.getHeight();
//	}

    public void switch2LoadingView() {
        if (!USE_LARK_STYLE)
            return;

//        if (null != mPullingAnim)
//            mPullingAnim.stop();

        mPullingGifView.setVisibility(View.INVISIBLE);
        mLoadingGifView.setVisibility(View.VISIBLE);

        if (null != mLoadingAnim)
            mLoadingAnim.start();
    }

    public void switch2PullingView() {
        if (!USE_LARK_STYLE)
            return;

//        if (null != mLoadingAnim)
//            mLoadingAnim.stop();

        mLoadingGifView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingGifView.setVisibility(View.INVISIBLE);
                mPullingGifView.setVisibility(View.VISIBLE);
            }
        }, PullToRefreshBase.SMOOTH_SCROLL_DURATION_MS);


//        if (null != mPullingAnim)
//            mPullingAnim.start();
    }

    public int getOriginalHeight() {
        return getResources().getDimensionPixelSize(R.dimen.lark_loading_layout_height);
    }

    private void resetLarkLoadingRootViewHeight() {
        if (!USE_LARK_STYLE)
            return;

        if (PULL_FROM_START == mMode) {
            ViewGroup.LayoutParams layoutParams = mLarkLoadingGifRoot.getLayoutParams();
            if (null != layoutParams) {
                layoutParams.height = getOriginalHeight();
                mLarkLoadingGifRoot.requestLayout();
            }
        }
    }

    public LoadingLayout(Context context, final Mode mode, final Orientation scrollDirection, TypedArray attrs) {
        super(context);
        mMode = mode;
        mScrollDirection = scrollDirection;

        switch (scrollDirection) {
            case HORIZONTAL:
                LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_horizontal, this);
                break;
            case VERTICAL:
            default:
                if (CUSTOM) {
                    switch (mode) {
                        default:
                        case PULL_FROM_START:
                            if (USE_LARK_STYLE)
                                LayoutInflater.from(context).inflate(R.layout.lark_loading_layout, this);
                            else
                                LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_vertical_custom_start, this);
                            break;
                        case PULL_FROM_END:
                            LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_vertical_custom_end, this);
                            break;
                    }
                } else {
                    LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_vertical, this);
                }
                break;
        }

        mInnerLayout = (ViewGroup) findViewById(R.id.fl_inner);
//		mHeaderText = (TextView) mInnerLayout.findViewById(R.id.pull_to_refresh_text);
        mHeaderText = (TextView) mInnerLayout.findViewById(R.id.fresh_loading_notice_txt);
        mHeaderProgress = (ProgressBar) mInnerLayout.findViewById(R.id.pull_to_refresh_progress);
        mSubHeaderText = (TextView) mInnerLayout.findViewById(R.id.pull_to_refresh_sub_text);
        mHeaderImage = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refresh_image);

        initGifViews();

        LayoutParams lp = (LayoutParams) mInnerLayout.getLayoutParams();

        switch (mode) {
            case PULL_FROM_END:
                lp.gravity = scrollDirection == Orientation.VERTICAL ? Gravity.TOP : Gravity.LEFT;

                if (CUSTOM) {
                    mPullLabel = "上拉查看更多";
                    mRefreshingLabel = "";
                    mReleaseLabel = "松开载入更多";
                } else {
                    // Load in labels
                    mPullLabel = context.getString(R.string.pull_to_refresh_from_bottom_pull_label);
                    mRefreshingLabel = context.getString(R.string.pull_to_refresh_from_bottom_refreshing_label);
                    mReleaseLabel = context.getString(R.string.pull_to_refresh_from_bottom_release_label);
                }
                break;

            case PULL_FROM_START:
            default:
                lp.gravity = scrollDirection == Orientation.VERTICAL ? Gravity.BOTTOM : Gravity.RIGHT;

                if (CUSTOM) {
                    mPullLabel = "下拉刷新";
                    mRefreshingLabel = "正在刷新...";
                    mReleaseLabel = "松开看看";
                } else {
                    // Load in labels
                    mPullLabel = context.getString(R.string.pull_to_refresh_pull_label);
                    mRefreshingLabel = context.getString(R.string.pull_to_refresh_refreshing_label);
                    mReleaseLabel = context.getString(R.string.pull_to_refresh_release_label);
                }
                break;
        }

        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderBackground)) {
            Drawable background = attrs.getDrawable(R.styleable.PullToRefresh_ptrHeaderBackground);
            if (null != background) {
                ViewCompat.setBackground(this, background);
            }
        }

        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderTextAppearance)) {
            TypedValue styleID = new TypedValue();
            attrs.getValue(R.styleable.PullToRefresh_ptrHeaderTextAppearance, styleID);
            setTextAppearance(styleID.data);
        }
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrSubHeaderTextAppearance)) {
            TypedValue styleID = new TypedValue();
            attrs.getValue(R.styleable.PullToRefresh_ptrSubHeaderTextAppearance, styleID);
            setSubTextAppearance(styleID.data);
        }

        // Text Color attrs need to be set after TextAppearance attrs
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderTextColor)) {
            ColorStateList colors = attrs.getColorStateList(R.styleable.PullToRefresh_ptrHeaderTextColor);
            if (null != colors) {
                setTextColor(colors);
            }
        }
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderSubTextColor)) {
            ColorStateList colors = attrs.getColorStateList(R.styleable.PullToRefresh_ptrHeaderSubTextColor);
            if (null != colors) {
                setSubTextColor(colors);
            }
        }

        // Try and get defined drawable from Attrs
        Drawable imageDrawable = null;
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawable)) {
            imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawable);
        }

        // Check Specific Drawable from Attrs, these overrite the generic
        // drawable attr above
        switch (mode) {
            case PULL_FROM_START:
            default:
                if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableStart)) {
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableStart);
                } else if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableTop)) {
                    Utils.warnDeprecation("ptrDrawableTop", "ptrDrawableStart");
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableTop);
                }
                break;

            case PULL_FROM_END:
                if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableEnd)) {
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableEnd);
                } else if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableBottom)) {
                    Utils.warnDeprecation("ptrDrawableBottom", "ptrDrawableEnd");
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableBottom);
                }
                break;
        }

        // If we don't have a user defined drawable, load the default
        if (null == imageDrawable) {
            if (CUSTOM) {
                imageDrawable = context.getResources().getDrawable(R.drawable.new_arrow);
            } else {
                imageDrawable = context.getResources().getDrawable(getDefaultDrawableResId());
            }
        }

        // Set Drawable, and save width/height
        setLoadingDrawable(imageDrawable);

//		if(!isCustomAndPullEndMode()) {
//			SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
//			mSubHeaderText.setText("上次更新时间: " + format.format(new Date()));
//		}

        reset();
    }

    public final void setHeight(int height) {
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) getLayoutParams();
        lp.height = height;
        requestLayout();
    }

    public final void setWidth(int width) {
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) getLayoutParams();
        lp.width = width;
        requestLayout();
    }

    public final int getContentSize() {
        switch (mScrollDirection) {
            case HORIZONTAL:
                return mInnerLayout.getWidth();
            case VERTICAL:
            default:
                if (USE_LARK_STYLE)
                    return getOriginalHeight();
                else
                    return mInnerLayout.getHeight();
        }
    }

    public final void hideAllViews() {
        if (View.VISIBLE == mHeaderText.getVisibility()) {
            mHeaderText.setVisibility(View.INVISIBLE);
        }
        if (View.VISIBLE == mHeaderProgress.getVisibility()) {
            mHeaderProgress.setVisibility(View.INVISIBLE);
        }
        if (View.VISIBLE == mHeaderImage.getVisibility()) {
            mHeaderImage.setVisibility(View.INVISIBLE);
        }
        if (View.VISIBLE == mSubHeaderText.getVisibility()) {
            mSubHeaderText.setVisibility(View.INVISIBLE);
        }
    }

    public final void onPull(float scaleOfLayout) {
        if (!mUseIntrinsicAnimation) {
            onPullImpl(scaleOfLayout);
        }
    }

    //fyqiang  判断方向
    public final void onPull(Mode mode, float scaleOfLayout) {
        if (!mUseIntrinsicAnimation) {
//            Log.e("====", "scaleOfLayout==" + scaleOfLayout);
            if (mode == PULL_FROM_START) {
                if (scaleOfLayout >= 1) {
                    mPullingGifView.setImageResource(imgs[imgs.length - 1]);
                } else {
                    mPullingGifView.setImageResource(imgs[(int) (scaleOfLayout * imgs.length)]);
                }
            }
            onPullImpl(scaleOfLayout);
        }
    }


    public final void pullToRefresh() {
        if (null != mHeaderText) {
            mHeaderText.setText(mPullLabel);
        }

        // Now call the callback
        pullToRefreshImpl();
    }

    public final void completeRefresh() {
        if (null != mHeaderText) {
            mHeaderText.setText("完成刷新");
        }
    }

    private boolean isCustomAndPullEndMode() {
        return CUSTOM && (Mode.PULL_FROM_END == mMode);
    }

    public final void refreshing() {
        if (null != mHeaderText) {
            if (isCustomAndPullEndMode()) {
                mHeaderText.setText("");
            } else {
                mHeaderText.setText(mRefreshingLabel);
            }
        }

        if (isCustomAndPullEndMode()) {
            mHeaderProgress.setVisibility(View.VISIBLE);
        }

        if (mUseIntrinsicAnimation) {
            ((AnimationDrawable) mHeaderImage.getDrawable()).start();
        } else {
            // Now call the callback
            refreshingImpl();
        }

        if (null != mSubHeaderText && !CUSTOM) {
            mSubHeaderText.setVisibility(View.GONE);
        }
    }

    public final void releaseToRefresh() {
        if (null != mHeaderText) {
            mHeaderText.setText(mReleaseLabel);
        }

        // Now call the callback
        releaseToRefreshImpl();
    }

    public final void reset() {
        resetLarkLoadingRootViewHeight();

        if (null != mHeaderText) {
            mHeaderText.setText(mPullLabel);
        }
        mHeaderImage.setVisibility(View.VISIBLE);

        if (isCustomAndPullEndMode()) {
            mHeaderProgress.setVisibility(View.INVISIBLE);
        }

        if (mUseIntrinsicAnimation) {
            ((AnimationDrawable) mHeaderImage.getDrawable()).stop();
        } else {
            // Now call the callback
            resetImpl();
        }

        if (null != mSubHeaderText) {
            if (TextUtils.isEmpty(mSubHeaderText.getText())) {
                mSubHeaderText.setVisibility(View.GONE);
            } else {
                mSubHeaderText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {
        if (!isCustomAndPullEndMode())
            setSubHeaderText(label);
    }

    public final void setLoadingDrawable(Drawable imageDrawable) {
        // Set Drawable
        mHeaderImage.setImageDrawable(imageDrawable);
        mUseIntrinsicAnimation = (imageDrawable instanceof AnimationDrawable);

        // Now call the callback
        onLoadingDrawableSet(imageDrawable);
    }

    public void setPullLabel(CharSequence pullLabel) {
        mPullLabel = pullLabel;
    }

    public void setRefreshingLabel(CharSequence refreshingLabel) {
        mRefreshingLabel = refreshingLabel;
    }

    public void setReleaseLabel(CharSequence releaseLabel) {
        mReleaseLabel = releaseLabel;
    }

    @Override
    public void setTextTypeface(Typeface tf) {
        mHeaderText.setTypeface(tf);
    }

    public final void showInvisibleViews() {
        if (View.INVISIBLE == mHeaderText.getVisibility()) {
            mHeaderText.setVisibility(View.VISIBLE);
        }
        if (View.INVISIBLE == mHeaderProgress.getVisibility()) {
            mHeaderProgress.setVisibility(View.VISIBLE);
        }
        if (View.INVISIBLE == mHeaderImage.getVisibility()) {
            mHeaderImage.setVisibility(View.VISIBLE);
        }
        if (View.INVISIBLE == mSubHeaderText.getVisibility()) {
            mSubHeaderText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Callbacks for derivative Layouts
     */

    protected abstract int getDefaultDrawableResId();

    protected abstract void onLoadingDrawableSet(Drawable imageDrawable);

    protected abstract void onPullImpl(float scaleOfLayout);

    protected abstract void pullToRefreshImpl();

    protected abstract void refreshingImpl();

    protected abstract void releaseToRefreshImpl();

    protected abstract void resetImpl();

    private void setSubHeaderText(CharSequence label) {
        if (null != mSubHeaderText) {
            if (TextUtils.isEmpty(label)) {
                mSubHeaderText.setVisibility(View.GONE);
            } else {
                mSubHeaderText.setText(label);

                // Only set it to Visible if we're GONE, otherwise VISIBLE will
                // be set soon
                if (View.GONE == mSubHeaderText.getVisibility()) {
                    mSubHeaderText.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setSubTextAppearance(int value) {
        if (null != mSubHeaderText) {
            mSubHeaderText.setTextAppearance(getContext(), value);
        }
    }

    private void setSubTextColor(ColorStateList color) {
        if (null != mSubHeaderText) {
            mSubHeaderText.setTextColor(color);
        }
    }

    private void setTextAppearance(int value) {
        if (null != mHeaderText) {
            mHeaderText.setTextAppearance(getContext(), value);
        }
        if (null != mSubHeaderText) {
            mSubHeaderText.setTextAppearance(getContext(), value);
        }
    }

    private void setTextColor(ColorStateList color) {
        if (null != mHeaderText) {
            mHeaderText.setTextColor(color);
        }
        if (null != mSubHeaderText) {
            mSubHeaderText.setTextColor(color);
        }
    }

}
