package com.unikrew.faceoff.model;

import java.io.Serializable;

public class BioMetricVerificationNadraResponse implements Serializable {

    private BioMetricVerificationNadraResponseData data;
    private BioMetricVerificationNadraResponseMessage message;

    public BioMetricVerificationNadraResponseData getData() {
        return data;
    }

    public void setData(BioMetricVerificationNadraResponseData data) {
        this.data = data;
    }

    public BioMetricVerificationNadraResponseMessage getMessage() {
        return message;
    }

    public void setMessage(BioMetricVerificationNadraResponseMessage message) {
        this.message = message;
    }


    @Override
    public String toString(){
        return
            "BioMetricVerificationNadraResponse{" +
                "data = '" + data + '\'' +
                ",message = '" + message + '\'' +
            "}";
    }
}
