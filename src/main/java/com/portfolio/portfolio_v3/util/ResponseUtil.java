package com.portfolio.portfolio_v3.util;

import org.springframework.http.ResponseEntity;

import com.portfolio.portfolio_v3.dto.CustomPageResponse;

import java.time.LocalDateTime;

public class ResponseUtil {

    // 성공 응답 (데이터 없음)
    public static ResponseEntity<ApiResponse<Void>> success(String message) {
        return ResponseEntity.ok(
            new ApiResponse<>("success", message, null, LocalDateTime.now())
        );
    }

    // 성공 응답 (데이터 포함)
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(
            new ApiResponse<>("success", message, data, LocalDateTime.now())
        );
    }

    // 페이징 응답
    public static <T> ResponseEntity<ApiResponse<CustomPageResponse<T>>> pagedSuccess(
        CustomPageResponse<T> pageResponse,
        String message
    ) {
        return ResponseEntity.ok(
            new ApiResponse<>("success", message, pageResponse, LocalDateTime.now())
        );
    }

    // 예외 핸들러 통합을 위한 래핑
    public static <T> ResponseEntity<ApiResponse<T>> handle(ServiceInvoker<T> invoker) {
        try {
            return success(invoker.execute(), "요청이 성공적으로 처리되었습니다");
        } catch (RuntimeException e) {
            throw e; // 글로벌 예외 핸들러에서 처리
        }
    }

    // 표준 응답 DTO
    public record ApiResponse<T>(
        String status,
        String message,
        T data,
        LocalDateTime timestamp
    ) {}

    @FunctionalInterface
    public interface ServiceInvoker<T> {
        T execute();
    }
}