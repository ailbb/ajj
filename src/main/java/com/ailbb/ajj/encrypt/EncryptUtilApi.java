package com.ailbb.ajj.encrypt;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public interface EncryptUtilApi {

    //------MD5-------//
    String MD5(String res) throws UnsupportedEncodingException, NoSuchAlgorithmException;//要加密的字符串
    String MD5(String res, String key) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException;//要加密的字符串和密码

    //------SHA1-------//
    String SHA1(String res) throws UnsupportedEncodingException, NoSuchAlgorithmException;//要加密的字符串
    String SHA1(String res, String key) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException;//要加密的字符串和密码

    //=====上面的没有解码方法====================================

    //------DES-------//
    String DESencode(String res, String key) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException;//加密：要加密的字符串和密码
    String DESdecode(String res, String key) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException;//解密：加密后的字符串和密码

    //------AES-------//
    String AESencode(String res, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException;//加密：要加密的字符串和密码
    String AESdecode(String res, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException;//解密：加密后的字符串和密码

    //------SM4-------//
    String SM4encode(String res, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException;//加密：要加密的字符串和密码
    String SM4decode(String res, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException;//解密：加密后的字符串和密码

    //------异或加密-----//
    String XORencode(String res, String key);//加密：要加密的字符串和密码
    String XORdecode(String res, String key);//解密：加密后的字符串和密码
    int XOR(int res, String key);//执行一次：加密：要加密的字符串和密码  ； 再执行一次：解密：加密后的字符串和密码

    //------Base64-----//
    String Base64Encode(String res);//加密：要加密的字符串
    String Base64Decode(String res);//解密：加密后的字符串
    /*
     * Purpose:生成随机数字和字母
     * @author Hermanwang
     * @param length :返回字符串的长度
     */
    String getStringRandom(int length) throws UnsupportedEncodingException;

    // 固定方式加解密
    String MD5_ex(String res) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    String AESencode_ex(String res) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException;//加密：要加密的字符串和密码
    String AESdecode_ex(String res) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException;//解密：加密后的字符串和密码
    // 带指定加密位置
    //------MD5-------//
    String MD5(String res, int pos) throws UnsupportedEncodingException, NoSuchAlgorithmException;//要加密的字符串
    String MD5(String res, String key, int pos) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException;//要加密的字符串和密码

    //------SHA1-------//
    String SHA1(String res, int pos) throws UnsupportedEncodingException, NoSuchAlgorithmException;//要加密的字符串
    String SHA1(String res, String key, int pos) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException;//要加密的字符串和密码

    //=====上面的没有解码方法====================================

    //------DES-------//
    String DESencode(String res, String key, int pos) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException;//加密：要加密的字符串和密码
    String DESdecode(String res, String key, int pos) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException;//解密：加密后的字符串和密码

    //------AES-------//
    String AESencode(String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException;//加密：要加密的字符串和密码
    String AESdecode(String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException;//解密：加密后的字符串和密码

    //------SM4-------//
    String SM4encode(String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException;//加密：要加密的字符串和密码
    String SM4decode(String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException;//解密：加密后的字符串和密码

    //加密
    String crypeEncode(String type, String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException;//解密：加密后的字符串和密码
    //解密
    String crypeDecode(String type, String res, String key, int pos) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException;//解密：加密后的字符串和密码

    //加密
    String crypeEncode(String type, String res) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException;//解密：加密后的字符串和密码
    //解密
    String crypeDecode(String type, String res) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException;//解密：加密后的字符串和密码

}
