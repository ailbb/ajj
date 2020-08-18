package com.ailbb.ajj.encrypt.util;

import com.ailbb.ajj.$;
import com.ailbb.ajj.encrypt.EncryptUtil;
import com.ailbb.ajj.encrypt.Encryption;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;

/**
 * SM4对称加密
 *
 */
public class Sm4Util implements Encryption {
    public static final String Sm4 = "SM4";
    public static final String ALGORIGTHM_NAME = "SM4";
    /**
     * 编码格式；UTF-8
     */
    public static final String ENCODING = "UTF-8";

    /**
     * SM4
     */
    public int keysizeSM4 = 128;

    private Cipher encipher;
    private Cipher decipher;
    private SecretKeySpec sks;
    private String key = "SM4KEY"; // 默认Key
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS7Padding";
    public static final String ALGORITHM_NAME_CBC_PADDING = "SM4/CBC/PKCS7Padding";

    private static Map<String, Sm4Util> cache = new HashMap<>();

    private Sm4Util() {}

    public Sm4Util(String strkey) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchProviderException, InvalidKeyException {
        this(strkey, ALGORITHM_NAME_CBC_PADDING);
    }

    public Sm4Util(String strkey, String ALGORITHM_NAME_PADDING) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchProviderException, InvalidKeyException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        if (strkey == null || strkey.length() == 0) {
            key = "SM4KEY";
            strkey = "SM4KEY";
        }
        if (sks == null || this.key.compareTo(strkey) != 0) {
            this.key = strkey;
            KeyGenerator kg = KeyGenerator.getInstance(ALGORIGTHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
            if($.isEmptyOrNull(ALGORITHM_NAME_PADDING)) ALGORITHM_NAME_PADDING = ALGORITHM_NAME_CBC_PADDING;
            if (keysizeSM4 == 0) {
                byte[] keyBytes = ENCODING == null ? key.getBytes() : key.getBytes(ENCODING);
                kg.init(new SecureRandom(keyBytes));
            } else if (key == null) {
                kg.init(keysizeSM4);
            } else {
                byte[] keyBytes = ENCODING == null ? key.getBytes() : key.getBytes(ENCODING);
                kg.init(keysizeSM4, new SecureRandom(keyBytes));
            }

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strkey.getBytes());
            kg.init(keysizeSM4, secureRandom);
            SecretKey sk = kg.generateKey();

            sks = new SecretKeySpec(sk.getEncoded(), ALGORIGTHM_NAME);
            encipher = Cipher.getInstance(ALGORITHM_NAME_PADDING, BouncyCastleProvider.PROVIDER_NAME);
            decipher = Cipher.getInstance(ALGORITHM_NAME_PADDING, BouncyCastleProvider.PROVIDER_NAME);
            if(ALGORITHM_NAME_PADDING.equalsIgnoreCase(ALGORITHM_NAME_CBC_PADDING)) {
                AlgorithmParameterSpec paramSpec = new IvParameterSpec(new byte[16]);
                encipher.init(Cipher.ENCRYPT_MODE, sks, paramSpec);
                decipher.init(Cipher.DECRYPT_MODE, sks, paramSpec);
            } else {
                encipher.init(Cipher.ENCRYPT_MODE, sks);
                decipher.init(Cipher.DECRYPT_MODE, sks);
            }
        }

    }

    public static Sm4Util getInstance(String strKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, NoSuchProviderException, InvalidAlgorithmParameterException {
        return getInstance(strKey, ALGORITHM_NAME_CBC_PADDING);
    }

    public static Sm4Util getInstance(String strKey, String ALGORITHM_NAME_PADDING) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if(null == cache.get(strKey)) {
            Sm4Util me = new Sm4Util(strKey, ALGORITHM_NAME_PADDING);
            cache.put(strKey, me);
        }

        return cache.get(strKey);
    }

    /**
     * 加密
     */
    public synchronized String encrypt(String strIn) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        byte[] resBytes = ENCODING == null ? strIn.getBytes() : strIn.getBytes(ENCODING);
        return ByteUtils.toHexString(encipher.doFinal(resBytes));
    }

    /*
     * 解密
     */
    public synchronized String decrypt(String strIn) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        return new String(decipher.doFinal(ByteUtils.fromHexString(strIn)));
    }
}
