package com.starklabs.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TotalActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView mRecyclerView;
    ArrayList<Accounts> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        fab = findViewById(R.id.add_new_account);
        mRecyclerView = findViewById(R.id.total_recycler_view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalActivity.this, AddEditAccount.class);
                startActivity(intent);
            }
        });

    }

    private void setRecyclerView() {
        // Gets the list of accounts and updates on the Recycler view through adapter

        accounts = Accounts.getAccountsList();
        AccountAdapter adapter = new AccountAdapter(accounts);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }
}
