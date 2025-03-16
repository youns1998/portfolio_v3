package com.portfolio.portfolio_v3.util;

import com.portfolio.portfolio_v3.dto.BoardPostRequest;
import com.portfolio.portfolio_v3.dto.VoteRequest;
import java.util.regex.Pattern;

public class ValidationUtil {

    // ✅ 정규식 패턴 정의
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$");
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
    private static final Pattern IPV4_REGEX = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

    // ✅ 기본적인 유효성 검사
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // ✅ 이메일 형식 검사
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_REGEX.matcher(email).matches();
    }

    // ✅ 비밀번호 강도 검사
    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_REGEX.matcher(password).matches();
    }

    // ✅ IP 주소 유효성 검사
    public static boolean isValidIp(String ip) {
        return ip != null && IPV4_REGEX.matcher(ip).matches();
    }

    public static String getSafeIp(String ip) {
        return (ip == null || ip.isBlank()) ? "0.0.0.0" : ip;
    }

    public static String maskIp(String ip) {
        if (ip == null || ip.isEmpty()) return "Unknown";
        String[] octets = ip.split("\\.");
        return octets.length == 4 ? octets[0] + "." + octets[1] + ".*.*" : ip;
    }

    // ✅ 사용자 ID 유효성 검사
    public static boolean isValidUserId(Long userId) {
        return userId != null && userId > 0L;
    }

    // ✅ 게시글 요청 유효성 검사 (BoardPostRequest)
    public static void validateBoardPostRequest(BoardPostRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("요청 데이터가 없습니다.");
        }
        if (!isNotEmpty(request.getTitle())) {
            throw new IllegalArgumentException("제목은 필수 입력 항목입니다.");
        }
        if (!isNotEmpty(request.getContent())) {
            throw new IllegalArgumentException("내용은 필수 입력 항목입니다.");
        }
        if (!isNotEmpty(request.getAuthor())) {
            throw new IllegalArgumentException("작성자 이름은 필수 입력 항목입니다.");
        }
        if (!isNotEmpty(request.getBoardId())) {
            throw new IllegalArgumentException("게시판 ID는 필수 입력 항목입니다.");
        }
        if (!isValidIp(request.getIpAddress())) {
            throw new IllegalArgumentException("올바른 IP 주소가 아닙니다.");
        }
        if (request.isMember()) {
            if (!isValidUserId(request.getUserId())) {
                throw new IllegalArgumentException("회원 게시글은 유효한 사용자 ID가 필요합니다.");
            }
        } else {
            if (!isNotEmpty(request.getPassword())) {
                throw new IllegalArgumentException("비회원 게시글은 비밀번호가 필요합니다.");
            }
        }
    }

    // ✅ 추천 요청 검증
    public static boolean isValidVoteRequest(VoteRequest request) {
        return request != null && 
              (isValidUserId(request.getUserId()) ^ isValidIp(request.getIpAddress()));
    }
}
