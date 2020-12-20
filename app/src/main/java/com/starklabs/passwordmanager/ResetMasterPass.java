package com.starklabs.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class ResetMasterPass extends AppCompatActivity {

    Spinner spinner;
    TextInputEditText ans, newPass;
    MaterialButton submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_master_pass);

        spinner = findViewById(R.id.security_ques_spinner);
        ans = findViewById(R.id.security_ques_text);
        submit = findViewById(R.id.submit_security_ques);
        newPass = findViewById(R.id.new_pass_tf);

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



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int secQues = spinner.getSelectedItemPosition();
                final String secAns = ans.getText().toString().trim();
                final String resetPass = newPass.getText().toString().trim();
                try {
                    if (secAns.isEmpty() || secQues <= 0 || resetPass.isEmpty()) {
                        Toast.makeText(ResetMasterPass.this, "Please fill in all fields!", Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }

                    SharedPreferences sp = getSharedPreferences("MasterPassword", MODE_PRIVATE);
                    int savedQues = sp.getInt("secQues", 0);
                    String savedAns = sp.getString("secAns", null);

                    if (savedQues != 0 && savedQues == secQues && savedAns.equals(secAns)) {
                        SharedPreferences.Editor editor = sp.edit();
                        String newPassHash = getHash(resetPass);
                        editor.putString("MasterPassHash", newPassHash);
                        editor.putString("secAns", secAns);
                        editor.putInt("secQues", secQues);
                        editor.apply();
                        Toast.makeText(ResetMasterPass.this, "Password updated!", Toast.LENGTH_SHORT)
                                .show();
                        finish();
                    } else {
                        Toast.makeText(ResetMasterPass.this, "Unmatched selections", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(ResetMasterPass.this, "Something went wrong!", Toast.LENGTH_SHORT)
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
            Toast.makeText(ResetMasterPass.this,"MD5 Algorithm error!", Toast.LENGTH_LONG).show();
        }
        return masterPassHash;
    }
}
