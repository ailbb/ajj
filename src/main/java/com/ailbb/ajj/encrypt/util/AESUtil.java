package com.ailbb.ajj.encrypt.util;

import com.ailbb.ajj.encrypt.EncryptUtil;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * AES对称加密
 *
 */
public class AESUtil {
    public static final String AES = "AES";
    /**编码格式；UTF-8*/
    public String charset = "UTF-8";
    /**AES*/
    public int keysizeAES = 128;

    private Cipher encipher;
    private Cipher decipher;
    private SecretKeySpec sks;
    private String key="AESKEY";

    private static Map<String,AESUtil> cache = new HashMap<>();

    public AESUtil(){}

    /**
     *
     * @param strkey
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    private void Init(String strkey) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException {
        if(strkey==null || strkey.length()==0)
        {
            key="AESKEY";
            strkey="AESKEY";
        }
        if(sks==null ||this.key.compareTo(strkey)!=0)
        {
            this.key = strkey;
            KeyGenerator kg = KeyGenerator.getInstance(AES);
            if (keysizeAES == 0) {
                byte[] keyBytes = charset==null?key.getBytes():key.getBytes(charset);
                kg.init(new SecureRandom(keyBytes));
            }else if (key==null) {
                kg.init(keysizeAES);
            }else {
                byte[] keyBytes = charset==null?key.getBytes():key.getBytes(charset);
                kg.init(keysizeAES, new SecureRandom(keyBytes));
            }

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strkey.getBytes());
            kg.init(128, secureRandom);
            SecretKey sk = kg.generateKey();

            sks = new SecretKeySpec(sk.getEncoded(), AES);
            encipher = Cipher.getInstance(AES);
            decipher = Cipher.getInstance(AES);
            encipher.init(Cipher.ENCRYPT_MODE, sks);
            decipher.init(Cipher.DECRYPT_MODE, sks);
        }

    }

    public AESUtil getInstance(String strKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        if(null == cache.get(strKey)) {
            AESUtil me = new AESUtil();
            me.Init(strKey);
            cache.put(strKey, me);
        }
        return cache.get(strKey);
    }

    /**
     * 加密
     */
    public synchronized String encrypt(String strIn) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        byte[] resBytes = charset==null?strIn.getBytes():strIn.getBytes(charset);
        return EncryptUtil.parseByte2HexStr(encipher.doFinal(resBytes));
    }

    /*
     * 解密
     */
    public synchronized String decrypt( String strIn) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        return new String(decipher.doFinal(EncryptUtil.parseHexStr2Byte(strIn)));
    }
}