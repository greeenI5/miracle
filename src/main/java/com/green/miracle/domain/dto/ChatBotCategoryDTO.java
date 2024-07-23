package com.green.miracle.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatBotCategoryDTO {
    private long cNo; // 카테고리 버튼 번호(pk)
    private String content; // 버튼내용
    private long type; // 타입
}
