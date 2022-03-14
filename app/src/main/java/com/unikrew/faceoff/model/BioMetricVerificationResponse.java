package com.unikrew.faceoff.model;

import java.io.Serializable;

public class BioMetricVerificationResponse implements Serializable {
	private BioMetricVerificationResponseData data;
	private BioMetricVerificationResponseMessage message;

	public void setData(BioMetricVerificationResponseData data){
		this.data = data;
	}

	public BioMetricVerificationResponseData getData(){
		return data;
	}

	public void setMessage(BioMetricVerificationResponseMessage message){
		this.message = message;
	}

	public BioMetricVerificationResponseMessage getMessage(){
		return this.message;
	}

	@Override
 	public String toString(){
		return 
			"BioMetricVerificationResponse{" +
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}