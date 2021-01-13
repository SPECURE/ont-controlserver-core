package com.specure.core.utils;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class HeaderExtrudeUtil {

    private final String HEADER_NGINX_X_REAL_IP = "x-real-ip";
    private final String HEADER_NGINX_X_FORWARDED_FOR = "x-forwarded-for";
    private final String USER_AGENT = "User-Agent";
    private final String USER_AGENT_LOW_CASE = "user-agent";
    private final String IP_UNKNOWN = "ip unknown";
    private final String UNKNOWN_DEVICE = "unknown device";

    public String getIpFromNgNixHeader(Map<String, String> headers) {
        if (headers.containsKey(HEADER_NGINX_X_REAL_IP)) {
            return headers.get(HEADER_NGINX_X_REAL_IP);
        }
        if (headers.containsKey(HEADER_NGINX_X_FORWARDED_FOR)){
            return headers.get(HEADER_NGINX_X_FORWARDED_FOR);
        }
        return IP_UNKNOWN;
    }
    public String getUserAgent(Map<String, String> headers) {
        if (headers.containsKey(USER_AGENT)) {
            return headers.get(USER_AGENT);
        }
        if (headers.containsKey(USER_AGENT_LOW_CASE)) {
            return headers.get(USER_AGENT_LOW_CASE);
        }
        return UNKNOWN_DEVICE;
    }
}
