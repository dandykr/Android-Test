package com.bri.ojt.Model.Response;

import android.content.Context;

import com.bri.ojt.Util.Consts;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MutasiVAResponse extends RestResponseCashcard {

    @SerializedName("DT")
    private List<DetailMutasi> data;

    public MutasiVAResponse() {
        data = new ArrayList<>();
    }

    public List<DetailMutasi> getData() {
        return data;
    }

    public static class DetailMutasi {

        @SerializedName("NOMOR_SEQAPP")
        private String nomorSeqapp;
        @SerializedName("ID")
        private String ID;
        @SerializedName("NOMOR_VIRTUAL")
        private String nomorVirtual;
        @SerializedName("PLAFON_BARU")
        private double plafonBaru;
        @SerializedName("NO_REK_DB")
        private String noRekDB;
        @SerializedName("NAMA_REK_DB")
        private String namaRekDB;
        @SerializedName("CHANNEL")
        private String channel;
        @SerializedName("TGL_TRX")
        private String tglTrxi;
        @SerializedName("KETERANGAN")
        private String keterangan;
        @SerializedName("JENIS_TRANSAKSI")
        private String jenisTransaksi;
        @SerializedName("DORC")
        private String DORC;
        @SerializedName("SALDO_AWAL")
        private double saldoAwal;
        @SerializedName("AMOUNT_DEBET")
        private double amountDebet;
        @SerializedName("AMOUNT_KREDIT")
        private double amountKredit;
        @SerializedName("SALDO_AKHIR")
        private double saldoAkhir;
        @SerializedName("NOMOR_SEQBRINETS")
        private String nomoraSeqbrinets;
        @SerializedName("CORPORATE_CODE")
        private String corporateCode;
        @SerializedName("CORPORATE_NAME")
        private String corporateName;
        @SerializedName("CUSTOMER_CODE")
        private String customerCode;
        @SerializedName("CUSTOMER_NAME")
        private String customerName;
        @SerializedName("GROUP_CODE")
        private String groupCode;
        @SerializedName("GROUP_NAME")
        private String groupName;

        public DetailMutasi() {
        }

        public DetailMutasi(String nomorSeqapp, String ID, String nomorVirtual, double plafonBaru, String noRekDB, String namaRekDB, String channel, String tglTrxi, String keterangan, String jenisTransaksi, String DORC, double saldoAwal, double amountDebet, double amountKredit, double saldoAkhir, String nomoraSeqbrinets, String corporateCode, String corporateName, String customerCode, String customerName, String groupCode, String groupName) {
            this.nomorSeqapp = nomorSeqapp;
            this.ID = ID;
            this.nomorVirtual = nomorVirtual;
            this.plafonBaru = plafonBaru;
            this.noRekDB = noRekDB;
            this.namaRekDB = namaRekDB;
            this.channel = channel;
            this.tglTrxi = tglTrxi;
            this.keterangan = keterangan;
            this.jenisTransaksi = jenisTransaksi;
            this.DORC = DORC;
            this.saldoAwal = saldoAwal;
            this.amountDebet = amountDebet;
            this.amountKredit = amountKredit;
            this.saldoAkhir = saldoAkhir;
            this.nomoraSeqbrinets = nomoraSeqbrinets;
            this.corporateCode = corporateCode;
            this.corporateName = corporateName;
            this.customerCode = customerCode;
            this.customerName = customerName;
            this.groupCode = groupCode;
            this.groupName = groupName;
        }

        public void setNomorSeqapp(String nomorSeqapp) {
            this.nomorSeqapp = nomorSeqapp;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public void setNomorVirtual(String nomorVirtual) {
            this.nomorVirtual = nomorVirtual;
        }

        public void setPlafonBaru(double plafonBaru) {
            this.plafonBaru = plafonBaru;
        }

        public void setNoRekDB(String noRekDB) {
            this.noRekDB = noRekDB;
        }

        public void setNamaRekDB(String namaRekDB) {
            this.namaRekDB = namaRekDB;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public void setTglTrxi(String tglTrxi) {
            this.tglTrxi = tglTrxi;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        public void setJenisTransaksi(String jenisTransaksi) {
            this.jenisTransaksi = jenisTransaksi;
        }

        public void setDORC(String DORC) {
            this.DORC = DORC;
        }

        public void setSaldoAwal(double saldoAwal) {
            this.saldoAwal = saldoAwal;
        }

        public void setAmountDebet(double amountDebet) {
            this.amountDebet = amountDebet;
        }

        public void setAmountKredit(double amountKredit) {
            this.amountKredit = amountKredit;
        }

        public void setSaldoAkhir(double saldoAkhir) {
            this.saldoAkhir = saldoAkhir;
        }

        public void setNomoraSeqbrinets(String nomoraSeqbrinets) {
            this.nomoraSeqbrinets = nomoraSeqbrinets;
        }

        public void setCorporateCode(String corporateCode) {
            this.corporateCode = corporateCode;
        }

        public void setCorporateName(String corporateName) {
            this.corporateName = corporateName;
        }

        public void setCustomerCode(String customerCode) {
            this.customerCode = customerCode;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public void setGroupCode(String groupCode) {
            this.groupCode = groupCode;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getNomorSeqapp() {
            return nomorSeqapp;
        }

        public String getID() {
            return ID;
        }

        public String getNomorVirtual() {
            return nomorVirtual;
        }

        public double getPlafonBaru() {
            return plafonBaru;
        }

        public String getNoRekDB() {
            return noRekDB;
        }

        public String getNamaRekDB() {
            return namaRekDB;
        }

        public String getChannel() {
            return channel;
        }

        public String getTglTrxi() {
            return tglTrxi;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public String getJenisTransaksi() {
            return jenisTransaksi;
        }

        public String getDORC() {
            return DORC;
        }

        public double getSaldoAwal() {
            return saldoAwal;
        }

        public double getAmountDebet() {
            return amountDebet;
        }

        public double getAmountKredit() {
            return amountKredit;
        }

        public double getSaldoAkhir() {
            return saldoAkhir;
        }

        public String getNomoraSeqbrinets() {
            return nomoraSeqbrinets;
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

        public String getGroupCode() {
            return groupCode;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getTrxDateOnly() {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.US);
            SimpleDateFormat dateOnly = new SimpleDateFormat("dd/MM/yyy", Locale.US);

            Date formatted = null;
            try {
                formatted = format.parse(tglTrxi);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dateOnly.format(formatted);

        }

        public String getTrxDay() {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.US);
            SimpleDateFormat dateOnly = new SimpleDateFormat("dd", Locale.US);

            Date formatted = null;
            try {
                formatted = format.parse(tglTrxi);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dateOnly.format(formatted);
        }

        public String getTrxMonth() {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.US);
            SimpleDateFormat monthOnly = new SimpleDateFormat("MM", Locale.US);

            Date formatted = null;
            try {
                formatted = format.parse(tglTrxi);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return monthOnly.format(formatted);
        }
    }

    public static MutasiVAResponse getExample(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("example.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return Consts.getInstance().getGson().fromJson(json, MutasiVAResponse.class);
    }

}
