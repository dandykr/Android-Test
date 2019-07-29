package com.bri.ojt.Model.Request;

import com.google.gson.annotations.SerializedName;

public class ReqOTP {
    @SerializedName("vaNo")
    private String vaNo;
    @SerializedName("name")
    private String name;
    @SerializedName("noHp")
    private String noHp;
    @SerializedName("email")
    private String email;

    public ReqOTP(String vaNo, String name, String noHp, String email) {
        this.vaNo = vaNo;
        this.name = name;
        this.noHp = noHp;
        this.email = email;
    }

    public String getVaNo() {
        return vaNo;
    }

    public String getName() {
        return name;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getEmail() {
        return email;
    }

    public static class VerifyOTP {
        @SerializedName("vaNo")
        private String vaNo;
        @SerializedName("kodeOtp")
        private String kodeOtp;

        public VerifyOTP(String vaNo, String kodeOtp) {
            this.vaNo = vaNo;
            this.kodeOtp = kodeOtp;
        }

        public String getVaNo() {
            return vaNo;
        }

        public String getKodeOtp() {
            return kodeOtp;
        }
    }
}
