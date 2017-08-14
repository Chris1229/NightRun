/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chris.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
	/**
	 * 保存Preference的name
	 */
	public static final String PREFERENCE_NAME = "jinyiwen_sp";
	private static SharedPreferences mSharedPreferences;

	/**
	 * 保存字符串
	 */
	public static void saveString(Context context, String key, String value) {
		if (mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
					Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putString(key, value).commit();
	}

	public static String getString(Context context, String key) {
		if (mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
					Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getString(key, "");
	}
	/**
	 * 保存boolean
	 */
	public static void saveBoolean(Context context, String key, boolean value) {
		if (mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
					Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putBoolean(key, value).commit();
	}
	
	public static boolean getBoolean(Context context, String key) {
		if (mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
					Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getBoolean(key, false);
	}

	/**
	 * 保存int
	 */
	public static void saveInteger(Context context, String key, int value) {
		if (mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
					Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putInt(key, value).commit();
	}

	public static Integer getInteger(Context context, String key) {
		if (mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
					Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getInt(key,-1);
	}

	/**
	 * 保存long
	 */
	public static void saveLong(Context context, String key, long value) {
		if (mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
					Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putLong(key, value).commit();
	}

	public static long getLong(Context context, String key) {
		if (mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
					Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getLong(key,-1);
	}
}
