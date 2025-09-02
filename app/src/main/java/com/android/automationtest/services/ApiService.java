package com.android.automationtest.services;

import com.android.automationtest.response.CheckVersionResponse;
import com.android.automationtest.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("check-version")
    Call<CheckVersionResponse> checkVersion();

    @FormUrlEncoded
    @POST("/login")
    Call<LoginResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );
}
