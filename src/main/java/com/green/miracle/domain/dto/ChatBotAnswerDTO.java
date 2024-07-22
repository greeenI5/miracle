package com.green.miracle.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatBotAnswerDTO {
    private long answerNo; // 질문 번호
    private String keyword; // 키워드
    private String content; // 대답
}
