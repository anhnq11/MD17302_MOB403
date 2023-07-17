package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.GsonBuildConfig;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtPrice, edtDesc;
    Button btnAdd, btnLoad;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDesc = findViewById(R.id.edtDesc);
        btnAdd = findViewById(R.id.btnAdd);
        btnLoad = findViewById(R.id.btnLoad);
    }

    String rs = "";
    ArrayList<Product> listProducts = new ArrayList<>();
    Product product = new Product();
    void inserData(){
        product.setName(edtName.getText().toString().trim());
        product.setPrice(Double.parseDouble(edtPrice.getText().toString().trim()));
        product.setDescription(edtDesc.getText().toString().trim());

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://batdongsanabc.000webhostapp.com/mob403lab4/")
                .addConverterFactory()
                .build();
    }
}