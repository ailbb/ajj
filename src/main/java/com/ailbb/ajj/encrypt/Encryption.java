package com.ailbb.ajj.encrypt;

import com.ailbb.ajj.$;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by Wz on 8/18/2020.
 */
public abstract class Encryption {

    public abstract String encrypt(String strIn) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException;

    public abstract String decrypt(String strIn) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException;

    public String encrypt(String rule, String row) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        EncryptionRule er = EncryptUtil.getRuleByEncode(rule, row.length()); // 获取加密规则对象
        if(er.isBad()) return row; // 如果规则异常，当异常数据处理，直接返回值

        int prefixSize = er.getPrefixs().length;
        String[] sp = new String[prefixSize];
        String sub;

        for(int i=0; i<prefixSize; i++) {
            sub = er.subEncodeData(row, i); // 截取关键数据
            sp[i] = er.getPrefixs()[i].equals(EncryptUtil.encryptionPrefix) ? encrypt(sub) : sub;
        }

        return $.join(sp, "");
    }

    public String decrypt(String rule, String row) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        EncryptionRule er = EncryptUtil.getRuleByDecode(rule, row.length()); // 获取加密规则对象
        if(er.isBad()) return row; // 如果规则异常，当异常数据处理，直接返回值

        int prefixSize = er.getPrefixs().length;
        String[] sp = new String[prefixSize];
        String sub;

        for(int i=0; i<prefixSize; i++) {
            sub = er.subDecodeData(row, i); // 截取关键数据
            sp[i] = er.getPrefixs()[i].equals(EncryptUtil.encryptionPrefix) ? decrypt(sub) : sub;
        }

        return $.join(sp, "");
    }

    public List<String> encrypt(String rule, List<String> strIn) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        List<String> result = new ArrayList<>(); // 数据结果集
        for(String s : strIn) result.add(encrypt(rule, s));
        return result;
    }

    public List<String> decrypt(String rule, List<String> strIn) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        List<String> result = new ArrayList<>(); // 数据结果集
        for(String s : strIn) result.add(decrypt(rule, s));
        return result;
    }

}
