package com.starklabs.passwordmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


    // Adding menu to the activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        switch(itemId)
        {
            case R.id.about:
                // Intent to initialize about activity
                Intent intent = new Intent(TotalActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_exit:
                // To exit the current activity
                finish();
                return true;

                default:
                    return super.onOptionsItemSelected(item);

        }


    }

    private void setRecyclerView() {
        // Gets the list of accounts and updates on the Recycler view through adapter

        accounts = new Accounts(getApplicationContext()).getAccountsList();
        if(accounts==null)
            return;
        AccountAdapter adapter = new AccountAdapter(accounts, getApplicationContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }
}
