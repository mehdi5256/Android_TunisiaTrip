package android.mehdi.soatunisitatrip.Remote;

import android.mehdi.soatunisitatrip.Model.APIResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IMyAPI {
    @FormUrlEncoded
    @POST("login.php")
    Call<APIResponse> loginUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<APIResponse> RegisterUser(@Field("nom") String nom, @Field("email") String email, @Field("password") String password);
 }
