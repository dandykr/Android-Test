package com.bri.ojt.Util;

import android.util.Base64;

import com.bri.ojt.Model.DataEncrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtils {

    public static DataEncrypt encryptBytes(byte[] plainTextBytes)
    {
        DataEncrypt dataEncrypt = null;
        String pw = "BRICashCard2019";
        try
        {
            //Random salt for next step
            SecureRandom random = new SecureRandom();
            byte salt[] = new byte[256];
            random.nextBytes(salt);

            //PBKDF2 - derive the key
            char[] passwordChar = pw.toCharArray(); //Turn password into char[] array
            PBEKeySpec pbKeySpec = new PBEKeySpec(passwordChar, salt, 1200, 256); //1324 iterations
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] keyBytes = secretKeyFactory.generateSecret(pbKeySpec).getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            //Create initialization vector for AES
            SecureRandom ivRandom = new SecureRandom(); //not caching previous seeded instance of SecureRandom
            byte[] iv = new byte[16];
            ivRandom.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            //Encrypt
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(plainTextBytes);
            dataEncrypt = new DataEncrypt(Base64.encodeToString(salt, Base64.NO_WRAP),
                    Base64.encodeToString(iv, Base64.NO_WRAP),
                    Base64.encodeToString(encrypted, Base64.NO_WRAP));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return dataEncrypt;
    }

    public static byte[] decryptData(DataEncrypt dataEncrypt)
    {
        byte[] decrypted = null;
        String pw = "BRICashCard2019";
        try
        {
            byte salt[] = Base64.decode(dataEncrypt.getSalt(), Base64.NO_WRAP);
            byte iv[] = Base64.decode(dataEncrypt.getIv(), Base64.NO_WRAP);
            byte encrypted[] = Base64.decode(dataEncrypt.getEncrypted(), Base64.NO_WRAP);


            char[] passwordChar = pw.toCharArray();
            PBEKeySpec pbKeySpec = new PBEKeySpec(passwordChar, salt, 1200, 256);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] keyBytes = secretKeyFactory.generateSecret(pbKeySpec).getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            //Decrypt
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            decrypted = cipher.doFinal(encrypted);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return decrypted;
    }

    public static String encryptBase64(String data) {
        return Base64.encodeToString(data.getBytes(), Base64.NO_WRAP);
    }

    public static String decryptBase64(String data) {
        return new String(Base64.decode(data, Base64.NO_WRAP));
    }
}
