package com.bri.ojt.Model;

import com.google.gson.annotations.SerializedName;

public class DataVA {
    @SerializedName("listVA")
    private String listVA;

    public DataVA(String listVA) {
        this.listVA = listVA;
    }

    public String getListVA() {
        return listVA;
    }
}
