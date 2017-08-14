package com.chris.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: zijiefeng Date: 13-6-14 Time: 下午5:57 Comment:
 */
public class AppUtil {

	/**
	 * 是否向网络请求数据, 当前系统采用轮循方式载入数据, 流量会比较大 所以在夜间12点到早上6点, 不进行数据轮询
	 *
	 * @return true:可以访问网络数据 false:不
	 */
	public static boolean shouldRequestData() {
		int hour = new Date().getHours();
		return !(hour >= 0 && hour < 6);
	}




	public static String priceOfValue(long value) {
		if (value % 100 == 0) {
			return String.format("%d", value / 100);
		}

		if (value % 10 == 0) {
			return String.format("%.1f", ((float) value) / 100);
		}

		return String.format("%.2f", ((float) value) / 100);
	}
	
	public static String priceOfDoubleValue(double value) {
		if (value % 100 == 0) {
			return String.format("%.0f", value / 100);
		}

		if (value % 10 == 0) {
			return String.format("%.1f", ((float) value) / 100);
		}

		return String.format("%.2f", ((float) value) / 100);
	}

	public static String priceOfValue2Double(double value) {
		if (value % 1.0 == 0) {
			return String.valueOf((long) value);
		}
		return String.valueOf(Math.round(value * 10) / 10.0);
	}

	public static String priceOfValue2Double2(long value) {
		String strValue = priceOfValue(value);
		double douValue = Double.parseDouble(strValue);
		if (douValue % 1.0 == 0) {
			return String.valueOf((long) douValue);
		}
		return String.valueOf(Math.round(douValue * 100) / 100.00);
	}

	public static String distanceOfValue(double distance) {
		if (distance > 0 && distance < 1000) {
			return String.format("%sm", distance);
		} else if (distance >= 1000 && distance <= 50000) {
			return String.format("%skm", String.format("%.2f", distance / 1000));
		} else {
			return "";
		}
	}

	public static Date getDefaultCheckinDate() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (calendar.get(Calendar.HOUR_OF_DAY) >= 23) {
			calendar.add(Calendar.DATE, 1);
			return calendar.getTime();
		} else {
			return date;
		}
	}

	public static Date getDefaultCheckoutDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		if (cal.get(Calendar.HOUR_OF_DAY) >= 23) {
			cal.add(Calendar.DATE, 2);
			return cal.getTime();
		} else {
			cal.add(Calendar.DATE, 1);
			return cal.getTime();
		}
	}

	/**
	 * 计算两个日期间隔的天数
	 * @param srcDate
	 * @param tarDate
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static int getIntervalDate(String srcDate, String tarDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long to = 0;
		long from = 0;
		try {
			to = df.parse(srcDate).getTime();
			from = df.parse(tarDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int interval = (int)(to - from) / (1000 * 60 * 60 * 24);
		return interval;
	}

	/**
	 * 判断一个double是否为int类型
	 * 
	 * @param amount
	 * @return
	 */
	public static boolean isIntType(double amount) {
		if (TextUtils.isEmpty(String.valueOf(amount))) {
			return false;
		}
		/*String amounts = String.valueOf(amount);
		if(amounts.startsWith("0")) {
			return false;
		}*/
		if (Math.round(amount) == amount) {
			return true;
		}
		return false;
	}
	
	public static String FILE_SERIALIZE_NAME_POWER = "file_power_page";


	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}



	/**
	 * 资产拷贝的工具类
	 *
	 *            文件名称
	 * @param destPath
	 *            拷贝的目标路径
	 * @return 文件对象.
	 */
	public static File copy(Context context, String fileName, String destPath) {
		Log.i("yyc","context:"+context);
		try {
			InputStream is = context.getAssets().open(fileName);
			Log.i("yyc","destPath:"+destPath);
			File tempFile = new File("/data/data/"+ context.getPackageName() +"/shared_prefs");
			if(!tempFile.exists()){
				tempFile.mkdirs();
//				file.createNewFile();
			}
			File file = new File(destPath);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;

			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			fos.close();
			is.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("yyc","Exception:"+e.getLocalizedMessage());
			return null;
		}

	}


	/**
	 * 判断是否有SD卡
	 */
	public static boolean sdcardAvailable() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取sd卡可用空间 MIB单位
	 *
	 * @return
	 */
	public static long getAvailaleSize() {

		File path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		// return availableBlocks * blockSize;
		// (availableBlocks * blockSize)/1024 KIB 单位
		long size = (availableBlocks * blockSize) / 1024 / 1024;// MIB单位
		return size;
	}

}
