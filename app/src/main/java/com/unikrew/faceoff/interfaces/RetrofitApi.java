package com.unikrew.faceoff.interfaces;

import com.unikrew.faceoff.model.BioMetricVerificationPostParams;
import com.unikrew.faceoff.model.VerifyOtpBioMetricVerificationPostParams;
import com.unikrew.faceoff.model.VerifyOtpBioMetricVerificationResponse;
import com.unikrew.faceoff.model.BioMetricVerificationResponse;
import com.unikrew.faceoff.model.BioMetricVerificationNadraPostParams;
import com.unikrew.faceoff.model.BioMetricVerificationNadraResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitApi {
    @POST("/RdaConsumer/api/consumer/public/bio-metric-verification")
    Call<BioMetricVerificationResponse> CNICpost(@Body BioMetricVerificationPostParams cd);

    @POST("/RdaConsumer/api/consumer/verify-otp-bio-metric-verification")
    Call<VerifyOtpBioMetricVerificationResponse> OtpPost(@Body VerifyOtpBioMetricVerificationPostParams pp, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/bio-metric-verification-nadra")
    Call<BioMetricVerificationNadraResponse> UpdateBioMetricStatus(@Body BioMetricVerificationNadraPostParams pp, @Header("Authorization") String accessToken);

}
