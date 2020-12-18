package com.starklabs.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    TextInputEditText masterPass;
    MaterialButton submitMasterPass;
    TextView setMasterPass, editMasterPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        masterPass = findViewById(R.id.master_password);
        submitMasterPass = findViewById(R.id.submit_master_pass);
        setMasterPass = findViewById(R.id.set_master_pass);
        editMasterPass = findViewById(R.id.change_master_pass);

        // On Button Click - Check if password is set, if not display a message, else check if password hash is correct.
        submitMasterPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isMasterPassSet = checkMasterPass();
                if(isMasterPassSet)
                {
                    SharedPreferences sp = getSharedPreferences("MasterPassword",MODE_PRIVATE);
                    String masterPassHash = sp.getString("MasterPassHash",null);
                    try {
                        String masterPassValue = masterPass.getText().toString().trim();
                        if(masterPassValue.isEmpty())
                        {
                            Toast.makeText(MainActivity.this,"Please enter a value",Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        String hashedMasterPass = getHash(masterPassValue);
                        if(hashedMasterPass.equals(masterPassHash))
                        {
                            Intent intent = new Intent(MainActivity.this, TotalActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Wrong password entered",Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                    catch (NullPointerException e)
                    {
                        Toast.makeText(MainActivity.this,"Error converting text!", Toast.LENGTH_LONG).show();
                    }
                }

                else
                {
                    Toast.makeText(MainActivity.this, "Please set the password", Toast.LENGTH_LONG).show();
                }
            }
        });

        // TO set Master Password
        setMasterPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetMasterPass.class);
                startActivity(intent);
            }
        });

        editMasterPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Implemetation Pending", Toast.LENGTH_SHORT)
                        .show();
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
            Toast.makeText(MainActivity.this,"MD5 Algorithm error!", Toast.LENGTH_LONG).show();
        }
        return masterPassHash;
    }

    private Boolean checkMasterPass() {
        // Method to check if master password has already been set

        SharedPreferences sp = getSharedPreferences("MasterPassword",MODE_PRIVATE);
        try {
            String masterPassHash = sp.getString("MasterPassHash",null); // null - Not Present
            if(masterPassHash==null)
                return false;
            else
                return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
