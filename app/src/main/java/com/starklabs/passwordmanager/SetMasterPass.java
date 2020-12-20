package com.starklabs.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class SetMasterPass extends AppCompatActivity {

    TextInputEditText setMasterPass, secAnsTF;
    MaterialButton savePass;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_master_pass);

        setMasterPass = findViewById(R.id.set_master_password);
        savePass = findViewById(R.id.submit_set_master_pass);
        spinner = findViewById(R.id.security_ques_spinner_set);
        secAnsTF = findViewById(R.id.set_master_secret);

        List<String> ques = new ArrayList<>();
        ques.add("Security Question");
        ques.add("Birthplace");
        ques.add("First School");
        ques.add("Pet Name");
        ques.add("Partner's Name");
        ques.add("Nickname");
        ques.add("Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ques);
        spinner.setAdapter(adapter);

        savePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String masterPass = setMasterPass.getText().toString().trim();
                    String passHash = getHash(masterPass);
                    int secQues = spinner.getSelectedItemPosition();
                    String secAns = secAnsTF.getText().toString().trim();

                    if(masterPass.isEmpty() || secAns.isEmpty() || secQues<=0)
                    {
                        Toast.makeText(SetMasterPass.this, "Please fill all fields", Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }

                    SharedPreferences sp = getSharedPreferences("MasterPassword",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("MasterPassHash", passHash);
                    editor.putString("secAns", secAns);
                    editor.putInt("secQues",secQues);
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
