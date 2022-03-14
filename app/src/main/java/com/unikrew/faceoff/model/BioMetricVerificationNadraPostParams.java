package com.unikrew.faceoff.model;

import java.io.Serializable;

public class BioMetricVerificationNadraPostParams implements Serializable {

    private BioMetricVerificationNadraPostData data = new BioMetricVerificationNadraPostData();

    public BioMetricVerificationNadraPostData getData() {
        return data;
    }

    public void setData(BioMetricVerificationNadraPostData data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return
            "BioMetricVerificationNadraPostParams{" +
                "data = '" + data + '\'' +
            "}";
    }
}
