package com.unikrew.faceoff.model;

import java.io.Serializable;

public class BioMetricVerificationPostData implements Serializable {
	private String cnic;
	private String accountNo;

	public void setCnic(String cnic){
		this.cnic = cnic;
	}

	public String getCnic(){
		return cnic;
	}

	public void setAccountNo(String accountNo){
		this.accountNo = accountNo;
	}

	public String getAccountNo(){
		return accountNo;
	}

	@Override
 	public String toString(){
		return 
			"BioMetricVerificationPostData{" +
			"cnic = '" + cnic + '\'' + 
			",accountNo = '" + accountNo + '\'' + 
			"}";
	}
}