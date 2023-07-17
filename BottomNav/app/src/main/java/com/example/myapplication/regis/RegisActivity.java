package com.example.myapplication.regis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;

public class RegisActivity extends AppCompatActivity {

    EditText btnContinue1;
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        btnContinue1 = findViewById(R.id.btnContinue1);
        txtLogin = findViewById(R.id.txtLogin);

        btnContinue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toNext =  new Intent(RegisActivity.this, PhoneNumActivity.class);
                startActivity(toNext);
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(RegisActivity.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });
    }
}