package com.bri.ojt.Model.Response;

import com.bri.ojt.Util.Consts;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class APIResponse {
    @SerializedName("Response_code")
    private String responseCode;
    @SerializedName("Response_message")
    private String responseMessage;
    @SerializedName("Response_exception")
    private String responseException;
    @SerializedName("DT")
    private List<Object> data;


    public APIResponse() {
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

    public List<Object> getData() {
        return data;
    }

    public <T> List<T> getDataCC(Class<T> tClass) {
        Gson gson = Consts.getInstance().getGson();
        return gson.fromJson(gson.toJson(data), new TypeToken<List<T>>(){}.getType());
    }
}
