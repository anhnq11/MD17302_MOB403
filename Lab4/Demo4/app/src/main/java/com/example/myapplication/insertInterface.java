package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface insertInterface {
    @FormUrlEncoded
    @POST("create_product.php")
    Call<resInsert> insertProduct(
            @Field("name") String name,
            @Field("price") Double price,
            @Field("description") String desc
    );
}
