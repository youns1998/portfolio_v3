package com.portfolio.portfolio_v3.util;

import com.portfolio.portfolio_v3.dto.BoardPostRequest;
import com.portfolio.portfolio_v3.dto.VoteRequest;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
    private static final Pattern IP_PATTERN = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

    /**
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * ✅ 비밀번호 유효성 검사 (영문 + 숫자 + 특수문자 최소 8자)
     */
    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * ✅ 비밀번호가 유효하지 않은지 검사 (null이거나 형식이 틀린 경우)
     */
    public static boolean isInvalidPassword(String password) {
        return password == null || password.isBlank() || !isValidPassword(password);
    }

    /**
     * ✅ 문자열이 비어있지 않은지 검사
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * ✅ IP 주소 유효성 검사
     */
    public static boolean isValidIpAddress(String ipAddress) {
        return ipAddress != null && IP_PATTERN.matcher(ipAddress).matches();
    }

    /**
     * ✅ userId 유효성 검사 (0 이상인지 체크)
     */
    public static boolean isValidUserId(Long userId) {
        return userId != null && userId > 0;
    }

    /**
     * ✅ 게시글 추천 요청 검증 (userId 또는 ipAddress 둘 중 하나는 필수)
     */
    public static boolean isValidVoteRequest(VoteRequest request) {
        return isValidUserId(request.getUserId()) || isValidIpAddress(request.getIpAddress());
    }

    /**
     * ✅ 추천 요청이 유효하지 않은지 검사
     */
    public static boolean isInvalidVoteRequest(VoteRequest request) {
        return !isValidVoteRequest(request);
    }

    public static void validateBoardPostRequest(BoardPostRequest request) {
        if (!request.isValidRequest()) {
            throw new IllegalArgumentException("게시글 요청이 유효하지 않습니다.");
        }
    }

    public static String getValidIp(String ip) {
        return (ip == null || ip.trim().isEmpty()) ? "0.0.0.0" : ip;
    }

    public static String maskIp(String ip) {
        if (ip == null || ip.isEmpty()) {
            return "Unknown";
        }
        String[] parts = ip.split("\\.");
        return parts.length >= 2 ? parts[0] + "." + parts[1] + ".*.*" : ip;
    }

    public static boolean isValidIp(String ip) {
        return ip != null && ip.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");
    }




}
