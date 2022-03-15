package com.unikrew.faceoff.model;

import java.io.Serializable;

public class BioMetricVerificationNadraResponseData implements Serializable {
    private String responseCode = "";
    private String responseDescription = "";

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    @Override
    public String toString(){
        return
            "BioMetricVerificationNadraResponseData{" +
                "responseCode = '" + responseCode + '\'' +
                ",responseDescription = '" + responseDescription + '\'' +
            "}";
    }
}
