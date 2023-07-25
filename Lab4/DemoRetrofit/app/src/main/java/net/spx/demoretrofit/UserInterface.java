package net.spx.demoretrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public interface UserInterface {

    static final String BASE_URL = "https://64b93df279b7c9def6c0cca0.mockapi.io/users/";

    UserInterface userInterface = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(UserInterface.class);

    @GET("users")
    Call<List<UserDTO>>  lay_danh_sach ();

    // Thêm mới user:
    @POST("users")
    Call<UserDTO> them_user (@Body UserDTO objU);
    @DELETE("users/{id}")
    Call<UserDTO> delete(@Path("id") String id);

    @PUT("users/{id}")
    Call<UserDTO> update(@Path("id") String id, @Body UserDTO userDTO);

}
