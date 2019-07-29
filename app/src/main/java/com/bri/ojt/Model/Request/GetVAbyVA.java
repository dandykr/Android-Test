package com.bri.ojt.Model.Request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetVAbyVA {

    @SerializedName("data")
    private List<DataVA> dataVA;

    public GetVAbyVA(String noVa) {
        this.dataVA = new ArrayList<>();
        DataVA data = new DataVA(noVa);
        this.dataVA.add(data);
    }

    public List<DataVA> getDataVA() {
        return dataVA;
    }

    public void setDataVA(List<DataVA> dataVA) {
        this.dataVA = dataVA;
    }

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
}
