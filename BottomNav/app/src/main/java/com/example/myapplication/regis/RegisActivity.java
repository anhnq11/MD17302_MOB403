package com.example.myapplication.regis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.API.ApiServices;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityRegisBinding;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisActivity extends AppCompatActivity {

    private ActivityRegisBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        binding = ActivityRegisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegisContinue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname = binding.edtRegisFullname.getText().toString().trim();
                String username = binding.edtRegisUsername.getText().toString().trim();
                String password = binding.edtRegisPassword.getText().toString().trim();
                String rePassword = binding.edtRegisRePassword.getText().toString().trim();

//                Validation Form
                if (checkInputData(username, fullname, password, rePassword)){
//                    Check is valid Username
                    ApiServices.apiServices.checkRegis(username).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if (response.code() == 404){
                                binding.edtRegisErr.setVisibility(View.VISIBLE);
                                binding.edtRegisErr.setText("* Tài khoản đã được sửa dụng");
                                binding.edtRegisUsername.setFocusable(true);
                            }
                            if (response.code() == 200){
                                userModel user = new userModel(fullname, username, password);
                                Gson gson = new Gson();
                                String userData = gson.toJson(user);
                                Intent toNext =  new Intent(RegisActivity.this, PhoneNumActivity.class);
                                toNext.putExtra("data", userData);
                                startActivity(toNext);
                            }
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Log.d("TAG", "Connection Error!");
                        }
                    });
                }
            }
        });

        binding.txtRegisLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(RegisActivity.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });
    }

    private boolean checkInputData(String username, String fullname, String password, String rePassword){
        boolean check = true;
        binding.edtRegisErr.setVisibility(View.GONE);

        if (fullname.length() == 0){
            binding.edtRegisErr.setText("* Vui lòng nhập tên người dùng!");
            binding.edtRegisErr.setVisibility(View.VISIBLE);
            binding.edtRegisFullname.setFocusable(true);
            return false;
        }

        if (username.length() == 0){
            binding.edtRegisErr.setText("* Vui lòng nhập tên đăng nhập!");
            binding.edtRegisErr.setVisibility(View.VISIBLE);
            binding.edtRegisUsername.setFocusable(true);
            return false;
        }

        if (password.length() == 0){
            binding.edtRegisErr.setText("* Vui lòng nhập mật khẩu!");
            binding.edtRegisErr.setVisibility(View.VISIBLE);
            binding.edtRegisPassword.setFocusable(true);
            return false;
        }

        if (rePassword.length() == 0){
            binding.edtRegisErr.setText("* Vui lòng nhập xác nhận mật khẩu!");
            binding.edtRegisErr.setVisibility(View.VISIBLE);
            binding.edtRegisRePassword.setFocusable(true);
            return false;
        }

        if (!password.equals(rePassword)){
            binding.edtRegisErr.setText("* Xác thực mật khẩu không chính xác!");
            binding.edtRegisErr.setVisibility(View.VISIBLE);
            binding.edtRegisRePassword.setFocusable(true);
            return false;
        }


        return check;
    }
}