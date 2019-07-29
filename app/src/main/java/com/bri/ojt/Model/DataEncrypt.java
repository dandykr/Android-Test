package com.bri.ojt.Model;

import java.io.Serializable;

public class DataEncrypt implements Serializable {
    private String salt;
    private String iv;
    private String encrypted;

    public DataEncrypt(String salt, String iv, String encrypted) {
        this.salt = salt;
        this.iv = iv;
        this.encrypted = encrypted;
    }

    public String getSalt() {
        return salt;
    }

    public String getIv() {
        return iv;
    }

    public String getEncrypted() {
        return encrypted;
    }
}
