package com.green.miracle.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import com.green.miracle.domain.dto.ChatBotCategoryDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "chatbotCategory")
public class ChatBotCategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cNo; // 카테고리 버튼 번호(pk)
	
	@Column(columnDefinition = "text", nullable = false)
	private String content; // 버튼내용
	
	@ManyToOne
	@JoinColumn(name = "type")
	private ChatBotCategoryEntity parent;// 타입
	
	
	public ChatBotCategoryDTO toChatBotCategoryDTO() {
		return ChatBotCategoryDTO.builder()
				.cNo(cNo).content(content).type(parent.cNo)
				.build();
	}

}
