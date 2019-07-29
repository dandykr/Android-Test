package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TarikTunaiResponse extends RestResponseCashcard implements Serializable {
    @SerializedName("One_time_password")
    private String oneTimePass;

    @SerializedName("Expired")
    private String expired;

    @SerializedName("CreateDateTime")
    private String createDateTime;

    @SerializedName("ExpiredDateTime")
    private String expiredDateTime;

    @SerializedName("Amount")
    private int amount;

    public TarikTunaiResponse() {
    }

    public String getOneTimePass() {
        return oneTimePass;
    }

    public void setOneTimePass(String oneTimePass) {
        this.oneTimePass = oneTimePass;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getExpiredDateTime() {
        return expiredDateTime;
    }

    public void setExpiredDateTime(String expiredDateTime) {
        this.expiredDateTime = expiredDateTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getExpiredMillisTeller() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = null;
        try {
            date = format.parse(getExpiredDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public boolean reqValid() {
        boolean success;
        boolean firstReq = getResponseCode().equalsIgnoreCase("00");
        boolean secondReq = getResponseCode().equalsIgnoreCase("02");
        boolean stillValid = getResponseCode().equals("MB");

        success = firstReq || secondReq || stillValid;

        return success;
    }

    public String getErrorMessage() {
        if (getResponseException().isEmpty()) {
            return getResponseMessage();
        }
        return getResponseException();
    }
}
