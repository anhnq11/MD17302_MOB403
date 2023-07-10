package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoadImageAsyncTask, View.OnClickListener{

    Button button;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        new AsyncTask(this, this).execute("https://image.pngaaa.com/23/5937023-middle.png");
    }

    @Override
    public void onLoadBitmap(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        textView.setText("Đọc ảnh thành công!");
    }

    @Override
    public void onError() {
        textView.setText("Đọc ảnh thất bại!");
    }


}