package com.portfolio.portfolio_v3.util;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * ✅ 공통 응답 유틸리티 클래스
 * - 컨트롤러에서 일관된 응답을 제공하도록 함
 */
public class ResponseUtil {

    public static ResponseEntity<?> success(String message) {
        return ResponseEntity.ok(Map.of("message", message));
    }

    public static ResponseEntity<?> successWithData(Object data) {
        return ResponseEntity.ok(Map.of("data", data));
    }

    public static ResponseEntity<?> error(String errorMessage) {
        return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
    }

    public static ResponseEntity<?> handle(ServiceCall serviceCall) {
        try {
            return successWithData(serviceCall.execute());
        } catch (RuntimeException e) {
            return error(e.getMessage());
        }
    }

    @FunctionalInterface
    public interface ServiceCall {
        Object execute();
    }
}
