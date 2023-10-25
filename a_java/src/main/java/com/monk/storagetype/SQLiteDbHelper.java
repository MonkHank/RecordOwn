package com.monk.storagetype;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author monk
 * @date 2019-06-01
 */
public class SQLiteDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_STUDENT = "students";
    private static final String STUDENTS_CREATE_TABLE_SQL = "create table" + TABLE_STUDENT + "("
            + "id integer primary key autoincrement,"
            + "name varchar(20) not null,"
            + "tel_no varchar(11) not null,"
            + "cls_id integer not null"
            + ");";

    public SQLiteDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STUDENTS_CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        if (!db.isReadOnly()) {
            // 启动外键
            db.execSQL("PRAGMA foreign_keys = 1;");

            //或者这样写
//            String query = String.format("PRAGMA foreign_keys = %s", "ON");
//            db.execSQL(query);
        }
    }
}
