package com.unikrew.faceoff.model;

import java.io.Serializable;

public class VerifyOtpBioMetricVerificationResponse implements Serializable {

    private VerifyOtpBioMetricVerificationResponseData data = new VerifyOtpBioMetricVerificationResponseData();
    private VerifyOtpBioMetricVerificationResponseMessage message = new VerifyOtpBioMetricVerificationResponseMessage();

    public VerifyOtpBioMetricVerificationResponseData getData() {
        return data;
    }

    public void setData(VerifyOtpBioMetricVerificationResponseData data) {
        this.data = data;
    }

    public VerifyOtpBioMetricVerificationResponseMessage getMessage() {
        return message;
    }

    public void setMessage(VerifyOtpBioMetricVerificationResponseMessage message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return
        "VerifyOtpBioMetricVerificationResponse{" +
            "data = '" + data + '\'' +
            ",message = '" + message + '\'' +
        "}";
    }
}
