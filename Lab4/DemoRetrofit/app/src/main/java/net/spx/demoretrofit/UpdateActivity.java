package net.spx.demoretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    EditText ed_username2, ed_passwd2, ed_email2;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ed_username2 = findViewById(R.id.ed_username2);
        ed_passwd2 = findViewById(R.id.ed_passwd2);
        ed_email2 = findViewById(R.id.ed_email2);
        btn_update = findViewById(R.id.btn_update);

        Gson gson = new Gson();
        UserDTO user = gson.fromJson(getIntent().getStringExtra("data"), UserDTO.class);
        Log.d("TAG", "onCreate: " + user.getUsername());

        ed_username2.setText(user.getUsername());
        ed_email2.setText(user.getEmail());
        ed_passwd2.setText(user.getPasswd());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDTO userDTO = new UserDTO(
                        ed_username2.getText().toString().trim(),
                        ed_passwd2.getText().toString().trim(),
                        ed_email2.getText().toString().trim()
                );

                UserInterface.userInterface.update(user.getId(), userDTO).enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        Toast.makeText(UpdateActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {

                    }
                });
            }
        });


    }
}