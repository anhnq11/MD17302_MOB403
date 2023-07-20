package net.spx.demoretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserInterface {
    @GET("users")
    Call<List<UserDTO>>  lay_danh_sach ();

    // Thêm mới user:
    @POST("users")
    Call<UserDTO> them_user (@Body UserDTO objU);

}
