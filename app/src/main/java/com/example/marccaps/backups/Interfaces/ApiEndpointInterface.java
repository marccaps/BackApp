package com.example.marccaps.backups.Interfaces;

import com.example.marccaps.backups.Models.BackUpItem;
import com.example.marccaps.backups.Models.Credentials;
import com.example.marccaps.backups.Models.FileObject;
import com.example.marccaps.backups.Models.ResponseLogin;
import com.example.marccaps.backups.Models.User;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by MarcCaps on 2/5/17.
 */

public interface ApiEndpointInterface {

    @GET("/{user}")
    Call<User> getUser(@Path("user") String user);

    @GET("api/{user}/files")
    Call<ResponseBody> getFileList(@Path("user") String user);

    @GET("/{user}/{file}")
    Call<BackUpItem> getFile(@Path("user") String user,@Path("file")String file);

    @GET("/{user}/{file}?t=info")
    Call<BackUpItem> getFileInfo(@Path("user") String user,@Path("file")String file);

    @POST("/api/signin")
    Call<ResponseLogin> getAccess(@Body Credentials credentials);

    @POST("/api/{user}/upload")
    Call<ResponseBody> uploadFile(@Path("user") String user, @Body FileObject fileObject);

}
