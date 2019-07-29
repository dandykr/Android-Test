package com.bri.ojt.Model.Request;

import com.google.gson.annotations.SerializedName;

public class ReqTarikTunai {
    @SerializedName("data")
    private DataTarikTunai data;

    public ReqTarikTunai(DataTarikTunai data) {
        this.data = data;
    }

    public DataTarikTunai getData() {
        return data;
    }

    public void setData(DataTarikTunai data) {
        this.data = data;
    }

    public static class DataTarikTunai {
        @SerializedName("virtualaccount")
        private String vAccount;
        @SerializedName("amount")
        private String amount;

        public DataTarikTunai(String virtualAccount, String amount) {
            this.vAccount = virtualAccount;
            this.amount = amount;
        }

        public String getvAccount() {
            return vAccount;
        }

        public void setvAccount(String vAccount) {
            this.vAccount = vAccount;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
