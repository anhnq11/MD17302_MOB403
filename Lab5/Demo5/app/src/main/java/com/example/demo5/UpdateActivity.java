package com.example.demo5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    EditText ed_username2, ed_passwd2, ed_email2;
    Button btn_update;
    String url = "https://64b93df279b7c9def6c0cca0.mockapi.io/users/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ed_username2 = findViewById(R.id.ed_username2);
        ed_passwd2 = findViewById(R.id.ed_passwd2);
        ed_email2 = findViewById(R.id.ed_email2);
        btn_update = findViewById(R.id.btn_update);

        Gson gson = new Gson();
        Model user = gson.fromJson(getIntent().getStringExtra("data"), Model.class);
        Log.d("TAG", "onCreate: " + user.getUsername());

        ed_username2.setText(user.getUsername());
        ed_email2.setText(user.getEmail());
        ed_passwd2.setText(user.getPasswd());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "/" + user.getId(),
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("TAG", "onResponse: " + response);
                                if (response != null){
                                    Toast.makeText(UpdateActivity.this, "Cập nhật tài khoản thành công!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                                }
                                else{
                                    Toast.makeText(UpdateActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateActivity.this, "API Connection Error!", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", ed_email2.getText().toString().trim());
                        paramV.put("username", ed_username2.getText().toString().trim());
                        paramV.put("passwd", ed_passwd2.getText().toString().trim());
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}