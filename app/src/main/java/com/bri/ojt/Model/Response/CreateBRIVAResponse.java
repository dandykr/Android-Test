package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

public class CreateBRIVAResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("responseDescription")
    private String responseDescription;
    @SerializedName("data")
    private Data data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("institutionCode")
        private String institutionCode;
        @SerializedName("brivaNo")
        private String brivaNo;
        @SerializedName("custCode")
        private String custCode;
        @SerializedName("nama")
        private String nama;
        @SerializedName("amount")
        private String amount;
        @SerializedName("keterangan")
        private String keterangan;
        @SerializedName("expiredDate")
        private String expiredDate;

        public String getInstitutionCode() {
            return institutionCode;
        }

        public void setInstitutionCode(String institutionCode) {
            this.institutionCode = institutionCode;
        }

        public String getBrivaNo() {
            return brivaNo;
        }

        public void setBrivaNo(String brivaNo) {
            this.brivaNo = brivaNo;
        }

        public String getCustCode() {
            return custCode;
        }

        public void setCustCode(String custCode) {
            this.custCode = custCode;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        public String getExpiredDate() {
            return expiredDate;
        }

        public void setExpiredDate(String expiredDate) {
            this.expiredDate = expiredDate;
        }
    }
}
