package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VAbyGroupResponseCashcard extends RestResponseCashcard {
    @SerializedName("DT")
    private List<DetailVA> DT;

    public VAbyGroupResponseCashcard() {
    }

    public List<DetailVA> getDT() {
        return DT;
    }

    public class DetailVA implements Serializable {
        @SerializedName("SISA_PLAFON")
        private int sisaPlafon;
        @SerializedName("ID")
        private int ID;
        @SerializedName("FID_CUSTOMERDRAFT")
        private int fIDCustDraft;
        @SerializedName("CORPORATE_CODE")
        private String corporateCode;
        @SerializedName("CORPORATE_NAME")
        private String corporateName;
        @SerializedName("CUSTOMER_CODE")
        private String customerCode;
        @SerializedName("CUSTOMER_NAME")
        private String customerName;
        @SerializedName("GROUP_CODE")
        private int groupCode;
        @SerializedName("GROUP_NAME")
        private String groupName;
        @SerializedName("NOMOR_VIRTUAL")
        private String nomorVirtual;
        @SerializedName("HANDPHONE")
        private String handphone;
        @SerializedName("EMAIL")
        private String email;
        @SerializedName("PLAFON")
        private String plafon;
        @SerializedName("TOTAL_PENARIKAN")
        private int totalPenarikan;
        @SerializedName("TOTAL_SETORAN")
        private int totalSetoran;
        @SerializedName("STATUS")
        private int status;
        @SerializedName("USER_ID")
        private String userID;
        @SerializedName("BRANCH_CODE")
        private String branchCode;
        @SerializedName("NAMA_IBU_KANDUNG")
        private String namaIbu;
        @SerializedName("TANGGAL_LAHIR")
        private String tanggalLahir;
        @SerializedName("JENIS_IDENTITAS")
        private String jenisIdentitas;
        @SerializedName("NO_IDENTITAS")
        private String noIdentitas;
        @SerializedName("TGL_UPDATE")
        private String tglUpdate;
        @SerializedName("NOMOR_KARTU")
        private String nomorKartu;
        @SerializedName("STATUS_KARTU")
        private String statusKartu;
        @SerializedName("TGL_UPDATE_KARTU")
        private String tglUpdateKartu;
        @SerializedName("MASA_BERLAKU_KARTU")
        private String masaBerlakuKartu;

        public DetailVA() {
        }

        public int getSisaPlafon() {
            return sisaPlafon;
        }

        public void setSisaPlafon(int sisaPlafon) {
            this.sisaPlafon = sisaPlafon;
        }

        public int getID() {
            return ID;
        }

        public int getfIDCustDraft() {
            return fIDCustDraft;
        }

        public String getCorporateCode() {
            return corporateCode;
        }

        public String getCorporateName() {
            return corporateName;
        }

        public String getCustomerCode() {
            return customerCode;
        }

        public String getCustomerName() {
            return customerName;
        }

        public int getGroupCode() {
            return groupCode;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getNomorVirtual() {
            return nomorVirtual;
        }

        public String getHandphone() {
            return handphone;
        }

        public void setHandphone(String handphone) {
            this.handphone = handphone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPlafon() {
            return plafon;
        }

        public int getTotalPenarikan() {
            return totalPenarikan;
        }

        public int getTotalSetoran() {
            return totalSetoran;
        }

        public int getStatus() {
            return status;
        }

        public String getUserID() {
            return userID;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public String getNamaIbu() {
            return namaIbu;
        }

        public String getTanggalLahir() {
            return tanggalLahir;
        }

        public String getJenisIdentitas() {
            return jenisIdentitas;
        }

        public String getNoIdentitas() {
            return noIdentitas;
        }

        public String getTglUpdate() {
            return tglUpdate;
        }

        public String getNomorKartu() {
            return nomorKartu;
        }

        public String getStatusKartu() {
            return statusKartu;
        }

        public String getTglUpdateKartu() {
            return tglUpdateKartu;
        }

        public String getMasaBerlakuKartu() {
            return masaBerlakuKartu;
        }
    }
}
