package com.portfolio.portfolio_v3.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;
import java.util.Collections;
import java.util.List;

/**
 * ✅ CustomPageResponse (페이지네이션 응답 DTO)
 * - 페이지네이션이 적용된 데이터를 클라이언트에 반환하는 DTO
 * - Spring Data JPA의 `Page<T>`를 감싸서 응답 데이터를 구성
 */
@Getter
public class CustomPageResponse<T> {

    /** ✅ 페이지의 데이터 리스트 */
    private final List<T> content;

    /** ✅ 전체 페이지 수 */
    private final int totalPages;

    /** ✅ 전체 요소(게시글) 수 */
    private final long totalElements;

    /**
     * ✅ 기존 생성자
     * - Spring Data JPA의 `Page<T>` 객체를 받아 변환
     * - `content`가 null일 경우 빈 리스트로 초기화
     * @param page 페이지 객체
     */
    public CustomPageResponse(Page<T> page) {
        if (page == null || page.isEmpty()) {
            this.content = Collections.emptyList();
            this.totalPages = 0;
            this.totalElements = 0;
        } else {
            this.content = page.getContent();
            this.totalPages = page.getTotalPages();
            this.totalElements = page.getTotalElements();
        }
    }

    /**
     * ✅ 새로운 생성자 (직접 데이터 전달)
     * - `List<T>` 데이터를 직접 받아서 페이지 정보를 설정할 수 있도록 추가
     * - `content`가 null일 경우 빈 리스트로 초기화
     * @param content 페이지 데이터 리스트
     * @param totalPages 전체 페이지 수
     * @param totalElements 전체 요소(게시글) 수
     */
    public CustomPageResponse(List<T> content, int totalPages, long totalElements) {
        this.content = content != null ? content : Collections.emptyList();
        this.totalPages = Math.max(totalPages, 1); // ✅ 최소 1페이지 이상 유지
        this.totalElements = Math.max(totalElements, 0);
    }
}
