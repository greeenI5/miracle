package com.green.miracle.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "chatBot")
public class ChatBotEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long qNo; //질문번호 (pk)
	
	@Column(columnDefinition = "blob", nullable = false)
	private String question; // 질문
	
	@Column(columnDefinition = "blob", nullable = false)
	private String answer;// 대답

}
