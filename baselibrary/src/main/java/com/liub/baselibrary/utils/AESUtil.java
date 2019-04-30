package com.liub.baselibrary.utils;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * aes加密工具
 * Created by liub on 2018/9/30
 */
public class AESUtil {

    public final static String AESKEY = "dafsdgrwdafsdgss";

    private static volatile AESUtil aesUtil;

    public static AESUtil getInstance() {
        if (aesUtil == null)
            synchronized (AESUtil.class) {
                if (aesUtil == null)
                    aesUtil = new AESUtil();
            }
        return aesUtil;
    }

    private AESUtil() {
    }

    /**
     * 加密
     */
    public String encrypt(String sSrc) {
        return encrypt(sSrc, AESKEY);
    }

    /**
     * 加密
     */
    public String encrypt(String sSrc, String key) {
        try {
            byte[] keyByte = generalKey(key);
            byte[] ivByte = generalIv(key);
            SecretKeySpec skeySpec = new SecretKeySpec(keyByte, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
            IvParameterSpec _iv = new IvParameterSpec(ivByte);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, _iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            return Base64.encodeToString(encrypted, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 解密
     */
    public String decrypt(String sSrc) {
        return decrypt(sSrc, AESKEY);
    }

    /**
     * 解密
     */
    public String decrypt(String sSrc, String key) {
        try {
            byte[] keyByte = generalKey(key);
            byte[] ivByte = generalIv(key);
            SecretKeySpec skeySpec = new SecretKeySpec(keyByte, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec _iv = new IvParameterSpec(ivByte);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, _iv);
            byte[] encrypted = Base64.decode(sSrc, Base64.DEFAULT);// 先用base64解码
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 构建密钥字节码
     */
    private byte[] generalKey(String keyStr) throws Exception {
        byte[] bytes = keyStr.getBytes("utf-8");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes);
        return md.digest();
    }

    /**
     * 构建加解密向量字节码
     */
    private byte[] generalIv(String keyStr) throws Exception {
        byte[] bytes = keyStr.getBytes("utf-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        return md.digest();
    }
}
