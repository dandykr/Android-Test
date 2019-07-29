package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

public class TransferInternalResponse extends RestResponse {
    @SerializedName("JournalSeq")
    private String journalSeq;

    public TransferInternalResponse() {
    }


    public String getJournalSeq() {
        return journalSeq;
    }
}
