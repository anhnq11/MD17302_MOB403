package com.example.myapplication.API;

import com.example.myapplication.Model.productModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiServices {

    ApiServices apiServices = new Retrofit.Builder()
//            .baseUrl("http://192.168.106.101:3000/api/")
            .baseUrl("http://10.0.2.2:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServices.class);

    @GET("products/products")
    Call<List<productModel>> getProducts();
}
