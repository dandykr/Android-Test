package com.bri.ojt.Model.Request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReqMutasi {

    @SerializedName("data")
    private List<DataMutasi> dataMutasi;

    public ReqMutasi(String virtualAccount, String newStartDate, String newFinishDate) {
        this.dataMutasi = new ArrayList<>();
        DataMutasi data = new DataMutasi(virtualAccount, newStartDate, newFinishDate);
        this.dataMutasi.add(data);
    }

    public List<DataMutasi> getDataMutasi() {
        return dataMutasi;
    }

    public void setDataMutasi(List<DataMutasi> dataMutasi) {
        this.dataMutasi = dataMutasi;
    }

    public class DataMutasi {
        @SerializedName("virtualaccount")
        private String virtualAccount;
        @SerializedName("newstartdate")
        private  String newStartDate;
        @SerializedName("newfinishdate")
        private String newFinishDate;

        public DataMutasi(String virtualAccount, String newStartDate, String newFinishDate) {
            this.virtualAccount = virtualAccount;
            this.newStartDate = newStartDate;
            this.newFinishDate = newFinishDate;
        }

        public String getVirtualAccount() {
            return virtualAccount;
        }

        public void setVirtualAccount(String virtualAccount) {
            this.virtualAccount = virtualAccount;
        }

        public String getNewStartDate() {
            return newStartDate;
        }

        public void setNewStartDate(String newStartDate) {
            this.newStartDate = newStartDate;
        }

        public String getNewFinishDate() {
            return newFinishDate;
        }

        public void setNewFinishDate(String newFinishDate) {
            this.newFinishDate = newFinishDate;
        }
    }
}
