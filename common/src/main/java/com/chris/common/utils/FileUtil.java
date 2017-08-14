package com.chris.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA. User: zijiefeng Date: 13-5-28 Time: 下午4:05
 */
public class FileUtil {

	/**
	 * 删除文件夹
	 *
	 * @param folderPath
	 *            String 文件夹绝对路径
	 * @param filterFolder
	 *            String 不需要删除的文件夹名 默认可为null
	 */
	public void delFolder(String folderPath, String filterFolder) {
		delAllFile(folderPath, filterFolder); // 删除完里面所有内容
		File file = new File(folderPath);
		file.delete(); // 删除空文件夹
	}

	public void writeToFile(byte[] bytes, String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream("/sdcard/" + fileName);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 *
	 * @param path
	 *            String 文件夹绝对路径
	 * @param filterFolder
	 *            String 不需要删除的文件夹名 默认可为null 格式："[folderName2/]folderName1/"
	 */
	private void delAllFile(String path, String filterFolder) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				if (filterFolder != null && filterFolder.endsWith(temp.getName() + File.separator)) {
					continue;
				}
				delAllFile(path + "/" + tempList[i], filterFolder);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i], filterFolder);// 再删除空文件夹
			}
		}
	}

	public static String getFromAssets(Context context, String fileName) {
		StringBuilder result = new StringBuilder();
		try {
			InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			while ((line = bufReader.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonTokener(result.toString());
	}

	/**
	 * 去除bom头信息
	 * 
	 * @param json
	 * @return
	 */
	public static String jsonTokener(String json) {
		// consume an optional byte order mark (BOM) if it exists
		if (json != null && json.startsWith("\ufeff")) {
			String result = json.substring(1);
			return result;
		}
		return json;
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

	/**
	 *
	 * @param bitmap
	 * @param absolutePath
	 */
	public static boolean saveBitmapFile(Bitmap bitmap, String absolutePath) {
		// Bitmap compressBitmap = compressImage(bitmap);
		File file = new File(absolutePath);
		if (file.exists()) {
			return true;
		}
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
			System.out.println("file>size:" + file.length());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * 获取指定文件大小
	 * @param filePath
	 * @return KB
	 * @throws Exception
	 */
	public static double getFileSize(String filePath){
		File file=new File(filePath);
		double fileSize = 0;
		if (file.exists() && !file.isDirectory() ){
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				long size  = fis.available();
				DecimalFormat df = new DecimalFormat("#.00");
				fileSize = Double.valueOf(df.format((double) size / 1024));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
//            file.createNewFile();
			Log.e("获取文件大小","文件不存在!");
		}
		return fileSize;
	}

}
