package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

public class RestResponse {

    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("responseDescription")
    private String responseMessage;
    @SerializedName("errorDescription")
    private String responseException;

    public RestResponse() {
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseException() {
        return responseException;
    }

    public void setResponseException(String responseException) {
        this.responseException = responseException;
    }
}
