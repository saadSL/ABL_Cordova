package com.unikrew.faceoff.model;

import java.io.Serializable;
import java.lang.reflect.Array;

public class BioMetricVerificationNadraPostData implements Serializable {
    private String rdaCustomerProfileId="";
    private String rdaCustomerAccountInfoId="";
    private String cnic="";

    /* saad's work for finger prints on line 11 and line 81 and line 18 - 24*/
    private Array fingerprints = null;
    private String templateType="";
    private String contactNumber="";
    private String area="";
    private String accountType="";

    public Array getFingerprints() {
        return fingerprints;
    }

    public void setFingerprints(Array fingerprints) {
        this.fingerprints = fingerprints;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getRdaCustomerProfileId() {
        return rdaCustomerProfileId;
    }

    public void setRdaCustomerProfileId(String rdaCustomerProfileId) {
        this.rdaCustomerProfileId = rdaCustomerProfileId;
    }

    public String getRdaCustomerAccountInfoId() {
        return rdaCustomerAccountInfoId;
    }

    public void setRdaCustomerAccountInfoId(String rdaCustomerAccountInfoId) {
        this.rdaCustomerAccountInfoId = rdaCustomerAccountInfoId;
    }

    @Override
    public String toString(){
        return
                "BioMetricVerificationNadraPostData{" +
                    "rdaCustomerProfileId = '" + rdaCustomerProfileId + '\'' +
                    ",rdaCustomerAccountInfoId = '" + rdaCustomerAccountInfoId + '\'' +
                    ",cnic = '" + cnic + '\'' +
                    ",fingerprints = '" + fingerprints + '\'' +
                    ",templateType = '" + templateType + '\'' +
                    ",contactNumber = '" + contactNumber + '\'' +
                    ",area = '" + area + '\'' +
                    ",accountType = '" + accountType + '\'' +
                "}";
    }
}
