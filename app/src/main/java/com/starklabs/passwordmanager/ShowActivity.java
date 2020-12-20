package com.starklabs.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShowActivity extends AppCompatActivity {

    TextInputEditText accountName, accountPass;
    MaterialButton suggest, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        accountName = findViewById(R.id.account_name_show);
        accountPass = findViewById(R.id.new_account_pass_show);

        suggest = findViewById(R.id.suggest_account_pass_show);
        save = findViewById(R.id.save_account_pass_show);

        final int type = getIntent().getIntExtra("Type", 0);
        final String name = getIntent().getStringExtra("name");
        final String pass = getIntent().getStringExtra("pass");

        accountName.setText(name);
        accountPass.setText(pass);

        switch (type)
        {
            case 1:
                accountName.setEnabled(false);
                accountPass.setEnabled(false);
                suggest.setEnabled(false);
                save.setText("Return");
                break;
            case 2:
                break;
            case 3:
                accountName.setEnabled(false);
                accountPass.setEnabled(false);
                suggest.setEnabled(false);
                save.setText("Delete");
                break;
            default:
                Toast.makeText(this, "Something looks wrong", Toast.LENGTH_SHORT).show();
        }

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = accountName.getText().toString().trim();
                if(name.isEmpty()) {
                    Toast.makeText(ShowActivity.this, "Suggestion Not Available!", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                SharedPreferences sp = getSharedPreferences("MasterPassword", MODE_PRIVATE);
                String masterPass = sp.getString("MasterPassHash", null);
                if(masterPass!=null)
                {
                    String suggestion = getHash(masterPass+accountName);
                    accountPass.setText(suggestion);
                }
                else
                {
                    Toast.makeText(ShowActivity.this,"How do you get there? Am I a bad programmer!"
                            , Toast.LENGTH_LONG).show();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(type)
                {
                    case 1:
                        finish();
                        break;
                    case 2:
                        String nameNew = accountName.getText().toString().trim();
                        String newPass = accountPass.getText().toString().trim();
                        if(nameNew.isEmpty() || newPass.isEmpty())
                        {
                            Toast.makeText(ShowActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try
                        {
                            DatabaseManager dbManager = new DatabaseManager(ShowActivity.this);
                            dbManager.open();
                            System.out.println("Successfully here 1");
                            dbManager.update(nameNew, newPass, name, pass);
                            System.out.println("Successfully here 2");
                            dbManager.close();
                            Toast.makeText(ShowActivity.this, "Change successful", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(ShowActivity.this, "Something went wrong", Toast.LENGTH_SHORT)
                                    .show();

                        }
                        break;
                    case 3:
                        try{
                            DatabaseManager dbManager = new DatabaseManager(ShowActivity.this);
                            dbManager.open();
                            dbManager.delete(name, pass);
                            dbManager.close();
                            Toast.makeText(ShowActivity.this, "Delete successful", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(ShowActivity.this, "Something went wrong", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        break;
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
            Toast.makeText(ShowActivity.this,"MD5 Algorithm error!", Toast.LENGTH_LONG).show();
        }
        return masterPassHash;
    }
}
