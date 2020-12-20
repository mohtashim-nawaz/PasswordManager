package com.starklabs.passwordmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{

    public static final String TABLE_NAME = "Accounts";
    public static final String DB_NAME = "PasswordManager.db";
    public static final int DB_VERSION = 1;
    public static final String ACC_FIELD = "Account";
    public static final String PASS_FIELD = "Password";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ACC_FIELD + " text, " + PASS_FIELD
            + " text);";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // When upgrade is to be performed put code here
    }
}
