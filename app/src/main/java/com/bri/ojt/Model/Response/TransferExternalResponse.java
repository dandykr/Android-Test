package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

public class TransferExternalResponse extends RestResponse {
    @SerializedName("JurnalSeq")
    private String jurnalSeq;

    public TransferExternalResponse() {
    }

    public String getJurnalSeq() {
        return jurnalSeq;
    }

    public void setJurnalSeq(String jurnalSeq) {
        this.jurnalSeq = jurnalSeq;
    }
}
