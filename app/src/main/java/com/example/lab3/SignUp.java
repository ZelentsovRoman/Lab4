package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    Button button3;
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        database = new Database(this);
    }
    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            StringBuilder passBuilder = new StringBuilder(new BigInteger(1, mdEnc.digest()).toString(16));
            while (passBuilder.length() < 32) {
                passBuilder.insert(0, "0");
            }
            pass = passBuilder.toString();
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }
    public void onClick(View view) {
        if ((editTextTextEmailAddress.getText().length() > 0) && (editTextTextPassword.getText().length() > 0)) {
            boolean status = database.addUser(convertPassMd5(editTextTextEmailAddress.getText().toString()),convertPassMd5(editTextTextPassword.getText().toString()));
            if(status) {
                Intent SignUp = new Intent(SignUp.this, MainActivity.class);
                startActivity(SignUp);
            }
        } else {
            Toast.makeText(getApplicationContext(),"Please Insert login and password", Toast.LENGTH_LONG).show();
        }
    }
}