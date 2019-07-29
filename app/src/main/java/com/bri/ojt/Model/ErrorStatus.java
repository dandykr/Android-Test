package com.bri.ojt.Model;

import com.google.gson.annotations.SerializedName;

public class ErrorStatus {
    @SerializedName("code")
    private String code;
    @SerializedName("desc")
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
