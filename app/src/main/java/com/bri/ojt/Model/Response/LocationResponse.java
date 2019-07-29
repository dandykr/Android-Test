package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationResponse extends RestResponse {
    @SerializedName("data")
    public List<Location> data;

    public List<Location> getData() {
        return data;
    }

    public static class Location {
        @SerializedName("id")
        private String id;
        @SerializedName("kanwil")
        private String kanwil;
        @SerializedName("unit_kerja")
        private String unitKerja;
        @SerializedName("unit_induk")
        private String unitInduk;
        @SerializedName("kanca_induk")
        private String kancaInduk;
        @SerializedName("jenis_uker")
        private String jenisUker;
        @SerializedName("kode_uker")
        private String kodeUker;
        @SerializedName("dati2")
        private String dati2;
        @SerializedName("dati1")
        private String dati1;
        @SerializedName("no_telp")
        private String noTelp;
        @SerializedName("no_fax")
        private String noFax;
        @SerializedName("mid")
        private String mid;
        @SerializedName("tid")
        private String tid;
        @SerializedName("nama_merchant")
        private String namaMerchant;
        @SerializedName("propinsi")
        private String provinsi;
        @SerializedName("kotakab")
        private String kotakab;
        @SerializedName("kecamatan")
        private String kecamatan;
        @SerializedName("keldes")
        private String keldes;
        @SerializedName("last_update")
        private String lastUpdate;
        @SerializedName("alamat")
        private String alamat;
        @SerializedName("lokasi")
        private String lokasi;
        @SerializedName("latitude")
        private String latitude;
        @SerializedName("longitude")
        private String longitude;
        @SerializedName("hari_operasional")
        private String hariOperasional;
        @SerializedName("jam_operasional")
        private String jamOperasional;
        @SerializedName("kodepos")
        private String kodepos;

        public String getId() {
            return id;
        }

        public String getKanwil() {
            return kanwil;
        }

        public String getUnitKerja() {
            return unitKerja;
        }

        public String getUnitInduk() {
            return unitInduk;
        }

        public String getKancaInduk() {
            return kancaInduk;
        }

        public String getJenisUker() {
            return jenisUker;
        }

        public String getKodeUker() {
            return kodeUker;
        }

        public String getDati2() {
            return dati2;
        }

        public String getDati1() {
            return dati1;
        }

        public String getNoTelp() {
            return noTelp;
        }

        public String getNoFax() {
            return noFax;
        }

        public String getMid() {
            return mid;
        }

        public String getTid() {
            return tid;
        }

        public String getNamaMerchant() {
            return namaMerchant;
        }

        public String getProvinsi() {
            return provinsi;
        }

        public String getKotakab() {
            return kotakab;
        }

        public String getKecamatan() {
            return kecamatan;
        }

        public String getKeldes() {
            return keldes;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public String getAlamat() {
            return alamat;
        }

        public String getLokasi() {
            return lokasi;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getHariOperasional() {
            return hariOperasional;
        }

        public String getJamOperasional() {
            return jamOperasional;
        }

        public String getKodepos() {
            return kodepos;
        }
    }
}
