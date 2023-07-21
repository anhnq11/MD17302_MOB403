package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.regis.RegisActivity;

public class RouteActivity extends AppCompatActivity {

    EditText btnToLogin, btnToRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        btnToLogin = findViewById(R.id.btnToLogin);
        btnToRegis = findViewById(R.id.btnToRegis);

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RouteActivity.this, LoginActivity.class));
            }
        });

        btnToRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RouteActivity.this, RegisActivity.class));
            }
        });
    }
}