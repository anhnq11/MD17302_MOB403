package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.ApiServices;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.regis.RegisActivity;
import com.example.myapplication.ui.home.HomeFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText btnLogin, edtUsername, edtPassword;
    TextView txtLoginErr, txtRegis;

    String userName, passWord;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegis = findViewById(R.id.txtRegis);
        edtUsername = findViewById(R.id.edt_login_Username);
        edtPassword = findViewById(R.id.edt_login_Password);
        txtLoginErr = findViewById(R.id.txtLoginErr);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName = edtUsername.getText().toString().trim();
                passWord = edtPassword.getText().toString().trim();

                if (checkInputData()){
                    ApiServices.apiServices.login(userName, passWord).enqueue(new Callback<userModel>() {
                        @Override
                        public void onResponse(Call<userModel> call, Response<userModel> response) {
                            if (response.code() == 404){
                                txtLoginErr.setVisibility(View.VISIBLE);
                                txtLoginErr.setText("* Tên đăng nhập hoặc mật khẩu không đúng!");
                            }
                            if (response.code() == 200){
                                Log.d("TAG", "Success!" + response.body().getFullname());
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeFragment.class));
                            }
                        }

                        @Override
                        public void onFailure(Call<userModel> call, Throwable t) {

                        }
                    });
                }
//
//                Intent toMainAct = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(toMainAct);
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
        private boolean checkInputData(){
            boolean check = true;
            txtLoginErr.setVisibility(View.GONE);

            if (userName.length() == 0){
                check = false;
                txtLoginErr.setVisibility(View.VISIBLE);
                txtLoginErr.setText("* Vui lòng nhập tên đăng nhập!");
            }
            else {
                if (passWord.length() == 0){
                    check = false;
                    txtLoginErr.setVisibility(View.VISIBLE);
                    txtLoginErr.setText("* Vui lòng nhập mật khẩu!");
                }
            }

            return check;
        }
}
