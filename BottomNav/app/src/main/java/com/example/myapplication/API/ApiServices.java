package com.example.myapplication.API;

import com.example.myapplication.Model.cartModel;
import com.example.myapplication.Model.favoursModel;
import com.example.myapplication.Model.invoicesModel;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.Model.userModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiServices {

    ApiServices apiServices = new Retrofit.Builder()
//            .baseUrl("http://192.168.106.102:3000/api/")
            .baseUrl("http://10.0.2.2:3000/api/").addConverterFactory(GsonConverterFactory.create()).build().create(ApiServices.class);

    //    Login
    @GET("users/login")
    Call<userModel> login(@Query("username") String username, @Query("password") String password);

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

    //    Delete cart
    @DELETE("products/carts/")
    Call<cartModel> deleteCarts(@Query("user_id") String user_id);

    //    Add cart to invoice
    @POST("products/invoices")
    Call<invoicesModel> addToInvoices(@Body invoicesModel myInvoice);

    //    Get list invoices
    @GET("products/invoices")
    Call<List<invoicesModel>> getInvoices(@Query("user_id") String user_id);

    //    Get favours products
    @GET("products/favours/details")
    Call<favoursModel> getFavours(@Query("user_id") String user_id, @Query("product_id") String product_id);

    //    Get list favours
    @GET("products/favours")
    Call<ArrayList<productModel>> getListFavours(@Query("user_id") String user_id);

    //    Add to favours
    @POST("products/favours")
    Call<favoursModel> addToFavours(@Body favoursModel myFavours);

    //    Delete
    @DELETE("products/favours")
    Call<favoursModel> deleteFavours(@Query("_id") String _id);
}
