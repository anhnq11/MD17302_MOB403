package com.example.myapplication.API;

import com.example.myapplication.Model.cartModel;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.Model.userModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    ApiServices apiServices = new Retrofit.Builder()
//            .baseUrl("http://192.168.106.102:3000/api/")
            .baseUrl("http://10.0.2.2:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServices.class);

//    Login
    @GET("users/login")
    Call<userModel> login(@Query("username") String username,
                                     @Query("password") String password);

//    Check valid username
    @GET("users/checkRegis")
    Call<Object> checkRegis(@Query("username") String username);

//    Get list products
    @GET("products/products")
    Call<List<productModel>> getProducts();

    //    Add product to cart
    @POST("products/carts")
    Call<List<cartModel>> addToCart(@Body cartModel myCart);

    //    Get list cart
    @GET("products/carts")
    Call<List<cartModel>> getCart(@Query("user_id") String user_id);

    //    Remove cart
    @PUT("products/carts/")
    Call<List<cartModel>> removeFromCart(@Query("id") String id, @Body cartModel cart);
}
