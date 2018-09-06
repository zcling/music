package com.qmx.model.pay;

import com.github.binarywang.wxpay.exception.WxPayException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * Created by zcl on 2018/8/19.
 */
public class WxPayConfig {
    private int httpConnectionTimeout = 5000;
    private int httpTimeout = 10000;
    private String appId;
    private String subAppId;
    private String mchId;
    private String mchKey;
    private String subMchId;
    private String notifyUrl;
    private String tradeType;
    private SSLContext sslContext;
    private String keyPath;
    private boolean useSandboxEnv = false;
    private String httpProxyHost;
    private Integer httpProxyPort;
    private String httpProxyUsername;
    private String httpProxyPassword;

    public WxPayConfig() {
    }

    public String getKeyPath() {
        return this.keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getMchId() {
        return this.mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return this.mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSubAppId() {
        return this.subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getSubMchId() {
        return this.subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getNotifyUrl() {
        return this.notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public SSLContext getSslContext() {
        return this.sslContext;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public boolean useSandbox() {
        return this.useSandboxEnv;
    }

    public void setUseSandboxEnv(boolean useSandboxEnv) {
        this.useSandboxEnv = useSandboxEnv;
    }

    public SSLContext initSSLContext() throws WxPayException {
        if(StringUtils.isBlank(this.getMchId())) {
            throw new WxPayException("请确保商户号mchId已设置");
        } else if(StringUtils.isBlank(this.getKeyPath())) {
            throw new WxPayException("请确保证书文件地址keyPath已配置");
        } else {
            String prefix = "classpath:";
            String fileHasProblemMsg = "证书文件【" + this.getKeyPath() + "】有问题，请核实！";
            String fileNotFoundMsg = "证书文件【" + this.getKeyPath() + "】不存在，请核实！";
            Object inputStream;
            if(this.getKeyPath().startsWith("classpath:")) {
                String path = StringUtils.removeFirst(this.getKeyPath(), "classpath:");
                if(!path.startsWith("/")) {
                    path = "/" + path;
                }

                inputStream = com.github.binarywang.wxpay.config.WxPayConfig.class.getResourceAsStream(path);
                if(inputStream == null) {
                    throw new WxPayException(fileNotFoundMsg);
                }
            } else {
                try {
                    File file = new File(this.getKeyPath());
                    if(!file.exists()) {
                        throw new WxPayException(fileNotFoundMsg);
                    }

                    inputStream = new FileInputStream(file);
                } catch (IOException var14) {
                    throw new WxPayException(fileHasProblemMsg, var14);
                }
            }

            SSLContext var7;
            try {
                KeyStore keystore = KeyStore.getInstance("PKCS12");
                char[] partnerId2charArray = this.getMchId().toCharArray();
                keystore.load((InputStream)inputStream, partnerId2charArray);
                this.sslContext = SSLContexts.custom().loadKeyMaterial(keystore, partnerId2charArray).build();
                var7 = this.sslContext;
            } catch (Exception var12) {
                throw new WxPayException(fileHasProblemMsg, var12);
            } finally {
                IOUtils.closeQuietly((InputStream)inputStream);
            }

            return var7;
        }
    }

    public int getHttpConnectionTimeout() {
        return this.httpConnectionTimeout;
    }

    public void setHttpConnectionTimeout(int httpConnectionTimeout) {
        this.httpConnectionTimeout = httpConnectionTimeout;
    }

    public int getHttpTimeout() {
        return this.httpTimeout;
    }

    public void setHttpTimeout(int httpTimeout) {
        this.httpTimeout = httpTimeout;
    }

    public String getHttpProxyHost() {
        return this.httpProxyHost;
    }

    public void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    public Integer getHttpProxyPort() {
        return this.httpProxyPort;
    }

    public void setHttpProxyPort(Integer httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    public String getHttpProxyUsername() {
        return this.httpProxyUsername;
    }

    public void setHttpProxyUsername(String httpProxyUsername) {
        this.httpProxyUsername = httpProxyUsername;
    }

    public String getHttpProxyPassword() {
        return this.httpProxyPassword;
    }

    public void setHttpProxyPassword(String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
    }
}
