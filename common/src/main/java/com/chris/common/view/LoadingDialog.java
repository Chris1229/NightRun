package com.chris.common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chris.common.R;


public class LoadingDialog extends Dialog {
    public LinearLayout layoutBg;
    public TextView tvMessage;
    public ImageView spaceshipImage;
    public Context context;
    private int styleid;

    private int[] loadingPics = new int[]{
            R.drawable.circle_white_s_000, R.drawable.circle_white_s_001, R.drawable.circle_white_s_002, R.drawable.circle_white_s_003,
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
//    private int[] loadingPicsRed = new int[]{
//            R.drawable.loading_red_000, R.drawable.loading_red_001, R.drawable.loading_red_002, R.drawable.loading_red_003,
//            R.drawable.loading_red_004, R.drawable.loading_red_005, R.drawable.loading_red_006, R.drawable.loading_red_007,
//            R.drawable.loading_red_008, R.drawable.loading_red_009, R.drawable.loading_red_010, R.drawable.loading_red_011,
//            R.drawable.loading_red_012, R.drawable.loading_red_013, R.drawable.loading_red_014, R.drawable.loading_red_015,
//            R.drawable.loading_red_016, R.drawable.loading_red_017, R.drawable.loading_red_018, R.drawable.loading_red_019,
//            R.drawable.loading_red_020, R.drawable.loading_red_021, R.drawable.loading_red_022, R.drawable.loading_red_023,
//            R.drawable.loading_red_024, R.drawable.loading_red_025, R.drawable.loading_red_026, R.drawable.loading_red_027,
//            R.drawable.loading_red_028, R.drawable.loading_red_029, R.drawable.loading_red_030, R.drawable.loading_red_031,
//            R.drawable.loading_red_032, R.drawable.loading_red_033, R.drawable.loading_red_034, R.drawable.loading_red_035,
//            R.drawable.loading_red_036, R.drawable.loading_red_037, R.drawable.loading_red_038, R.drawable.loading_red_039,
//            R.drawable.loading_red_040, R.drawable.loading_red_041, R.drawable.loading_red_042, R.drawable.loading_red_043
//    };
    private SceneAnimation sceneAnimation;

    public LoadingDialog(Context context) {
        this(context, R.style.loadingDialog);
    }

    public LoadingDialog(Context context, int styleId) {
        super(context, styleId);
        setContentView(R.layout.dialog_loading);
        this.context = context;
        this.styleid = styleId;
        layoutBg = (LinearLayout) findViewById(R.id.dialog_view);
        spaceshipImage = (ImageView) findViewById(R.id.img);
        tvMessage = (TextView) findViewById(R.id.tipTextView);
//        if (styleId == R.style.loadingDialogRed) {
//            layoutBg.setBackgroundResource(R.color.transparent);
//            tvMessage.setVisibility(View.GONE);
//        } else {
//            layoutBg.setBackgroundResource(R.drawable.background_round_7f000000);
//            tvMessage.setVisibility(View.VISIBLE);
//        }
        layoutBg.setBackgroundResource(R.drawable.background_round_7f000000);
        tvMessage.setVisibility(View.VISIBLE);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = -120; // 新位置Y坐标
        dialogWindow.setAttributes(lp);

    }

    public void setMessage(String message) {
        if (TextUtils.isEmpty(message)) {
            tvMessage.setVisibility(View.GONE);
        } else {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
        }
    }

    public void dismissDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 300);
    }

    @Override
    public void dismiss() {
        sceneAnimation.stop();
        super.dismiss();
    }

    @Override
    public void show() {
        if (spaceshipImage == null) {
            spaceshipImage = (ImageView) findViewById(R.id.img);
        }
        if (sceneAnimation == null) {
//            if (styleid == R.style.loadingDialogRed) {
//                sceneAnimation = new SceneAnimation(spaceshipImage, loadingPicsRed, 33);
//            } else {
//                sceneAnimation = new SceneAnimation(spaceshipImage, loadingPics, 33);
//            }
            sceneAnimation = new SceneAnimation(spaceshipImage, loadingPics, 33);

        }
        super.show();
    }
}
