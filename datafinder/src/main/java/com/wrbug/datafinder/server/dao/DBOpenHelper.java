package com.wrbug.datafinder.server.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import org.greenrobot.greendao.database.Database;

public class DBOpenHelper extends DaoMaster.OpenHelper {
    private static final String DB_NAME = "data_finder.db";


    public DBOpenHelper(Context context) {
        super(context, DB_NAME);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            super.onDowngrade(db, oldVersion, newVersion);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("GreenDAO", "Upgrading marketing db schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        DaoMaster.dropAllTables(db, true);
        DaoMaster.createAllTables(db, true);
    }

}