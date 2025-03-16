package com.portfolio.portfolio_v3.util;

import com.portfolio.portfolio_v3.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Map;

public class ResponseUtil {

    private ResponseUtil() {}

    // ✅ 성공 응답 (데이터 포함)
    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(new ApiResponse<>(true, data, "요청이 성공적으로 처리되었습니다."));
    }

    // ✅ 성공 응답 (데이터 + 커스텀 메시지)
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(new ApiResponse<>(true, data, message));
    }

    // ✅ 실패 응답 (단순 메시지)
    public static <T> ResponseEntity<ApiResponse<T>> fail(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse<>(false, null, message));
    }

    // ✅ 실패 응답 (에러 메시지 Map 포함)
    public static ResponseEntity<ApiResponse<Map<String, String>>> failWithErrors(
            String message, Map<String, String> errors, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse<>(false, errors, message));
    }

    // ✅ 예외 처리 통합 핸들러 (CustomException 지원)
    public static <T> ResponseEntity<ApiResponse<T>> handle(ServiceInvoker<T> invoker) {
        try {
            return success(invoker.execute());
        } catch (CustomException e) {
            return fail(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            return fail("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ API 응답 객체
    public static class ApiResponse<T> {
        private final boolean success;
        private final T data;
        private final String message;
        private final LocalDateTime timestamp = LocalDateTime.now();

        public ApiResponse(boolean success, T data, String message) {
            this.success = success;
            this.data = data;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public T getData() { return data; }
        public String getMessage() { return message; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }

    @FunctionalInterface
    public interface ServiceInvoker<T> {
        T execute();
    }
}
