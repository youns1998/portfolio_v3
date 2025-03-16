package com.portfolio.portfolio_v3.handler;

import com.portfolio.portfolio_v3.util.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ DTO 유효성 검사 실패 처리 (유지)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseUtil.error("유효성 검사 실패", errors);
    }

    // ❌ `CustomException` 핸들러 제거 가능 (ResponseUtil에서 이미 처리됨)
    
    // ✅ 기타 모든 예외 처리 (유지)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex) {
        return ResponseUtil.error("서버 내부 오류가 발생했습니다");
    }
}
