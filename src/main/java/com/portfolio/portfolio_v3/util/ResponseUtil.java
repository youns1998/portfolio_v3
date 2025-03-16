package com.portfolio.portfolio_v3.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.portfolio.portfolio_v3.dto.CustomPageResponse;
import com.portfolio.portfolio_v3.exception.CustomException;
import java.time.LocalDateTime;
import java.util.Map;

public class ResponseUtil {

    // ✅ 성공 응답 (데이터 포함)
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(new ApiResponse<>("success", message, data, LocalDateTime.now()));
    }

    // ✅ 페이징 응답 (Page 객체 포함)
    public static <T> ResponseEntity<ApiResponse<CustomPageResponse<T>>> pagedSuccess(CustomPageResponse<T> pageResponse, String message) {
        return ResponseEntity.ok(new ApiResponse<>("success", message, pageResponse, LocalDateTime.now()));
    }

    // ✅ 에러 응답 (데이터 없음)
    public static ResponseEntity<ApiResponse<Void>> error(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("error", message, null, LocalDateTime.now()));
    }

    // ✅ 에러 응답 (Map<String, String> 데이터 포함)
    public static ResponseEntity<ApiResponse<Map<String, String>>> error(String message, Map<String, String> errors) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>("error", message, errors, LocalDateTime.now()));
    }

    // ✅ 커스텀 HTTP 상태 코드 지원
 // ✅ 제네릭 추가하여 타입 불일치 문제 해결
    public static <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(new ApiResponse<>("error", message, null, LocalDateTime.now()));
    }


    // ✅ handle() 내부에서 예외 처리 (CustomException 포함)
    public static <T> ResponseEntity<ApiResponse<T>> handle(ServiceInvoker<T> invoker) {
        try {
            return success(invoker.execute(), "요청이 성공적으로 처리되었습니다");
        } catch (CustomException e) { // CustomException 직접 처리
            return ResponseUtil.<T>error(e.getMessage(), e.getStatus());
        } catch (Exception e) { // 기타 모든 예외 처리
            return ResponseUtil.<T>error("서버 내부 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ 표준 응답 DTO
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
