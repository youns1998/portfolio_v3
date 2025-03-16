package com.portfolio.portfolio_v3.util;

import com.portfolio.portfolio_v3.dto.BoardPostRequest;
import com.portfolio.portfolio_v3.dto.VoteRequest;
import java.util.regex.Pattern;

public class ValidationUtil {
    
    // Regex patterns
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$");
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
    private static final Pattern IPV4_REGEX = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

    // Basic validations
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Credential validations
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_REGEX.matcher(password).matches();
    }

    // IP address handling
    public static boolean isValidIp(String ip) {
        return ip != null && IPV4_REGEX.matcher(ip).matches();
    }

    public static String getSafeIp(String ip) {
        return (ip == null || ip.isBlank()) ? "0.0.0.0" : ip;
    }

    public static String maskIp(String ip) {
        if (ip == null || ip.isEmpty()) return "Unknown";
        String[] octets = ip.split("\\.");
        return octets.length == 4 ? 
            octets[0] + "." + octets[1] + ".*.*" : ip;
    }

    // User ID validation
    public static boolean isValidUserId(Long userId) {
        return userId != null && userId > 0L;
    }

    // Request object validations
    public static void validatePostRequest(BoardPostRequest request) {
        if (request == null || !request.isValidRequest()) {
            throw new IllegalArgumentException("Invalid post request");
        }
    }

    public static boolean isValidVoteRequest(VoteRequest request) {
        return request != null && 
              (isValidUserId(request.getUserId()) ^ 
               isValidIp(request.getIpAddress()));
    }
}