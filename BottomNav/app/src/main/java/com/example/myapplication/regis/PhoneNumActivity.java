package com.example.myapplication.regis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityPhoneNumBinding;
import com.google.gson.Gson;

import org.w3c.dom.Text;

public class PhoneNumActivity extends AppCompatActivity {

    ActivityPhoneNumBinding  binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_num);

        Gson gson = new Gson();
        userModel user = gson.fromJson(getIntent().getStringExtra("data"), userModel.class);
        Log.d("TAG", "onCreate: " + user.getUsername());

        EditText btnContinue = findViewById(R.id.btnContinue);
        EditText edtPhoneNum = findViewById(R.id.edt_phonenum);
        TextView txtBack = findViewById(R.id.txtBack1);
        TextView txtPhoneNumErr = findViewById(R.id.txt_phonenum_err);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = edtPhoneNum.getText().toString();
                txtPhoneNumErr.setVisibility(View.GONE);
                if (phoneNum.length() != 0 && phoneNum.startsWith("0") && phoneNum.length() <= 13){
                    user.setEmail(phoneNum);
                    String userData = gson.toJson(user);
                    Intent intent =  new Intent(PhoneNumActivity.this, BirthActivity.class);
                    intent.putExtra("data", userData);
                    startActivity(intent);
                }
                else {
                    txtPhoneNumErr.setVisibility(View.VISIBLE);
                    txtPhoneNumErr.setText("Số điện thoại không hợp lệ!");
                }
            }
        });

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
}