package com.starklabs.passwordmanager;

import java.util.ArrayList;

public class Accounts
{
    private String accountName, password;

    public Accounts(String name, String pass)
    {
        accountName = name;
        password = pass;
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

    public static ArrayList<Accounts> getAccountsList()
    {
        ArrayList<Accounts> accounts = new ArrayList<>();
        // Add members from data base here
        // Accounts are accessed from database

        return accounts;
    }
}
