package com.chris.common.utils;

import java.security.MessageDigest;

public class Md5Utils {

	public static String Md5(String userPwd) {
		String str = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(userPwd.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
			System.out.println("result: " + buf.toString());// 32位的加密
			System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}
