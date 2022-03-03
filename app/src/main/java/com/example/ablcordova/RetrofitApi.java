package com.example.ablcordova;

import com.example.ablcordova.model.CnicPostParams;
import com.example.ablcordova.model.OtpPostParams;
import com.example.ablcordova.model.OtpResponse;
import com.example.ablcordova.model.ResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitApi {
    @POST("/RdaConsumer/api/consumer/public/bio-metric-verification")
    Call<ResponseDTO> CNICpost(@Body CnicPostParams cd);

    @POST("/RdaConsumer/api/consumer/verify-otp-bio-metric-verification")
    Call<OtpResponse> OtpPost(@Body OtpPostParams pp, @Header("Authorization") String accessToken);
}
