package com.arunrk.boradcost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "mycontact.db";
    public static final int DB_VERSION = 1;
    private static final String KEY_TBL_NAME = "con";
    public static final String KEY_MOBILE_NUM = "mobile_number";
    public static final String KEY_ID = "id";
    public static final String CREATE = "create table " + KEY_TBL_NAME +
                " ("+ KEY_ID +" integer primary key autoincrement," + KEY_MOBILE_NUM + " text);";
    public static final String DROP = "drop table if exists " + KEY_TBL_NAME;
    public static final String UPDATE_UI_FILTER = "com.arunrk.boradcost.UPDATE_UI";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP);
        onCreate(db);
    }

    public void save(SQLiteDatabase db, String number) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_MOBILE_NUM, number);
        db.insert(KEY_TBL_NAME, null, cv);
    }

    public Cursor readNumber(SQLiteDatabase db) {
        String[] projection = {KEY_ID, KEY_MOBILE_NUM};
        return (db.query(KEY_TBL_NAME
                , projection
                , null
                , null
                , null
                , null
                , null));
    }


}
