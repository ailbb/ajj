package com.ailbb.ajj.encrypt.util;

import com.ailbb.ajj.encrypt.Encryption;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import java.util.HashMap;
import java.util.Map;

public class StringEncryptorUtil extends Encryption {
    public static final String STRINGENCRYPTOR = "StringEncryptor";
    private String password = "STRINGENCRYPTORAESKEY";
    private StringEncryptor encryptor;

    private static Map<String, StringEncryptorUtil> cache = new HashMap<>();

    public StringEncryptorUtil() {}

    public StringEncryptorUtil(String password) {
        this.password = password;
    }

    public StringEncryptorUtil(String password, StringEncryptor encryptor) {
        this.password = password;
        this.encryptor = encryptor;
        cache.put(password, this);
    }

    public static StringEncryptorUtil getInstance(String password) {
        StringEncryptorUtil stringEncryptorUtilCache = cache.get(password);
        // 如果由缓存，直接返回
        if(null != stringEncryptorUtilCache) return stringEncryptorUtilCache;

        // 生成对象后重新赋值后再测试
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        return new StringEncryptorUtil(password, encryptor);
    }

    public synchronized String decrypt(String str){
        return getInstance(password).encryptor.decrypt(str);
    }

    public synchronized String encrypt(String str){
        return getInstance(password).encryptor.encrypt(str);
    }

    public synchronized String decrypt(String password, String str){
        return getInstance(password).encryptor.decrypt(str);
    }

    public synchronized String encrypt(String password, String str){
        return getInstance(password).encryptor.encrypt(str);
    }

}
