package com.specture.core.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Base64;

@UtilityClass
public class BasicAuthHeaderUtil {
    private final String basic =  "Basic ";
    public String getHeader(String userName, String password) {
        String plainCredentials = userName + ":" + password;
        byte[] plainCredentialsBytes = plainCredentials.getBytes();
        byte[] base64CredentialsBytes = Base64.encodeBase64(plainCredentialsBytes);
        String base64Credentials = new String(base64CredentialsBytes);
        return basic + base64Credentials;
    }
}
