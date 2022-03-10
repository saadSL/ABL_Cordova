package com.unikrew.faceoff.model;

import java.io.Serializable;

public class UpdateBioMetricStatusResponseData implements Serializable {
    private int rdaCustomerProfileId=0;
    private String message="";
    private Object bioMetricVerificationNadra=null;

    public int getRdaCustomerProfileId() {
        return rdaCustomerProfileId;
    }

    public void setRdaCustomerProfileId(int rdaCustomerProfileId) {
        this.rdaCustomerProfileId = rdaCustomerProfileId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBioMetricVerificationNadra() {
        return bioMetricVerificationNadra;
    }

    public void setBioMetricVerificationNadra(Object bioMetricVerificationNadra) {
        this.bioMetricVerificationNadra = bioMetricVerificationNadra;
    }

    @Override
    public String toString(){
        return
            "UpdateBioMetricStatusResponseData{" +
                "rdaCustomerProfileId = '" + rdaCustomerProfileId + '\'' +
                ",message = '" + message + '\'' +
                ",bioMetricVerificationNadra = '" + bioMetricVerificationNadra + '\'' +
            "}";
    }
}
