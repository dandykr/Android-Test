package com.bri.ojt.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetVAbyGroup {

    @SerializedName("data")
    private List<GroupData> dataGroups;

    public GetVAbyGroup() {
    }

    public List<GroupData> getDataGroups() {
        return dataGroups;
    }

    public void setDataGroups(List<GroupData> dataGroups) {
        this.dataGroups = dataGroups;
    }
}
