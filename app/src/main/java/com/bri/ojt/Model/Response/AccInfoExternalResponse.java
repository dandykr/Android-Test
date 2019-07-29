package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

public class AccInfoExternalResponse extends RestResponse {
    @SerializedName("Name")
    private String name;

    public AccInfoExternalResponse() {
    }

    public String getName() {
        return name;
    }
}
