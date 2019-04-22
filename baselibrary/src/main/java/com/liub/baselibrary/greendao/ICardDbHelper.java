package com.liub.baselibrary.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.liub.dao.bean.DaoMaster;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

/**
 * Create by liub on 2019/4/18
 * Describe:
 */
public class ICardDbHelper extends DatabaseOpenHelper {
    private static String TAG = "ICardDbHelper";

    //数据库名字
    private static final String dbName = "IccCard.db";

    public ICardDbHelper(Context context, int version) {
        //数据库版本根据versioncode
        super(context, dbName, version);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
        DaoMaster.createAllTables(db,false);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
//        Lmsg.e(oldVersion+"---new:"+newVersion);
//        if (oldVersion<10){
//            MigrationHelper.getInstance().migrate(db,UserInfoDao.class);
//        }
    }
}
