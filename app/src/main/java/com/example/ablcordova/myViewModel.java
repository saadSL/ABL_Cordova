package com.example.ablcordova;

import androidx.lifecycle.MutableLiveData;

import com.example.ablcordova.model.CnicPostParams;
import com.example.ablcordova.model.OtpPostParams;
import com.example.ablcordova.model.OtpResponse;
import com.example.ablcordova.model.ResponseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class myViewModel {


    private RetrofitApi service;
    public MutableLiveData<ResponseDTO> CnicSuccessLiveData = new MutableLiveData<ResponseDTO>();
    public MutableLiveData<String> CnicErrorLiveData = new MutableLiveData<String>();

    public MutableLiveData<OtpResponse> OtpSuccessLiveData = new MutableLiveData<OtpResponse>();
    public MutableLiveData<String> OtpErrorLiveData = new MutableLiveData<String>();

    myViewModel(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(RetrofitApi.class);
    }

    public void postCNIC(CnicPostParams cd) throws InterruptedException {

        System.out.println(cd);
        Call<ResponseDTO> callableRes = service.CNICpost(cd);
        callableRes.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                if (response.code()==200){
                    CnicSuccessLiveData.postValue(response.body());
                }else if (response.code()==403){
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String msg = jObjError.getJSONObject("message").getString("description");
                        CnicErrorLiveData.postValue(msg);
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
    }
}
