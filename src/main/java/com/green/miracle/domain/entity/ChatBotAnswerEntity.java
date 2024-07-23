package com.green.miracle.domain.entity;



import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@DynamicUpdate
@Entity
@Table(name = "chatBotAnswer")
public class ChatBotAnswerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long answerNo; //질문번호 (pk)
	
	@Column(columnDefinition = "text", nullable = false)
	private String keyword; // 키워드
	
	@Column(columnDefinition = "text", nullable = false)
	private String content;// 대답

}
