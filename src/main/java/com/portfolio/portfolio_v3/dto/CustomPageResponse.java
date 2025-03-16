package com.portfolio.portfolio_v3.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
public class CustomPageResponse<T> {

    private final List<T> content;
    private final int totalPages;
    private final long totalElements;

    public CustomPageResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }

    public CustomPageResponse(List<T> content, int totalPages, long totalElements) {
        this.content = content;
        this.totalPages = Math.max(totalPages, 1);
        this.totalElements = Math.max(totalElements, 0);
    }
}
