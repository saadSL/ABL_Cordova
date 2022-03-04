package com.example.ablcordova;

import com.example.ablcordova.model.ResponseDTO;

public class Config {
    public static final String BASE_URL = "http://192.168.1.5:8080";

    public static String ACCOUNT_NUMBER = "account_number";
    public static String CNIC_NUMBER = "cnic_number";

    public static String RESPONSE = "response";
    public static String CNIC_ACC = "cnic_acc";

    public static int errorType = 0;
    public static int successType = 1;
    public static int ACCOUNT_LENGTH = 2;
    public static int CNIC_LENGTH = 2;

    public static int countDownTime = 5*60*1000;

}
