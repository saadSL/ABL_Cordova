package com.unikrew.faceoff.model;

import java.io.Serializable;

public class BioMetricVerificationPostParams implements Serializable {

	private BioMetricVerificationPostData data = new BioMetricVerificationPostData();

	public void setData(BioMetricVerificationPostData data){
		this.data = data;
	}

	public BioMetricVerificationPostData getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"BioMetricVerificationPostParams{" +
			"data = '" + data + '\'' + 
			"}";
		}
}