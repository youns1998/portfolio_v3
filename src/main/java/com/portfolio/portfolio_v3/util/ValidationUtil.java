package com.portfolio.portfolio_v3.util;
import com.portfolio.portfolio_v3.exception.CustomException;
import com.portfolio.portfolio_v3.exception.ErrorCode;
import com.portfolio.portfolio_v3.dto.BoardPostRequest;
import com.portfolio.portfolio_v3.dto.VoteRequest;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$");
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
    private static final Pattern IPV4_REGEX = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

    private ValidationUtil() {}

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_REGEX.matcher(password).matches();
    }

    public static boolean isValidIp(String ip) {
        return ip != null && IPV4_REGEX.matcher(ip).matches();
    }

    public static boolean isValidUserId(Long userId) {
        return userId != null && userId > 0L;
    }

    

    public static void validateBoardPostRequest(BoardPostRequest request) {
        if (request == null) {
            throw new CustomException(ErrorCode.VALIDATION_FAILED);
        }
        if (!isNotEmpty(request.getTitle())) {
            throw new CustomException(ErrorCode.VALIDATION_FAILED);
        }
    }


    public static boolean isValidVoteRequest(VoteRequest request) {
        return request != null && (isValidUserId(request.getUserId()) ^ isValidIp(request.getIpAddress()));
    }
}
