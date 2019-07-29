package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccInfoInternalResponseCashcard extends RestResponse implements Serializable {
    @SerializedName("Data")
    private AccountInfo data;

    public AccInfoInternalResponseCashcard() {
    }

    public AccountInfo getData() {
        return data;
    }

    public class AccountInfo {
        @SerializedName("sourceAccount")
        private String sourceAccount;
        @SerializedName("sourceAccountName")
        private String sourceAccountName;
        @SerializedName("sourceAccountStatus")
        private String sourceAccountStatus;
        @SerializedName("sourceAccountBalace")
        private String sourceAccountBalace;
        @SerializedName("registrationStatus")
        private String registrationStatus;
        @SerializedName("beneficiaryAccount")
        private String beneficiaryAccount;
        @SerializedName("beneficiaryAccountName")
        private String beneficiaryAccountName;
        @SerializedName("beneficiaryAccountStatus")
        private String beneficiaryAccountStatus;

        public AccountInfo() {
        }

        public String getSourceAccount() {
            return sourceAccount;
        }

        public String getSourceAccountName() {
            return sourceAccountName;
        }

        public String getSourceAccountStatus() {
            return sourceAccountStatus;
        }

        public String getSourceAccountBalace() {
            return sourceAccountBalace;
        }

        public String getRegistrationStatus() {
            return registrationStatus;
        }

        public String getBeneficiaryAccount() {
            return beneficiaryAccount;
        }

        public String getBeneficiaryAccountName() {
            return beneficiaryAccountName;
        }

        public String getBeneficiaryAccountStatus() {
            return beneficiaryAccountStatus;
        }
    }
}
