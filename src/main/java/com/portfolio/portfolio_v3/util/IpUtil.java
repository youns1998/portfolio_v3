package com.portfolio.portfolio_v3.util;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtil {

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String maskIp(String ip) {
        if (ip == null || ip.isEmpty()) return "Unknown";
        String[] parts = ip.split("\\.");
        return parts.length >= 2 ? parts[0] + "." + parts[1] + ".*.*" : ip;
    }
}
