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

public class SetMasterPass extends AppCompatActivity {

    TextInputEditText setMasterPass;
    MaterialButton savePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_master_pass);

        setMasterPass = findViewById(R.id.set_master_password);
        savePass = findViewById(R.id.submit_set_master_pass);

        savePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String masterPass = setMasterPass.getText().toString().trim();
                    String passHash = getHash(masterPass);
                    SharedPreferences sp = getSharedPreferences("MasterPassword",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("MasterPassHash", passHash);
                    editor.apply();
                    Toast.makeText(SetMasterPass.this, "Password saved!",Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
                catch (NullPointerException e)
                {
                    Toast.makeText(SetMasterPass.this, "Unable to fetch password",Toast.LENGTH_SHORT)
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
            Toast.makeText(SetMasterPass.this,"MD5 Algorithm error!", Toast.LENGTH_LONG).show();
        }
        return masterPassHash;
    }
}
