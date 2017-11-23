package com.zt.nightrun.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zt.db.DaoMaster;
import com.zt.db.MessageDao;

import org.greenrobot.greendao.database.Database;

/**
 * 作者：by chris
 * 日期：on 2017/9/20 - 上午9:16.
 * 邮箱：android_cjx@163.com
 */

public class MyOpenHelper extends DaoMaster.OpenHelper{

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //操作数据库的更新 有几个表升级都可以传入到下面
        MigrationHelper.getInstance().migrate(db,MessageDao.class);
    }

}
