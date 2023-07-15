package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    Button addBtn, loadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView1);
        addBtn = findViewById(R.id.button1);
        loadBtn = findViewById(R.id.button2);
        getWebPage();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageInfo();
            }
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebPage();
            }
        });
    }


    void getWebPage(){
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        service.execute(new Runnable() {
            @Override
            public void run() {
                String myUrl = "https://vnexpress.net/rss/kinh-doanh.rss";
                String noiDung = "";
//                1. Tạo đối tượng URL
                try {
                    URL URL = new URL(myUrl);
//                    2. Mở kết nối
                    HttpURLConnection connection = (HttpURLConnection) URL.openConnection();
                    Log.d("TAG", "Đang mở kết nối");
//                    3. Tạo đối tượng đọc luồng dữ liệu
                    InputStream inputStream = connection.getInputStream();
                    Log.d("TAG", "Đang tạo đối tượng");
//                    4. Tạo biến đọc dữ liệu
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    Log.d("TAG", "Đang tạo biến đọc dữ liệu");
//                    5. Tạo biến để ghép dữ liệu
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";
//                    6. Tiến hành đọc và ghi dữ liệu
                    Log.d("TAG", "Đang tiến hành đọc ghi dữ liệu");
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    Log.d("TAG", "Đọc dữ liệu thành công");
//                    7. Kết thúc quá trình, ngắt kết nối
                    bufferedReader.close();
                    inputStream.close();
                    connection.disconnect();
                    noiDung = stringBuilder.toString();
                    Log.d("TAG", "Noi dung: " + noiDung);

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

//                Hiển thị dữ liệu lên giao diện
                String finalNoiDung = noiDung;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadData(finalNoiDung, "text/html", "utf8");
                    }
                });
            }
        });
    }

    void pageInfo() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

//        Lấy dữ liệu từ form
        final EditText edtName = findViewById(R.id.txtName);
        final EditText edtNote = findViewById(R.id.txtNote);
        final String myUrl = "https://63dcb865df83d549ce926243.mockapi.io/baiViet/test";


        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL URL = new URL(myUrl);
//                    Mở kết nối
                    HttpURLConnection connection = (HttpURLConnection) URL.openConnection();
//                    Thiết lập phương thức
                    connection.setRequestMethod("POST");
//                    Tạo đối tượng dữ liệu để gửi lên server
                    JSONObject postData = new JSONObject();
                    postData.put("name", edtName.getText().toString().trim());
                    postData.put("note", edtNote.getText().toString().trim());
//                    Thiết lập dữ liệu gửi lên server
                    connection.setRequestProperty("Content-Type", "application/json");
//                    Tạo đối tượng out dữ liệu ra khỏi ứng dụng
                    OutputStream outputStream = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
//                    Ghi dữ liệu
                    writer.append(postData.toString());
//                    Xóa bộ nhớ đệm
                    writer.flush();
                    writer.close();
                    outputStream.close();
//                    Chưa cần đóng connection -> Nhận phản hồi từ server

//                    Nhận phản hồi từ server
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    reader.close();
                    inputStream.close();
//                    Ngắt kết nối connection
                    connection.disconnect();
                    String res = stringBuilder.toString();
//                    Gửi dữ liệu phản hồi lên webview
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            WebView webView1 = findViewById(R.id.webView1);
                            webView1.loadData(res, "text/plain", "utf8");
                        }
                    });

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}