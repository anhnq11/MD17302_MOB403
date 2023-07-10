package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;

public class AsyncTask extends android.os.AsyncTask<String, Void, Bitmap> {

    private LoadImageAsyncTask loadImageAsyncTask;

    public AsyncTask(LoadImageAsyncTask loadImageAsyncTask, Context context) {

        this.loadImageAsyncTask = loadImageAsyncTask;
    }

    //    Input data
    @Override
    protected Bitmap doInBackground(String... strings) {
//        Lấy hình ảnh từ URL
        try{
            return BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    Output image
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null){
//            Trả kết quả cho interface
            loadImageAsyncTask.onLoadBitmap(bitmap);
        }
        else {
//            Trả về lỗi cho interface
            loadImageAsyncTask.onError();
        }
    }

//    On Progress

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
