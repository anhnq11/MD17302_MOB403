package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.regis.RegisActivity;

public class LoginActivity extends AppCompatActivity {

    EditText btnLogin;
    TextView txtRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegis = findViewById(R.id.txtRegis);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMainAct = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(toMainAct);
            }
        });

        txtRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegis = new Intent(LoginActivity.this, RegisActivity.class);
                startActivity(toRegis);
            }
        });
    }
}