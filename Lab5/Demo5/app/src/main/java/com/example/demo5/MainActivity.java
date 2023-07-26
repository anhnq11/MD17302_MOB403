package com.example.demo5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2;
    TextView txt1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        txt1 = findViewById(R.id.textView);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVolleyData();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJsonObjects();
            }
        });

    }
        public void getVolleyData(){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://www.google.com/";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            txt1.setText("Rs: " + response.substring(0, 1000));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    txt1.setText(error.getMessage());
                }
            });
            requestQueue.add(stringRequest);
        }

        public void getJsonObjects(){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://64b93df279b7c9def6c0cca0.mockapi.io/users/users";
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    String rs = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            String username = object.getString("username");
                            String email = object.getString("email");
                            rs += (i + 1) + " - " + username + " - " + email + "\n\n";
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    txt1.setText(rs);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(request);
        }
}