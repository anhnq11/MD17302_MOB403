package com.example.myapplication.regis;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.sharedPreferencesHelper.SharedPreferencesHelper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BirthActivity extends AppCompatActivity {

    EditText btn_regis;
    TextView txt_dob_err, txt_back;
    ImageView img_upload_avatar;
    Bitmap bitmap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth);

        btn_regis = findViewById(R.id.btn_regis);
        txt_dob_err = findViewById(R.id.txt_dob_err);
        txt_back = findViewById(R.id.txt_back);
        img_upload_avatar = findViewById(R.id.img_upload_avatar);

        Gson gson = new Gson();
        userModel user = gson.fromJson(getIntent().getStringExtra("data"), userModel.class);

//        Get URI Image and show in ImageView

        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                img_upload_avatar.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        img_upload_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });

//        Post data to server
        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                if (bitmap != null){
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                    byte[] bytes = byteArrayOutputStream.toByteArray();
//                    final String base64Img = android.util.Base64.encodeToString(bytes, Base64.DEFAULT);

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    String url = "http://10.0.2.2:3000/api/users/regis";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("TAG", "onResponse: " + response);
                                    JSONObject jsonObject;
                                    userModel newUser;
                                    if (response != null){
                                        try {
                                            jsonObject = new JSONObject(response);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                        try {
                                            newUser = new userModel(jsonObject.getString("_id"),
                                                    jsonObject.getString("fullname"),
                                                    jsonObject.getString("username"),
                                                    jsonObject.getString("password"),
                                                    jsonObject.getString("email"),
                                                    jsonObject.getString("id_role")
                                                    );
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext());
                                        sharedPreferencesHelper.saveObject("userInfo", newUser);
                                        Toast.makeText(BirthActivity.this, "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(BirthActivity.this, MainActivity.class));
                                    }
                                    else{
                                        Toast.makeText(BirthActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(BirthActivity.this, "API Connection Error!", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
//                            paramV.put("image", base64Img);
                            paramV.put("fullname", user.getFullname());
                            paramV.put("username", user.getUsername());
                            paramV.put("password", user.getPassword());
                            paramV.put("email", user.getEmail());
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
//            }
        });

        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
}