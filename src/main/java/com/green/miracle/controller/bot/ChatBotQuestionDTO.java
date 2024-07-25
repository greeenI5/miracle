package com.green.miracle.controller.bot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//question을 받기위한 DTO
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatBotQuestionDTO {
	private long key;
	private String keyword;
	private String content;

}
