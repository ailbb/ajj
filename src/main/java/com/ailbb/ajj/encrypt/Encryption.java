package com.ailbb.ajj.encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by Wz on 8/18/2020.
 */
public interface Encryption {
    String encrypt(String strIn) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException;

    String decrypt( String strIn) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException;
}
