package com.unikrew.faceoff.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

import com.unikrew.faceoff.Config;
import com.unikrew.faceoff.interfaces.RetrofitApi;
import com.unikrew.faceoff.model.CnicPostParams;
import com.unikrew.faceoff.model.OtpPostParams;
import com.unikrew.faceoff.model.OtpResponse;
import com.unikrew.faceoff.model.ResponseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CnicAvailabilityViewModel {


    private RetrofitApi service;
    public MutableLiveData<ResponseDTO> CnicSuccessLiveData = new MutableLiveData<ResponseDTO>();
    public MutableLiveData<String> CnicErrorLiveData = new MutableLiveData<String>();

    public MutableLiveData<OtpResponse> OtpSuccessLiveData = new MutableLiveData<OtpResponse>();
    public MutableLiveData<String> OtpErrorLiveData = new MutableLiveData<String>();

    Activity activity;
    AlertDialog alertDialog;

    CircularProgressButton cpb;

    myViewModel(){
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

    public void postCNIC(CnicPostParams cd,Activity myActivity) throws InterruptedException {
        this.activity = myActivity;
        cpb = this.activity.findViewById(R.id.btn_Next);
        System.out.println(cd);
        Call<ResponseDTO> callableRes = service.CNICpost(cd);
        callableRes.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                if (response.code()==200){
                    CnicSuccessLiveData.postValue(response.body());
                    cpb.stopAnimation();
                }else if (response.code()==403){
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject message = jObjError.getJSONObject("message");
                        if (message.getString("status").equals("api_error")){
                            CnicErrorLiveData.postValue(message.getString("errorDetail"));
                            cpb.stopAnimation();
                        }else{
                            CnicErrorLiveData.postValue(message.getString("description"));
                            cpb.stopAnimation();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                CnicErrorLiveData.postValue(t.getMessage().toString());
            }
        });
        cpb.startAnimation();
//        showLoading();
//        alertDialog.show();
    }

    public void postOtp(OtpPostParams postParams,String accessToken) throws InterruptedException{
        System.out.println("Access Token : "+accessToken);
        System.out.println("Post Params : "+postParams);
        Call<OtpResponse> callableRes = service.OtpPost(postParams,"Bearer "+accessToken);
//        Response<OtpResponse> res = null;
        callableRes.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.code()==200){
                    System.out.println(response.body());
                    OtpSuccessLiveData.postValue(response.body());
                }else if (response.code()==403){
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String msg = jObjError.getJSONObject("message").getString("description");
                        OtpErrorLiveData.postValue(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                OtpErrorLiveData.postValue(t.getMessage());
            }
        });
        showLoading();
        alertDialog.show();
    }

    private void showLoading(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setView(View.inflate(activity,R.layout.loader,null));
        builder1.setCancelable(false);
        alertDialog = builder1.create();
    }
}
