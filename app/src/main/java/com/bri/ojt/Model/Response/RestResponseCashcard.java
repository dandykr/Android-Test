package com.bri.ojt.Model.Response;

import com.bri.ojt.Util.Consts;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestResponseCashcard implements Serializable {

    @SerializedName("Response_code")
    private String responseCode;
    @SerializedName("Response_message")
    private String responseMessage;
    @SerializedName("Response_exception")
    private String responseException;

    public RestResponseCashcard() {
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getResponseException() {
        return responseException;
    }
}
