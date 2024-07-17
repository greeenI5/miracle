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
@Table(name = "chatLogs")
public class ChatLogsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long logNo; //로그번호 (pk)
	
	@ManyToOne
	@JoinColumn(name = "empNo")
	private EmployeeEntity employee; //사원번호 (fk)
	
	@Column(columnDefinition = "timestamp", nullable = false)
	private LocalDateTime logTime; //
	
	@Column(columnDefinition = "blob", nullable = false)
	private String userText; //
	
	@Column(columnDefinition = "blob", nullable = false)
	private String botResponse;// 챗봇이 응답한 멧

}
