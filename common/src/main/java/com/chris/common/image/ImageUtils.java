package com.chris.common.image;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chris.common.utils.FileConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.android.volley.misc.ImageUtils.calculateInSampleSize;

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
//                    .placeholder(R.drawable.room_thumbnails)
//                    .error(R.drawable.room_thumbnails)
                    .into(view);
        }else{
//            view.setImageResource(R.drawable.room_thumbnails);
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
//                    .placeholder(R.drawable.room_thumbnails)
//                    .error(R.drawable.room_thumbnails)
                    .into(view);
        }else{

//            view.setImageResource(R.drawable.room_thumbnails);
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
//                        .placeholder(R.drawable.room_thumbnails)
//                        .error(R.drawable.room_thumbnails)
                        .into(view);
            }else{

//                view.setImageResource(R.drawable.room_thumbnails);
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
           loadRoundImage(fragment.getActivity(),url,radius,defaultId,view);
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

    /**
     * convert Bitmap to byte array
     *
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     *
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     *
     * @param b
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     *
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     *
     * @param b
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }

    /**
     * scale image
     *
     * @param org
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
    }

    /**
     * scale image
     *
     * @param org
     * @param scaleWidth  sacle of width
     * @param scaleHeight scale of height
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    static int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap getCompressedBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 根据图片路径返回缩略图Bitmap
     */
    public static Bitmap getBitmapThumbByPath(String path) {
        Bitmap bitmap;
        int angle = readOrientation(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = computeSampleSize(options, -1, 500 * 500);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        bitmap = BitmapFactory.decodeFile(path, options);//根据Path读取资源图片
        if (angle != 0) {
            // 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(angle); // 旋转angle度
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                    m, true);// 从新生成图片
            if (newBitmap != bitmap) {
                bitmap.recycle();
            }
            return newBitmap;
        }
        return bitmap;
    }

    /**
     * 读取图片的翻转角度
     */
    public static int readOrientation(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 计算缩略图所需的inSampleSize
     *
     * @param options        原本Bitmap的options
     * @param minSideLength  希望生成的缩略图的宽高中的较小的值
     * @param maxNumOfPixels 希望生成的缩量图的总像素
     */
    private static int computeSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    /**
     * 计算缩略图所需的inSampleSize
     *
     * @param options        原本Bitmap的options
     * @param minSideLength  希望生成的缩略图的宽高中的较小的值
     * @param maxNumOfPixels 希望生成的缩量图的总像素
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }


    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
//    Build.VERSION_CODES.KITKAT 19
    @TargetApi(19)
    public static String getAbsoluteImagePath(final Context context, final Uri uri) {
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context, uri)) {
            if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * 根据图片路径返回缩略图Bitmap
     */
    public static Bitmap getBitmapThumbByPathNoRotate(String path) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = computeSampleSize(options, -1, 1080 * 1080);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        bitmap = BitmapFactory.decodeFile(path, options);//根据Path读取资源图片
        return bitmap;
    }

    /**
     * 保存bitmap
     *
     * @param bm      图片
     * @param picName 文件名
     * @return 文件路劲
     */
    public static String saveBitmap(Bitmap bm, String picName) {
        File f = new File(FileConstants.getImageSaveFilePath());
        if (!f.exists()) {
            f.mkdir();
        }
        File file = new File(f, picName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                Log.i("IMAGE", "已经保存");
                return file.getPath();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return file.getPath();

    }

    /**
     * 保存bitmap并通知系统相册更新图片
     *
     * @param bm      图片
     * @param picName 文件名
     * @return 文件路劲
     */
    public static String saveBitmap(final Context context, Bitmap bm, String picName) {
        File f = new File(FileConstants.getImageSaveFilePath());
        if (!f.exists()) {
            f.mkdir();
        }
        final File file = new File(f, picName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                Log.i("IMAGE", "已经保存");

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri uri = Uri.fromFile(file);
                        intent.setData(uri);
                        context.sendBroadcast(intent);
                    }
                });

                return file.getPath();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return file.getPath();

    }
}
