package com.bri.ojt.Model;

import com.google.gson.annotations.SerializedName;

public class GroupData {
    @SerializedName("listGroup")
    private String listGroup;

    public GroupData(String listGroup) {
        this.listGroup = listGroup;
    }

    public String getListGroup() {
        return listGroup;
    }
}
