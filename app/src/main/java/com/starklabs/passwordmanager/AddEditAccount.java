package com.starklabs.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AddEditAccount extends AppCompatActivity {

    TextInputEditText name, pass;
    MaterialButton suggest, save;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_acount);

        name = findViewById(R.id.account_name);
        pass = findViewById(R.id.new_account_pass);

        suggest = findViewById(R.id.suggest_account_pass);
        save = findViewById(R.id.save_account_pass);

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountName = name.getText().toString().trim();
                if(accountName.isEmpty()) {
                    Toast.makeText(AddEditAccount.this, "Suggestion Not Available!", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                SharedPreferences sp = getSharedPreferences("MasterPassword", MODE_PRIVATE);
                String masterPass = sp.getString("MasterPassHash", null);
                if(masterPass!=null)
                {
                    String suggestion = getHash(masterPass+accountName);
                    pass.setText(suggestion);
                }
                else
                {
                    Toast.makeText(AddEditAccount.this,"How do you get there? Am I a bad programmer!"
                    , Toast.LENGTH_LONG).show();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the account name and password to the database
                String nameVal = name.getText().toString().trim();
                String passVal = pass.getText().toString().trim();

                if(nameVal.isEmpty() || passVal.isEmpty())
                {
                    Toast.makeText(AddEditAccount.this, "Fill all fields", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                // Save to sqlite database
                dbManager = new DatabaseManager(AddEditAccount.this);
                try {
                    dbManager.open();
                    dbManager.insert(nameVal, passVal);
                    Toast.makeText(AddEditAccount.this, "Saved!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                catch (SQLException e)
                {
                    Toast.makeText(AddEditAccount.this, "Database failure!", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

    }

    private String getHash(String masterPassValue) {
        String masterPassHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte [] messageDigest = md.digest(masterPassValue.getBytes());
            BigInteger bigInteger = new BigInteger(1, messageDigest);
            masterPassHash = bigInteger.toString(16);
            while(masterPassHash.length()<32)
                masterPassHash = "0"+masterPassHash;
        }
        catch (NoSuchAlgorithmException e)
        {
            Toast.makeText(AddEditAccount.this,"MD5 Algorithm error!", Toast.LENGTH_LONG).show();
        }
        return masterPassHash;
    }
}
