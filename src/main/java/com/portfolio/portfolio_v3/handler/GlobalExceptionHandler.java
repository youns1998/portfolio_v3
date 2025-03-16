package com.portfolio.portfolio_v3.handler;

import com.portfolio.portfolio_v3.exception.CustomException;
import com.portfolio.portfolio_v3.util.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ DTO 유효성 검사 실패 처리 (메서드 수정)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseUtil.ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        // ✅ 수정된 failWithErrors() 사용
        return ResponseUtil.failWithErrors("유효성 검사 실패", errors, HttpStatus.BAD_REQUEST);
    }

    // ✅ 커스텀 예외 처리 (CustomException)
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseUtil.ApiResponse<Void>> handleCustomException(CustomException ex) {
        return ResponseUtil.fail(ex.getMessage(), ex.getStatus());
    }

    // ✅ 모든 기타 예외 처리 (최적화)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseUtil.ApiResponse<Void>> handleAllExceptions(Exception ex) {
        return ResponseUtil.fail("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
