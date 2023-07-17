package com.example.myapplication.regis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class BirthActivity extends AppCompatActivity {

    EditText btnRegis;
    TextView back2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth);

        btnRegis = findViewById(R.id.btnRegis);
        back2 = findViewById(R.id.txtBack2);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMainAct = new Intent(BirthActivity.this, MainActivity.class);
                startActivity(toMainAct);
            }
        });

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(BirthActivity.this, PhoneNumActivity.class);
                startActivity(back);
            }
        });
    }
}