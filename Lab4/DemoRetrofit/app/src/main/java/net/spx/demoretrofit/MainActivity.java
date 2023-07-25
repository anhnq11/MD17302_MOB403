package net.spx.demoretrofit;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText ed_username, ed_passwd, ed_email;
    Button btn_add, btn_get;
    RecyclerView lv_user;
    static final String BASE_URL = "https://64b93df279b7c9def6c0cca0.mockapi.io/users/";
    ArrayList<UserDTO> ds_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_email = findViewById(R.id.ed_email);
        ed_passwd = findViewById(R.id.ed_passwd);
        ed_username = findViewById(R.id.ed_username);
        btn_add = findViewById(R.id.btn_add);
        btn_get = findViewById(R.id.btn_get);
        lv_user = findViewById(R.id.lv_user);

        // khai báo ds a
        ds_user = new ArrayList<UserDTO>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv_user.setLayoutManager(linearLayoutManager);
        Adapter adapter = new Adapter(getApplication(), (ArrayList<UserDTO>) ds_user);
        lv_user.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = ed_username.getText().toString();
                String p = ed_passwd.getText().toString();
                String m = ed_email.getText().toString();

                UserDTO u = new UserDTO();
                u.setUsername(uname);
                u.setEmail(m);
                u.setPasswd(p);

                AddUser(u);
            }
        });

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetListUser();
            }
        });

        // vào ứng dung load ds luôn
        GetListUser();

    }

    void AddUser(UserDTO objUser) {
        // Tạo đối tượng chuyển đổi kiểu dữ liệu
        Gson gson = new GsonBuilder().setLenient().create();
        // Tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Khơỉ tạo Interface
        UserInterface userInterface = retrofit.create(UserInterface.class);

        // Tạo call
        Call<UserDTO> objCall = userInterface.them_user(objUser);

        // thực hiện gửi dữ lệu lên sv
        objCall.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                // kết quả server trả về ở đây
                if (response.isSuccessful()) {
                    // lấy kết quả trả về
                    UserDTO userDTO = response.body();
                    Toast.makeText(MainActivity.this,
                            "Kết quả: " + userDTO.getUsername() + ", id = " + userDTO.getId()
                            , Toast.LENGTH_SHORT).show();
                    GetListUser();

                } else {
                    Log.e("zzzzzzz", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                // nếu xảy ra lôi sẽ thông báo ở đây
                Log.e("zzzzzzz", t.getLocalizedMessage());

            }
        });

    }

    void GetListUser() {
        // tạo gson
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // sử dụng interface
        UserInterface userInterface = retrofit.create(UserInterface.class);

        // tạo đối tượng
        Call<List<UserDTO>> objCall = userInterface.lay_danh_sach();
        objCall.enqueue(new Callback<List<UserDTO>>() {
            @Override
            public void onResponse(Call<List<UserDTO>> call, Response<List<UserDTO>> response) {
                if (response.isSuccessful()) {

                    ds_user.clear();
                    ds_user.addAll(response.body());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    lv_user.setLayoutManager(linearLayoutManager);
                    Adapter adapter = new Adapter(getApplicationContext(), ds_user);
                    lv_user.setAdapter(adapter);

                } else {
                    Toast.makeText(MainActivity.this,
                            "Không lấy được dữ liệu" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<UserDTO>> call, Throwable t) {

            }
        });

    }

}