package com.zenzile.attendance.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 211282278 on 10/2/2014.
 */
public class DBAdapter extends SQLiteOpenHelper {

    // Set Table name
    public final  static String TABLE_STUDENT = "student";

    // Set Collums
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STUDENT_NO="studentno";
    public static final String COLUMN_INITIALS = "initials";
    public static final String COLUMN_SURNAME="surname";
    public static final String COLUMN_TIME="time";

    private static final String DATABASE_NAME="student.db";
    private static final int DATABASE_VERSION=1;

    // DATABASE CREATION SQL
    private static final String CREATE_STUDENT_TABLE= " create table IF NOT EXISTS "
            + TABLE_STUDENT + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_STUDENT_NO + " text not null, "
            + COLUMN_INITIALS + " text not null, "
            + COLUMN_SURNAME + " text not null, "
            + COLUMN_TIME + " date not null); ";

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("", "database");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(DBAdapter.class.getName(), "upgrading the Database from Version " + oldVersion +
        		" to " + newVersion + " Which will destroy old data");
        database.execSQL(" DROP IF EXISTS "+ TABLE_STUDENT);
        onCreate(database);
    }
}
