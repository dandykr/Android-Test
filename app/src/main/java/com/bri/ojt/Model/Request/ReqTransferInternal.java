package com.bri.ojt.Model.Request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReqTransferInternal implements Serializable {
    @SerializedName("NoReferral")
    private String noReferral;
    @SerializedName("sourceAccount")
    private String sourceAccount;
    @SerializedName("beneficiaryAccount")
    private String beneficiaryAccount;
    private String beneficiaryAccountName;
    @SerializedName("Amount")
    private String amount;
    @SerializedName("FeeType")
    private String feeType;
    @SerializedName("transactionDateTime")
    private String transactionDateTime;
    @SerializedName("remark")
    private String remark;

    public ReqTransferInternal() {
    }

    public ReqTransferInternal(String noReferral, String sourceAccount, String beneficiaryAccount, String beneficiaryAccountName, String amount, String feeType, String transactionDateTime, String remark) {
        this.noReferral = noReferral;
        this.sourceAccount = sourceAccount;
        this.beneficiaryAccount = beneficiaryAccount;
        this.beneficiaryAccountName = beneficiaryAccountName;
        this.amount = amount;
        this.feeType = feeType;
        this.transactionDateTime = transactionDateTime;
        this.remark = remark;
    }

    public String getNoReferral() {
        return noReferral;
    }

    public void setNoReferral(String noReferral) {
        this.noReferral = noReferral;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getBeneficiaryAccountName() {
        return beneficiaryAccountName;
    }

    public void setBeneficiaryAccountName(String beneficiaryAccountName) {
        this.beneficiaryAccountName = beneficiaryAccountName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
