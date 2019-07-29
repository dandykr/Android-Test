package com.bri.ojt.Model.Request;

import com.google.gson.annotations.SerializedName;

public class ReqLocation {
    @SerializedName("tipe")
    private String tipe;
    @SerializedName("radius")
    private String radius;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

    public ReqLocation(String tipe, String radius, String latitude, String longitude) {
        this.tipe = tipe;
        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
