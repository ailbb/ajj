package com.ailbb.ajj.encrypt;


import com.ailbb.ajj.$;
import com.ailbb.ajj.encrypt.util.AESUtil;
import com.ailbb.ajj.encrypt.util.EncryptType;
import com.ailbb.ajj.encrypt.util.Sm4Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class EncryptUtil implements EncryptUtilApi {

    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA1";
    public static final String HmacMD5 = "HmacMD5";
    public static final String HmacSHA1 = "HmacSHA1";
    public static final String DES = "DES";
    public static final String AES = "AES";
    public static final String SM4 = "SM4";
    public static final String staticKey = "AILBB";
    public static EncryptUtil me;
    /**
     * 编码格式；默认null为GBK
     */
    public String charset = "UTF-8";

    //大批量字符加解密时报 Cipher not initialized
    private static HashMap decryptCipherMap = new HashMap();//解决该问题：https://asuwing712.iteye.com/blog/1553344

    private EncryptUtil() { }

    AESUtil au = new AESUtil();

    Sm4Util sm4 = new Sm4Util();

    public static EncryptUtil getInstance() {
        if (me == null) {
            me = new EncryptUtil();
        }
        return me;
    }

    /*
     * 将二进制转换成16进制
     */
    public static String parseByte2HexStr(byte buf[]) {
        return $.byter.parseByte2HexStr(buf);
    }

    /*
     * 将16进制转换为二进制
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        return $.byter.parseHexStr2Byte(hexStr);
    }

    /**
     * 使用MessageDigest进行单向加密（无密码）
     */
    private String messageDigest(String res, String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
        return parseByte2HexStr(md.digest(resBytes));
        // return base64(md.digest(resBytes));
    }

    /**
     * 使用KeyGenerator进行单向/双向加密（可设密码）
     */
    private String keyGeneratorMac(String res, String algorithm, String key) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {

        SecretKey sk = null;
        if (key == null) {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            sk = kg.generateKey();
        } else {
            byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
            sk = new SecretKeySpec(keyBytes, algorithm);
        }
        Mac mac = Mac.getInstance(algorithm);
        mac.init(sk);
        byte[] result = mac.doFinal(res.getBytes());
        return base64(result);
    }

    /**
     * 使用KeyGenerator双向加密，DES/AES/SM4，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
     */
    private String keyGeneratorES(String res, String algorithm, String key, int keysize, boolean isEncode) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        KeyGenerator kg = KeyGenerator.getInstance(algorithm);
        if (keysize == 0) {
            byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
            kg.init(new SecureRandom(keyBytes));
        } else if (key == null) {
            kg.init(keysize);
        } else {
            byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
            kg.init(keysize, new SecureRandom(keyBytes));
        }
        SecretKey sk = kg.generateKey();
        SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
        Cipher cipher = (Cipher) decryptCipherMap.get(algorithm);
        if (cipher == null) {//判断是否已经存在实例，如果存在不在实例化。

            cipher = Cipher.getInstance(algorithm);
            decryptCipherMap.put(algorithm, cipher);

        }
        if (isEncode) {
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
            return parseByte2HexStr(cipher.doFinal(resBytes));
        } else {
            cipher.init(Cipher.DECRYPT_MODE, sks);
            return new String(cipher.doFinal(parseHexStr2Byte(res)));
        }

    }

    private String base64(byte[] res) {
        return Base64.encode(res);
    }

    @Override
    public String MD5(String res) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return messageDigest(res, MD5);
    }

    @Override
    public String MD5(String res, String key) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        return keyGeneratorMac(res, HmacMD5, key);
    }

    @Override
    public String SHA1(String res) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return messageDigest(res, SHA1);
    }

    @Override
    public String SHA1(String res, String key) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        return keyGeneratorMac(res, HmacSHA1, key);
    }

    /**
     */
    @Override
    public String DESencode(String res, String key) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        return keyGeneratorES(res, DES, key, 0, true);
    }

    /**
     */
    @Override
    public String DESdecode(String res, String key) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        return keyGeneratorES(res, DES, key, 128, false);
    }

    /**
     */
    @Override
    public String AESencode(String res, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return au.getInstance(key).encrypt(res);
    }

    /**
     */
    @Override
    public String AESdecode(String res, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return au.getInstance(key).decrypt(res);
    }

    /**
     */
    @Override
    public String SM4encode(String res, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        return sm4.getInstance(key).encrypt(res);
    }

    /**
     */
    @Override
    public String SM4decode(String res, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        return sm4.getInstance(key).decrypt(res);
    }

    /**
     */
    @Override
    public String XORencode(String res, String key) {
        byte[] bs = res.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return parseByte2HexStr(bs);
    }

    /**
     */
    @Override
    public String XORdecode(String res, String key) {
        byte[] bs = parseHexStr2Byte(res);
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return new String(bs);
    }

    /**
     */
    @Override
    public int XOR(int res, String key) {
        return res ^ key.hashCode();
    }

    /**
     */
    @Override
    public String Base64Encode(String res) {
        return Base64.encode(res.getBytes());
    }

    /**
     */
    @Override
    public String Base64Decode(String res) {
        return new String(Base64.decode(res));
    }

    /**
     * Purpose:生成随机数字和字母
     *
     * @param length :返回字符串的长度
     * @return String
     * @author Hermanwang
     */
    @Override
    public String getStringRandom(int length) throws UnsupportedEncodingException {
        String val = "";
        Random random = new Random();
        // length为几位密码
        for (int i = 0; i < length; i++) {
            // 判断生成数字还是字母(字母有大小写区别)
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    @Override
    public String MD5_ex(String res) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return EncryptType.MD5 + "~" + messageDigest(res, MD5);
    }

    @Override
    public String AESencode_ex(String res) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return EncryptType.AES + "~" + au.getInstance(staticKey).encrypt(res);
    }

    @Override
    public String AESdecode_ex(String res) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return au.getInstance(staticKey).decrypt(res.substring(EncryptType.AES.length() + 1));
    }

    @Override
    public String MD5(String res, int pos) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (pos == 0) {
            return MD5(res);
        }
        if (pos < res.length() && pos > 0) {
            String subend = res.substring(pos);
            String subbeg = res.substring(0, pos);
            return subbeg + MD5(subend);
        }
        return res;
    }

    @Override
    public String MD5(String res, String key, int pos) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        if (pos == 0) {
            return MD5(res, key);
        }
        if (pos < res.length()) {
            String subend = res.substring(pos);
            String subbeg = res.substring(0, pos);
            return subbeg + MD5(subend, key);
        }
        return res;
    }

    @Override
    public String SHA1(String res, int pos) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public String SHA1(String res, String key, int pos) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        return null;
    }

    @Override
    public String DESencode(String res, String key, int pos) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        return null;
    }

    @Override
    public String DESdecode(String res, String key, int pos) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        return null;
    }

    @Override
    public String AESencode(String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        if (pos == 0) {
            return AESencode(res, key);
        }
        if (pos < res.length()) {
            String subend = res.substring(pos);
            String subbeg = res.substring(0, pos);
            return subbeg + AESencode(subend, key);
        }
        return res;
    }

    @Override
    public String AESdecode(String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        if (pos == 0) {
            return AESdecode(res, key);
        }
        if (pos < res.length()) {
            String subend = res.substring(pos);
            String subbeg = res.substring(0, pos);
            return subbeg + AESdecode(subend, key);
        }
        return res;
    }

    @Override
    public String SM4encode(String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (pos == 0) {
            return SM4encode(res, key);
        }
        if (pos < res.length()) {
            String subend = res.substring(pos);
            String subbeg = res.substring(0, pos);
            return subbeg + SM4encode(subend, key);
        }
        return res;
    }

    @Override
    public String SM4decode(String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (pos == 0) {
            return SM4decode(res, key);
        }
        if (pos < res.length()) {
            String subend = res.substring(pos);
            String subbeg = res.substring(0, pos);
            return subbeg + SM4decode(subend, key);
        }
        return res;
    }

    @Override
    public String crypeEncode(String type, String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (type == null || type.length() == 0) {
            return res;
        }
        if (EncryptType.DES.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return DESencode(res, key);
        } else if (EncryptType.AES.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return AESencode(res, key);
        } else if (EncryptType.SM4.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return SM4encode(res, key, pos);
        } else if (EncryptType.XOR.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return XORencode(res, key);
        } else if (EncryptType.BASE64.compareToIgnoreCase(type) == 0) {
            return Base64Encode(res);
        } else if (EncryptType.AES_EX.compareToIgnoreCase(type) == 0) {
            return AESencode_ex(res);
        } else if (EncryptType.DES_POS.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return DESencode(res, key, pos);
        } else if (EncryptType.AES_POS.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return AESencode(res, key, pos);
        } else if (EncryptType.MD5.compareToIgnoreCase(type) == 0) {
            return MD5(res);
        } else if (EncryptType.MD5_KEY.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return MD5(res, key);
        } else if (EncryptType.MD5_EX.compareToIgnoreCase(type) == 0) {
            return MD5_ex(res);
        } else if (EncryptType.MD5_POS.compareToIgnoreCase(type) == 0) {
            return MD5(res, pos);
        } else if (EncryptType.MD5_KEY_POS.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return MD5(res, key, pos);
        } else if (EncryptType.SHA1_KEY.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return SHA1(res, key);
        } else if (EncryptType.SHA1_POS.compareToIgnoreCase(type) == 0) {
            return SHA1(res, pos);
        } else if (EncryptType.SHA1_KEY_POS.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return SHA1(res, key, pos);
        }

        return res;
    }

    @Override
    public String crypeDecode(String type, String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (type == null || type.length() == 0) {
            return res;
        }
        if (EncryptType.DES.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return DESdecode(res, key);
        } else if (EncryptType.AES.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return AESdecode(res, key);
        } else if (EncryptType.SM4.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return SM4decode(res, key);
        } else if (EncryptType.XOR.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return XORdecode(res, key);
        } else if (EncryptType.BASE64.compareToIgnoreCase(type) == 0) {
            return Base64Decode(res);
        } else if (EncryptType.AES_EX.compareToIgnoreCase(type) == 0) {
            return AESdecode_ex(res);
        } else if (EncryptType.DES_POS.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return DESdecode(res, key, pos);
        } else if (EncryptType.AES_POS.compareToIgnoreCase(type) == 0 && key != null && key.length() > 0) {
            return AESdecode(res, key, pos);
        }
        return res;
    }


    @Override
    public String crypeEncode(String type, String res) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (type == null || type.length() == 0 || res == null || res.length() == 0) {
            return res;
        }
        if (EncryptType.AES.compareToIgnoreCase(type) == 0) {
            return AESencode_ex(res);
        } if (EncryptType.SM4.compareToIgnoreCase(type) == 0) {
            return SM4encode(res, EncryptUtil.SM4 + "~" + staticKey);
        } else if (EncryptType.MD5.compareToIgnoreCase(type) == 0) {
            return MD5_ex(res);
        }

        return res;
    }

    @Override
    public String crypeDecode(String type, String res) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (type == null || type.length() == 0 || res == null || res.length() == 0) {
            return res;
        }
        if (EncryptType.AES.compareToIgnoreCase(type) == 0) {
            if (res.startsWith(EncryptUtil.AES + "~")) {
                return AESdecode_ex(res);
            }
            if (res.startsWith(EncryptUtil.SM4 + "~")) {
                return SM4decode(res, EncryptUtil.SM4 + "~" + staticKey);
            }
        }
        return res;
    }

    /**
     *  自动生成密钥
     */
    public byte[] generateKey()  {
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        return key.getBytes();
    }

    public byte[] generateKey(int keySize, String type) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            KeyGenerator kg = KeyGenerator.getInstance(type, BouncyCastleProvider.PROVIDER_NAME);
            kg.init(keySize, new SecureRandom());
            return kg.generateKey().getEncoded();
        } catch (Exception e) {
            $.warn(e);
            return generateKey();
        }
    }

}
