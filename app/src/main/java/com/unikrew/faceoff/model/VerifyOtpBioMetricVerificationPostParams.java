package com.unikrew.faceoff.model;

import java.io.Serializable;

public class VerifyOtpBioMetricVerificationPostParams implements Serializable {
    private VerifyOtpBioMetricVerificationPostData data = new VerifyOtpBioMetricVerificationPostData();

    public VerifyOtpBioMetricVerificationPostData getData() {
        return data;
    }

    public void setData(VerifyOtpBioMetricVerificationPostData data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return
            "VerifyOtpBioMetricVerificationPostParams{" +
                    "data = '" + data + '\'' +
                    "}";
    }
}
