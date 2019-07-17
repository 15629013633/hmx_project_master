package com.hmx.system.entity;

/**
 * Created by songjinbao on 2019/7/17.
 */
public class LiveRequest {
    private String Version;
    private String AccessKeyId;
    private String Signature;
    private String SignatureMethod;
    private String Timestamp;
    private String SignatureVersion;
    private String SignatureNonce;
    private String ResourceOwnerAccount;
    private String Format;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getAccessKeyId() {
        return AccessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        AccessKeyId = accessKeyId;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getSignatureMethod() {
        return SignatureMethod;
    }

    public void setSignatureMethod(String signatureMethod) {
        SignatureMethod = signatureMethod;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getSignatureVersion() {
        return SignatureVersion;
    }

    public void setSignatureVersion(String signatureVersion) {
        SignatureVersion = signatureVersion;
    }

    public String getSignatureNonce() {
        return SignatureNonce;
    }

    public void setSignatureNonce(String signatureNonce) {
        SignatureNonce = signatureNonce;
    }

    public String getResourceOwnerAccount() {
        return ResourceOwnerAccount;
    }

    public void setResourceOwnerAccount(String resourceOwnerAccount) {
        ResourceOwnerAccount = resourceOwnerAccount;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
