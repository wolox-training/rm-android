package ar.com.wolox.android.example.network;

import java.util.List;

import ar.com.wolox.android.example.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 */
public interface LoginService {

    @GET("/users")
    Call<List<User>> getUserByCredentials(@Query("email") String username, @Query("password") String password);

    @GET("/users")
    Call<List<User>> getUserByMail(@Query("email") String username);

}