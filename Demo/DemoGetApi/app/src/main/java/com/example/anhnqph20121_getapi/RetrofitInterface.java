package com.example.anhnqph20121_getapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitInterface {

    static final String BASE_URL = "https://64b93df279b7c9def6c0cca0.mockapi.io/users/";

    RetrofitInterface retrofitInterface = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitInterface.class);

    @GET("users")
    Call<List<UserModel>> getListUser ();
}
