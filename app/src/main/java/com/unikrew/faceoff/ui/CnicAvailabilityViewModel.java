package com.unikrew.faceoff.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

import com.unikrew.faceoff.Config;
import com.unikrew.faceoff.R;
import com.unikrew.faceoff.interfaces.RetrofitApi;
import com.unikrew.faceoff.model.BioMetricVerificationPostParams;
import com.unikrew.faceoff.model.VerifyOtpBioMetricVerificationPostParams;
import com.unikrew.faceoff.model.VerifyOtpBioMetricVerificationResponse;
import com.unikrew.faceoff.model.BioMetricVerificationResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unikrew.faceoff.model.BioMetricVerificationNadraPostParams;
import com.unikrew.faceoff.model.BioMetricVerificationNadraResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CnicAvailabilityViewModel {


    private RetrofitApi service;

    public MutableLiveData<BioMetricVerificationResponse> CnicSuccessLiveData = new MutableLiveData<BioMetricVerificationResponse>();
    public MutableLiveData<String> CnicErrorLiveData = new MutableLiveData<String>();
    public MutableLiveData<String> CnicVerifiedLiveData = new MutableLiveData<String>();

    public MutableLiveData<VerifyOtpBioMetricVerificationResponse> OtpSuccessLiveData = new MutableLiveData<VerifyOtpBioMetricVerificationResponse>();
    public MutableLiveData<String> OtpErrorLiveData = new MutableLiveData<String>();

    public MutableLiveData<BioMetricVerificationNadraResponse> BioMetricStatusSuccessLiveData = new MutableLiveData<BioMetricVerificationNadraResponse>();
    public MutableLiveData<String> BioMetricStatusErrorLiveData = new MutableLiveData<String>();



    Activity activity;
    AlertDialog loader;


    CnicAvailabilityViewModel(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(RetrofitApi.class);
    }

    public void postCNIC(BioMetricVerificationPostParams cd, Activity myActivity) throws InterruptedException {
        this.activity = myActivity;

        Call<BioMetricVerificationResponse> callableRes = service.CNICpost(cd);
        callableRes.enqueue(new Callback<BioMetricVerificationResponse>() {
            @Override
            public void onResponse(Call<BioMetricVerificationResponse> call, Response<BioMetricVerificationResponse> response) {
                if (response.code()==200){
                    CnicSuccessLiveData.postValue(response.body());
                    loader.dismiss();
                }else if (response.code()==403){
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject message = jObjError.getJSONObject("message");
                        if (message.getString("status").equals("api_error")){
                            CnicErrorLiveData.postValue(message.getString("errorDetail"));
                            loader.dismiss();
                        }else{
                            if (message.getString("status").equals("409")){
                                CnicVerifiedLiveData.postValue(message.getString("description"));
                            }else if (message.getString("status").equals("401")){
                                CnicErrorLiveData.postValue(message.getString("description"));
                            }

                            loader.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<BioMetricVerificationResponse> call, Throwable t) {
                CnicErrorLiveData.postValue(t.getMessage());
                loader.dismiss();
            }
        });
        showLoading();
        loader.show();
    }

    public void postOtp(VerifyOtpBioMetricVerificationPostParams postParams, String accessToken, Activity myActivity) throws InterruptedException{
        this.activity = myActivity;
        Call<VerifyOtpBioMetricVerificationResponse> callableRes = service.OtpPost(postParams,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<VerifyOtpBioMetricVerificationResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpBioMetricVerificationResponse> call, Response<VerifyOtpBioMetricVerificationResponse> response) {
                if (response.code()==200){
                    OtpSuccessLiveData.postValue(response.body());
                    loader.dismiss();
                }else if (response.code()==403){
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String msg = jObjError.getJSONObject("message").getString("description");
                        OtpErrorLiveData.postValue(msg);
                        loader.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<VerifyOtpBioMetricVerificationResponse> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
                OtpErrorLiveData.postValue(t.getMessage());
                loader.dismiss();
            }
        });
        showLoading();
        loader.show();
    }

    public void updateBioMetricStatus(BioMetricVerificationNadraPostParams pp, String accessToken, Activity myActivity){
        this.activity = myActivity;
        Call<BioMetricVerificationNadraResponse> callableRes = service.UpdateBioMetricStatus(pp,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<BioMetricVerificationNadraResponse>() {
            @Override
            public void onResponse(Call<BioMetricVerificationNadraResponse> call, Response<BioMetricVerificationNadraResponse> response) {
                if (response.code()==200){
                    BioMetricStatusSuccessLiveData.postValue(response.body());
                    loader.dismiss();
                }else if (response.code()==403){
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String msg = jObjError.getJSONObject("message").getString("description");
                        BioMetricStatusErrorLiveData.postValue(msg);
                        loader.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<BioMetricVerificationNadraResponse> call, Throwable t) {
                BioMetricStatusErrorLiveData.postValue(t.getMessage());
                loader.dismiss();
            }
        });
        showLoading();
        loader.show();
    }

    private void showLoading(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setView(View.inflate(activity,R.layout.loader,null));
        builder1.setCancelable(false);
        loader = builder1.create();
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
