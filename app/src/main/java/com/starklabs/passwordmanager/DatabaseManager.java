package com.starklabs.passwordmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager
{
    private SQLiteDatabase db;
    private Context mContext;
    private DatabaseHelper dbHelper;

    public DatabaseManager (Context c)
    {
        mContext = c;
    }


    // To open the database
    public DatabaseManager open() throws SQLException
    {
        dbHelper = new DatabaseHelper(mContext);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // To close the database
    public void close()
    {
        dbHelper.close();
    }

    // To insert the values into the fields
    public void insert(String accountName, String pass)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ACC_FIELD, accountName);
        contentValues.put(DatabaseHelper.PASS_FIELD, pass);
        db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    // To fetch values from database
    public Cursor fetch()
    {
        String [] data = new String[] {DatabaseHelper.ACC_FIELD, DatabaseHelper.PASS_FIELD};

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, data, null, null, null, null
        , null);

        if(cursor!=null)
                cursor.moveToFirst();
        return cursor;
    }

    public int update(String accName, String pass)
    {
        // Update code here
        return 0;
    }
}
