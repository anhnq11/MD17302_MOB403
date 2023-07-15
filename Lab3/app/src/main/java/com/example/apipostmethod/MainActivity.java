package com.example.apipostmethod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText edtUsername, edtPassword;
    TextView txtResult;
    String path = "https://batdongsanabc.000webhostapp.com/mob403/demo2_api_get.php";
    String reSult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        edtUsername = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        txtResult = findViewById(R.id.txtResult);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getData().execute();
            }
        });
    }

    class getData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            path += "?name" + edtUsername.getText().toString().trim() + "&mark" + edtPassword.getText().toString().trim();
            try {
                URL url = new URL(path);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                reSult = stringBuilder.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }
}