package com.unikrew.faceoff.interfaces;

import com.unikrew.faceoff.model.CnicPostParams;
import com.unikrew.faceoff.model.OtpPostParams;
import com.unikrew.faceoff.model.OtpResponse;
import com.unikrew.faceoff.model.ResponseDTO;
import com.unikrew.faceoff.model.UpdateBioMetricStatusPostParams;
import com.unikrew.faceoff.model.UpdateBioMetricStatusResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitApi {
    @POST("/RdaConsumer/api/consumer/public/bio-metric-verification")
    Call<ResponseDTO> CNICpost(@Body CnicPostParams cd);

    @POST("/RdaConsumer/api/consumer/verify-otp-bio-metric-verification")
    Call<OtpResponse> OtpPost(@Body OtpPostParams pp, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/update-bio-metric-verification-status")
    Call<UpdateBioMetricStatusResponse> UpdateBioMetricStatus(@Body UpdateBioMetricStatusPostParams pp, @Header("Authorization") String accessToken);

}
