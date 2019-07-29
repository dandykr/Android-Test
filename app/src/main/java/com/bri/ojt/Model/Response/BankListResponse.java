package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankListResponse extends RestResponse {
    @SerializedName("Data")
    private List<Data> data;

    public BankListResponse() {
    }

    public List<Data> getData() {
        return data;
    }

    public static class Data {
        @SerializedName("BankCode")
        private String bankCode;
        @SerializedName("Bankname")
        private String bankName;

        public Data() {
        }

        public String getBankCode() {
            return bankCode;
        }

        public String getBankName() {
            return bankName;
        }
    }
}
