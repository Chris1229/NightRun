package com.chris.common.utils;

import android.os.Environment;

/**
 * 作者：by chris
 * 日期：on 2017/8/6 - 上午9:56.
 * 邮箱：android_cjx@163.com
 */

public class FileConstants {
    // 文件保存路径
    private static final String SAVE_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    //获取缓存存储的根目录
    public static String getLarkSaveFilePath() {
        return SAVE_FILE_PATH + "/nightruncache/";
    }

    //获取文件存储的根目录
    public static String getApiSaveFilePath() {
        return SAVE_FILE_PATH + "/nightruncache/api2/";
    }

    //获取图片存储的根目录
    public static String getImageSaveFilePath() {
        return SAVE_FILE_PATH + "/nightruncache/image/";
    }

    //获取IM拍摄小视频存储的根目录
    public static String getVideoSaveFilePath() {
        return SAVE_FILE_PATH + "/nightruncache/recordvideo/";
    }
}
