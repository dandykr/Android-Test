package com.bri.ojt.Model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("status")
    private ErrorStatus errorStatus;

    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }
}
