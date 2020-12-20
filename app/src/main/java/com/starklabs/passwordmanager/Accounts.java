package com.starklabs.passwordmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;

public class Accounts
{
    private String accountName, password;
    private Context mContext;

    public Accounts(String name, String pass)
    {
        accountName = name;
        password = pass;
    }

    public Accounts(Context context)
    {
        mContext = context;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Accounts> getAccountsList()
    {
        ArrayList<Accounts> accounts = new ArrayList<>();
        // Add members from data base here
        // Accounts are accessed from database
        try
        {
            DatabaseManager dbManager = new DatabaseManager(mContext);
            dbManager.open();
            Cursor cursor = dbManager.fetch();
            if(cursor!=null)
            {
                while(cursor.moveToNext())
                {
                    String accName = cursor.getString(0);
                    String pass = cursor.getString(1);
                    Accounts tempObj = new Accounts(accName, pass);
                    accounts.add(tempObj);
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(mContext, "Error fetching the data!",Toast.LENGTH_SHORT).show();
        }
        return accounts;
    }
}
