package com.example.myapplication;

import android.graphics.Bitmap;

public interface LoadImageAsyncTask {
    void onLoadBitmap(Bitmap bitmap);
    void onError();
}
