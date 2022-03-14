package com.unikrew.faceoff.model;

import java.io.Serializable;

public class BioMetricVerificationNadraResponseData implements Serializable {
    private int responseCode =0;
    private String responseMsg ="";

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    @Override
    public String toString(){
        return
            "BioMetricVerificationNadraResponseData{" +
                "responseCode = '" + responseCode + '\'' +
                ",responseMsg = '" + responseMsg + '\'' +
            "}";
    }
}
