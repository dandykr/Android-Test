package com.bri.ojt.Model.Request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReqTransferExternal implements Serializable {
    @SerializedName("Amount")
    private String amount;
    @SerializedName("beneficiaryAccount")
    private String beneficiaryAcc;
    @SerializedName("bankCode")
    private String bankCode;
    @SerializedName("beneficiaryAccountName")
    private String beneficiaryAccName;
    @SerializedName("sourceAccount")
    private String sourceAcc;
    @SerializedName("noReferral")
    private String noReferral;

    public ReqTransferExternal() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBeneficiaryAcc() {
        return beneficiaryAcc;
    }

    public void setBeneficiaryAcc(String beneficiaryAcc) {
        this.beneficiaryAcc = beneficiaryAcc;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBeneficiaryAccName() {
        return beneficiaryAccName;
    }

    public void setBeneficiaryAccName(String beneficiaryAccName) {
        this.beneficiaryAccName = beneficiaryAccName;
    }

    public String getSourceAcc() {
        return sourceAcc;
    }

    public void setSourceAcc(String sourceAcc) {
        this.sourceAcc = sourceAcc;
    }

    public String getNoReferral() {
        return noReferral;
    }

    public void setNoReferral(String noReferral) {
        this.noReferral = noReferral;
    }
}
