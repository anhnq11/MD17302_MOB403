package com.example.demo5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnLoad;
    RecyclerView txtResult;

    EditText edtUsername, edtEmail, edtPass;

    String url = "https://64b93df279b7c9def6c0cca0.mockapi.io/users/users";

    ArrayList<Model> userList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnLoad = findViewById(R.id.btnLoad);
        txtResult = findViewById(R.id.txtResult);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPassword);

        userList = new ArrayList<>();
        getJsonObjects();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        txtResult.setLayoutManager(linearLayoutManager);
        Adapter adapter = new Adapter(getApplicationContext(), userList);
        txtResult.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("TAG", "onResponse: " + response);
                                if (response != null){
                                    Toast.makeText(MainActivity.this, "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
                                    edtUsername.setText(null);
                                    edtEmail.setText(null);
                                    edtPass.setText(null);
                                    getJsonObjects();
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "API Connection Error!", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", edtEmail.getText().toString().trim());
                        paramV.put("username", edtUsername.getText().toString().trim());
                        paramV.put("passwd", edtPass.getText().toString().trim());
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJsonObjects();
            }
        });

    }

        public void getJsonObjects(){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("TAG", "onResponse: " + response.length());
                    userList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            Model user = new Model(object.getString("id"), object.getString("username"), object.getString("passwd"), object.getString("email"));
                            userList.add(user);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            txtResult.setLayoutManager(linearLayoutManager);
                            Adapter adapter = new Adapter(getApplicationContext(), userList);
                            adapter.notifyDataSetChanged();
                            txtResult.setAdapter(adapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TAG", "Connection Error!");
                }
            });
            requestQueue.add(request);
        }
}