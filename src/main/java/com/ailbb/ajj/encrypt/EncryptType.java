package com.ailbb.ajj.encrypt;

import java.util.ArrayList;

/*
 * 加解密类型定义
 * <p> <br>
 *
 * @author - tiger
 * @version - 1.0
 * @date - Created in 2019-04-04 9:34
 * @modified -
 */
public class EncryptType {
    //没有解码方法
    public static final  String MD5="MD5";//MD5(String res)
    public static final  String MD5_KEY="MD5_KEY";//MD5(String res,String key)  带key的md5加密
    public static final  String MD5_EX="MD5_EX";//MD5_ex(String res) 默认key值加密
    public static final  String MD5_POS="MD5_POS";//MD5(String res,int pos) 带指定保留前面多少位原值的加密
    public static final  String MD5_KEY_POS="MD5_KEY_POS";//MD5(String res,String key,int pos)
    public static final  String SHA1="SHA1";//SHA1(String res)
    public static final  String SHA1_KEY="SHA1_KEY";//SHA1(String res,String key)
    public static final  String SHA1_POS="SHA1_POS";//SHA1(String res,int pos)
    public static final  String SHA1_KEY_POS="SHA1_KEY_POS";//SHA1(String res,String key,int pos)

    //有解码方法
    public static final  String DES="DES";//DESencode(String res,String key)
    public static final  String AES="AES";//AESencode(String res,String key)
    public static final  String XOR="XOR";//XORencode(String res,String key)
    public static final  String BASE64="BASE64";//Base64Encode(String res);
    public static final  String SM4="SM4";//SM4encode(String res,String key,int pos)
    public static final  String AES_EX="AES_EX";//AESencode_ex(String res)
    public static final  String DES_POS="DES_POS";//DESencode(String res,String key,int pos)
    public static final  String AES_POS="AES_POS";//AESencode(String res,String key,int pos)
    public static final  String SM4_CBC="SM4_CBC";//SM4encode(String res,String key,int pos)


    //获取没有有解码方法的加密类型
    public ArrayList<String> getUnReversibilityType() {
        ArrayList<String> arr = new ArrayList<String>();
        arr.add(MD5);
        arr.add(MD5_KEY);
        arr.add(MD5_EX);
        arr.add(MD5_POS);
        arr.add(MD5_KEY_POS);
        arr.add(SHA1);
        arr.add(SHA1_KEY);
        arr.add(SHA1_POS);
        arr.add(SHA1_KEY_POS);
        return arr;
    }
    //获取有解码方法的加密类型
    public ArrayList<String> getReversibilityType() {
        ArrayList<String> arr = new ArrayList<String>();
        arr.add(DES);
        arr.add(AES);
        arr.add(XOR);
        arr.add(BASE64);
        arr.add(AES_EX);
        arr.add(DES_POS);
        arr.add(AES_POS);
        arr.add(SM4_CBC);
        return arr;
    }
}
