package com.green.miracle.domain.entity;

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

@DynamicUpdate
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="notice")
public class Notice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long notice_no; // 글 번호
	
	@ManyToOne
	@JoinColumn(name = "employee_no" , nullable =false )
	private long no; // 사원번호
	
	@Column(nullable =false)
	private String notice_title; // 공지사항 제목
	
	@Column(columnDefinition = "blob", nullable =false)
	private String notice_content; // 공지사항 내용
	
	
}