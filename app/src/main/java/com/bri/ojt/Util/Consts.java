package com.bri.ojt.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.bri.ojt.Model.DataEncrypt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Consts {
    private static Consts consts;
    private SharedPreferences sPreferences;
    private static final String KEY_SHARED_PREFERENCE   = "shared_preference";
    private static final String KEY_SHARED_TOKEN    = "token";
    private static final String KEY_LOGIN_VA        = "virtual_account";
    public static final String KEY_CLIENT_ID_       = "A85TLHHwyAwhCF7VWGL8w821eXjfH5Th";
    public static final String KEY_CLIENT_SECRET_   = "k0AGlOhz09cg0l6C";

    public static final String KEY_CLIENT_ID        = "EESDGkhUd9RzdR7bAxxGvDitzqSv6RTH";
    public static final String KEY_CLIENT_SECRET    = "av09NnFNPYethLMT";

    public static final String BASE_URL_1           = "eyJlbmNyeXB0ZWQiOiJCbG9FajlHYlM1ai8zeDZjQkwxWXcwWlFRR3pFR2VFZlErU3h5S3VpTnFjXHUwMDNkIiwiaXYiOiJNSFljcTZheDR2VFIwenB4U0tLVi9nXHUwMDNkXHUwMDNkIiwic2FsdCI6IkpUVEJDYkViTXhzRzZ3dS9oSkppcWF5VGFlbXR2eW5sL1FYR0QxUzhuWi9TSHJCV3JYNjFVZGFML0VnK0NKTXNFSmpyRTY1Q3FrQm1uUnNRSGxrOUx2N2J6OGs2N0tWZThBVlZmaGl3K2RCdUU3a1A0a2FsMkRheFJUaFFzNk1nWDdicXdFYnpOczI2VXFGQXpHYlZ4cktJaWl6QnVXMFdaN3FvMDRHaDFnc3c2dWFvVGQvVjdlalFpVkZBUExnbmNDdWFNbHJiZGdFT2piMWlBZkFkWmxsQ29SQlcyRHVEN0VPSkVDd0JBVFBISlA3UTl0NHd4eTk3aU9QckVKakhTc3VUVG0rVExiSGJFTGhGZUIyQzZOa1NCVGVyMnR5NThBZEVOQ3JiUjFVTE92eFhDRDlSeGQxaGd6YTlqWWs5cU54VmRkd0hUN1AvMGdWR2FyQmVZd1x1MDAzZFx1MDAzZCJ9";
    public static final String SSL_PUBLIC_KEY_1     = "sha256/2SYcaiY9VjjHmCGRlS3ADAbiPs4NpdfFQGOSIk6rJuo=";
    public static final String BASE_URL_2           = "eyJlbmNyeXB0ZWQiOiJUOW9yclFNNzBiZDFFU213dXpnYkRtdFg2a0xMVmtLRkRwZHl4ODYydzM4XHUwMDNkIiwiaXYiOiJSOC9DZncyQXNYZWxqeDRIbk5KUHZ3XHUwMDNkXHUwMDNkIiwic2FsdCI6InE2MHlFWXNjSHlsWEpyTWJhejFjNXZIYVFmWjZhRmFXRjlKWi92MTVOSTVGZDZ6ZlVONHlrWG1aL3MyV3lTZWhTUUNDaysrMkpaOVQrVG42UVd3M0lPa1JCM2phemdESnJSMW9DdVhMNnRkaGlubG5MNmU2ZXR4M3JFTlFLVjk1cjB1OUJubzdwWVRSbXBzMUlvWU51M21HNlc1bVQyMTlYNUowSFVXd1RGOEpIYjdIckFEK0s1VUs1emdhSk1PYk5mZHBabU1OR1lYTjFWRCt5UWduZnVFT0NYMzh5emZ5NnU2eG8wM293MlVwTzNFUll6THlYQ2k0OTNzYzVlWDZOdzdpTUJmZFVMWkgzRXJleDh0OUtRTGlOMEFKRnVncXJDdktSMk9URENzWG1SVGc5MWxSUmZ0dWs2VndOOHJTcHArNFlpSmc2azlJV1lXUmhRZ05EZ1x1MDAzZFx1MDAzZCJ9";
    public static final String SSL_PUBLIC_KEY_2     = "sha256/wLJblDY2J5AM14K6eA8/UAkujZhGD+F/2zeUcIQsdsc=";

    public static final String PATH_VA_BY_GROUP     = "cashcard2-sandbox/va/bygroup";
    public static final String PATH_VA_REGISTER     = "cashcard2-sandbox/va";
    public static final String PATH_CREATE_BRIVA    = "sandbox/v1/briva";
    public static final String PATH_VA_BY_VA        = "cashcard2-sandbox/va/byva";
    public static final String PATH_ACC_INFO_INTERNAL = "sandbox/v2/transfer/internal/accounts";
    public static final String PATH_TRANSFER_INTERNAL = "sandbox/v2/transfer/internal";
    public static final String PATH_MUTASI          = "cashcard2-sandbox/mutasi";
    public static final String PATH_OTP_TELLER      = "cashcard2-sandbox/otp/teller";
    public static final String PATH_OTP_ECHANNEL    = "cashcard2-sandbox/otp/echannel";
    public static final String PATH_LIST_OTHER_BANK = "sandbox/v2/transfer/external/accounts";
    public static final String PATH_ACC_INFO_EXTERNAL = "sandbox/v2/transfer/external/accounts";
    public static final String PATH_TRANSFER_EXTERNAL = "sandbox/v2/transfer/external";
    public static final String PATH_LOCATION        = "v1/location/near";


    public static final String KEY_IS_LOGIN = "is_login";
    public static final String KEY_TRANSFER_INFO_DIALOG = "transfer_info";
    public static final String KEY_TRANSFER_TYPE    = "transfer_type";
    public static final int TRANSFER_INFO_INTERNAL  = 100;
    public static final String FEE_INTERNAL_TF      = "OUR";
    public static final String KEY_TARTUN_OTP       = "tarik_tunai_otp";
    public static final String KEY_TARTUN_FROM      = "tarik_tunai_from";
    public static final int TARTUN_ECHANNEL         = 100;
    public static final int TARTUN_TELLER           = 200;
    public static final int DIALOG_TRANSFER         = 100;
    public static final int DIALOG_TARTUN           = 200;
    public static final String KEY_SESSION_END      = "session_end";
    private static final String KEY_NO_VA_ENCRYPTED = "key_va";
    private static final String KEY_TOKEN           = "key_token";
    private static final String KEY_DEVICE_ID       = "device_id";
    private static final String KEY_C_ID            = "eyJlbmNyeXB0ZWQiOiJnOFg2WGhrZ3BPbkl5Tm5yN0prRTdZME1NOVFsQVRnYThENHlUQjFqQkIwREZtSUJQVWdRdGVnaGlHOEhGaVRjIiwiaXYiOiJ0OGtWMkF6MHlacEtNS0xUaXZOZUt3XHUwMDNkXHUwMDNkIiwic2FsdCI6IkZwMHhMZldwYnZMRzluTFJTZVVIU2VaRjdGRWx0R3NDemR1MDJQQjJPUVkyS2R0TTNnSFFOdXMvOHFZMkdYKzJVOUZwRzNkUVVmWmFTVUlQNERHeWYxeCsvNDhkTUFPaFZLNFQyUWdFc2J0L1RBcUNPNnMvcXZ3WWZ0a3dYdjhGTFVYYTcrdmF1aHFqb3JpL2tWdXR1MGhKcEdqYy8vQUp3OHk4MDJSZjZsd1Y1ZEtlT21MUzFxdXB0YkcwbFNRMEpSYVYyNlZUSm81VlJOWjkrWmlMeVk1eFBJS1hjNmhIaklXaFgrd3djamlDK1A3cUQ2cVB1TTZUcHpoTFlEWlNic3ExZ05mT3J1RzhMTVg3RVA1ck0xdW5kNGRpbTdzSDFzdVpEbDJhUFdOWFdUcGgvRnVDY1E5OGJTSzBKbWZGTzQ1S2tDMVRUNXJ1b2tHbmY0QjN2QVx1MDAzZFx1MDAzZCJ9";
    private static final String KEY_C_SECRET        = "eyJlbmNyeXB0ZWQiOiJ3aVc4VHVKRTFTL0pNNzluV3JLci9Xa09WeS9LcUlNTG9QR2Ztd3dRY0JBXHUwMDNkIiwiaXYiOiJzbWlLcXJYcUQxQWREb0JSSTRvajFnXHUwMDNkXHUwMDNkIiwic2FsdCI6InpGRTh1SmhGdWllNmdjdHp1ZDBTRGdlQ3dVL1lzZmRrUzREY3JMUlE2dEgyS3FuVlpiUnd4bGhWODhid3g5Z3c3TG81ZWhYU3YvWVJiZ2IwMW9QNVhVb0ZhckpKWkwreDdZUm9TOVdUZkRkZ0FYVmU0L0w2YlB4WWVheVlpbFkrazVnaUhHTCtRdUYxTlZoTVJmaUZad0NSMlBKdTBNS2x0UVJCQjJXb0lOQzZCdk91eXlSckMwc2FSRDBQZ1czbkx2OGVjQVMvZUFDMjhuUUdhZVBuS2FTZ0w0bVliVjd2MEJ3bjE0dE1hTWRvR3JqeGJyQ1c2d0NsTmdwTVdvK1Rwb3o4RkFIbVVmVmt3OVVyNkQwZGRMZVNvTW1hdVliSnNta21ZQVF1MFcyV0RRLy9YUkxZMkIyeWwvdS8xZTF6ZGtYaXNSN3VOVWx0QzVLS3hPS0RSQVx1MDAzZFx1MDAzZCJ9";
    private static final String FCM_KEY             = "eyJlbmNyeXB0ZWQiOiJDQzk2SWVsUWhDRCszcjk2VXMxcUk4OElXNVZHeEdhbTRWTlNVR1NwbnovODgvUkFMUmM2N0hwWk9jL1hLcEsvVXd2WUdlMlhvZmhQbVVLdHpVVVZqemdwTjdwSEJXbGt5a1JoNXhTYXozUW5oVXJoRFlQb2Zxdkx4aGovRTAwYjNCT0JzUVhuVmt6dU1kLzFRYWJtU1RrOThIV0p4MWhEZlZKUktmbXVIMkRBR1NjNnFZd3FTMHlqTjBMZk9JQUhaL094YS8wWGVURWdwUEJHb3ZQem13XHUwMDNkXHUwMDNkIiwiaXYiOiIwQ2o0TzFwc05JWS95SUZPbG1VeU5RXHUwMDNkXHUwMDNkIiwic2FsdCI6IldVak1WcVdSRTNxSG9vaTZPZ1czTVpTSXVUY0U2NUhUUGNOTUprWlVtUzZSSEh6SDJJOU9kdlppSjN1K20zVWFXbDBnaXdncHpQUFVlY1B1SnV1VU93NmxOSnJMeUppbWJmaHZsMGVjck1ZYWU2RngyaytpRVBwM3ZrK1pWSGtRTi81VFFzZ2NEUU50R1llcG0zMDRXVndaMVFrZkR5eE1lN2NlZEdENEhjZmxEWlFydFFDeGZFNWlETlVxaWpPN2NkNHZSMnhwT1BRS3c3dFROc0NmSGlZSjcvWGIxK2RqanJWZG9relV2cktXTUhTaXV0MStzOURVb1YwclpSdUlRY3o4cFp0Y1F1Z3MyNlN1cHZDR3VJdTFSbml0cUxjR29tQm96ZHMwaUhMNFRSdVdwNndXNE16M01UM2h0TlB4T2tlTXNBUEpudEhSQTlhcHlMaWxZZ1x1MDAzZFx1MDAzZCJ9";

    public static final int CODE_SUCCESS            = 200;
    public static final int CODE_UNAUTHORIZED       = 401;

    private final Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .create();

    public Consts() {
    }

    public static Consts getInstance() {
        if (consts == null) {
            consts = new Consts();
        }
        return consts;
    }

    void setsPreferences(Context context) {
        sPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void setToken(String token) {
        SharedPreferences.Editor edit = sPreferences.edit();
        edit.putString(KEY_SHARED_TOKEN, token);
        edit.apply();
    }

    public Gson getGson() {
        return gson;
    }

    public void setVAEncrypt(String noVA) {
        DataEncrypt dataEncrypt = SecurityUtils.encryptBytes(noVA.getBytes());
        String rawEncrypt = SecurityUtils.encryptBase64(gson.toJson(dataEncrypt));
        SharedPreferences.Editor edit = sPreferences.edit();
        edit.putString(KEY_NO_VA_ENCRYPTED, rawEncrypt);
        edit.apply();
    }

    public String getVAEncrypt() {
        String rawData = sPreferences.getString(KEY_NO_VA_ENCRYPTED, "");
        if (rawData != null) {
            if (!rawData.isEmpty()) {
                String rawDecrypt = SecurityUtils.decryptBase64(rawData);
                DataEncrypt dataEncrypt = gson.fromJson(rawDecrypt, DataEncrypt.class);
                return new String(SecurityUtils.decryptData(dataEncrypt));
            }
        }
        return "";
    }

    public void setTokenEncrypt(String token) {
        DataEncrypt dataEncrypt = SecurityUtils.encryptBytes(token.getBytes());
        String rawEncrypt = SecurityUtils.encryptBase64(gson.toJson(dataEncrypt));
        SharedPreferences.Editor edit = sPreferences.edit();
        edit.putString(KEY_TOKEN, rawEncrypt);
        edit.apply();
    }

    public String getToken() {
        String rawData = sPreferences.getString(KEY_TOKEN, "");
        if (rawData != null) {
            if (!rawData.isEmpty()) {
                String rawDecrypt = SecurityUtils.decryptBase64(rawData);
                DataEncrypt dataEncrypt = gson.fromJson(rawDecrypt, DataEncrypt.class);
                return new String(SecurityUtils.decryptData(dataEncrypt));
            }
        }
        return "";
    }

    public void setIsLogin(boolean isLogin) {
        SharedPreferences.Editor edit = sPreferences.edit();
        edit.putBoolean(KEY_IS_LOGIN, isLogin);
        edit.apply();
    }

    public boolean isLogin() {
        return sPreferences.getBoolean(KEY_IS_LOGIN, false);
    }

    public String getCIDEncrypt() {
        String rawDecrypt = SecurityUtils.decryptBase64(KEY_C_ID);
        DataEncrypt rawData = gson.fromJson(rawDecrypt, DataEncrypt.class);
        byte[] decryptBytes = SecurityUtils.decryptData(rawData);
        return new String(decryptBytes);
    }

    public String getCSecretEncrypt() {
        String rawDecrypt = SecurityUtils.decryptBase64(KEY_C_SECRET);
        DataEncrypt rawData = gson.fromJson(rawDecrypt, DataEncrypt.class);
        byte[] decryptBytes = SecurityUtils.decryptData(rawData);
        return new String(decryptBytes);
    }

    public String getBaseUrl1() {
        String rawDecrypt = SecurityUtils.decryptBase64(BASE_URL_1);
        DataEncrypt rawData = gson.fromJson(rawDecrypt, DataEncrypt.class);
        byte[] decryptBytes = SecurityUtils.decryptData(rawData);
        return new String(decryptBytes);
    }

    public String getBaseUrl2() {
        String rawDecrypt = SecurityUtils.decryptBase64(BASE_URL_2);
        DataEncrypt rawData = gson.fromJson(rawDecrypt, DataEncrypt.class);
        byte[] decryptBytes = SecurityUtils.decryptData(rawData);
        return new String(decryptBytes);
    }

    public String getFCMKey() {
        String rawDecrypt = SecurityUtils.decryptBase64(FCM_KEY);
        DataEncrypt rawData = gson.fromJson(rawDecrypt, DataEncrypt.class);
        byte[] decryptBytes = SecurityUtils.decryptData(rawData);
        return new String(decryptBytes);
    }

    public void setDeviceId(String deviceId) {
        SharedPreferences.Editor edit = sPreferences.edit();
        edit.putString(KEY_DEVICE_ID, deviceId);
        edit.apply();
    }

    public String getDeviceId() {
        return sPreferences.getString(KEY_DEVICE_ID, "");
    }
}
