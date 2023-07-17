package com.example.myapplication.regis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

public class PhoneNumActivity extends AppCompatActivity {

    EditText btnContinue2;
    TextView back1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_num);

        btnContinue2 = findViewById(R.id.btnContinue2);
        back1 = findViewById(R.id.txtBack1);

        btnContinue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toNext = new Intent(PhoneNumActivity.this, BirthActivity.class);
                startActivity(toNext);
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(PhoneNumActivity.this, RegisActivity.class);
                startActivity(back);
            }
        });
    }
}