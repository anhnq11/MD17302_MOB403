package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface selectInterface {
    @GET("get_all_product.php")
    Call<resSelect> getPrduct();
}
