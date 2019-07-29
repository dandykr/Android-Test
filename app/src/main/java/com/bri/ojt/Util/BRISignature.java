package com.bri.ojt.Util;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class BRISignature {
    public static String getSignature(String value, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        String hash = Base64.encodeToString(sha256_HMAC.doFinal(value.getBytes()), Base64.DEFAULT);
        String cropped = hash.replace("\n", "");

        return cropped;
    }

    public static String getTimestamp() {
        // Input
        Date date = new Date(System.currentTimeMillis());

        // Conversion
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timeStamp = sdf.format(date);
        return timeStamp;
    }

    public static Map<String, String> getHeader (String path, String method, String token, String body) {
        String timestamp = getTimestamp();

        String payload = "path=/" + path + "&verb=" + method + "&token=Bearer " + token + "&timestamp=" + timestamp +
                "&body=" + body;

        String briSignature = "";

        try {
            briSignature = BRISignature.getSignature(payload, Consts.KEY_CLIENT_SECRET);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>();
        map.put("bri-signature", briSignature);
        map.put("bri-timestamp", timestamp);
        map.put("content-type", "application/json");
        map.put("authorization", "Bearer " + token);
        map.put("Accept", "application/json");

        return map;
    }

    public static Map<String, String> getHeaderv2 (String path, String method, String token, String body) {
        String timestamp = getTimestamp();

        String payload = "path=/" + path + "&verb=" + method + "&token=Bearer " + token + "&timestamp=" + timestamp +
                "&body=" + body;

        String briSignature = "";

        try {
            briSignature = BRISignature.getSignature(payload, Consts.KEY_CLIENT_SECRET);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>();
        map.put("bri-signature", briSignature);
        map.put("bri-timestamp", timestamp);
        map.put("authorization", "Bearer " + token);
        map.put("Accept", "*/*");

        return map;
    }
}
