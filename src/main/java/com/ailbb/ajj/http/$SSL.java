package com.ailbb.ajj.http;

import com.ailbb.ajj.$;

import javax.net.ssl.*;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class $SSL {
    SSLContext sslContext;

    public void setTrustStore(String truststorePath, String truststorePassword) throws Exception {
        // 创建信任库并加载证书
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream fis = $.file.getResourceAsStream(truststorePath.replace("classpath:",""));
        trustStore.load(fis, truststorePassword.toCharArray());

        // 创建信任管理器工厂
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        // 创建SSL上下文
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        // 设置默认的SSL上下文
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

    }

    public void disableCertificateValidation() throws Exception {
        // 创建信任所有证书的信任管理器
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {}
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        // 创建SSL上下文
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        // 设置默认的SSL上下文
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }
}
