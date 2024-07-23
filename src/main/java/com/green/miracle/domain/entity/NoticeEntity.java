package com.green.miracle.domain.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.green.miracle.domain.dto.NoticeDetailDTO;
import com.green.miracle.domain.dto.NoticeUpdateDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "notice")
public class NoticeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long noticeNo; // 글 번호

	@ManyToOne
	@JoinColumn(name = "empNo", nullable = false)
	private EmployeeEntity employee; // 사원번호 (fk)

	@Column(columnDefinition = "varchar(255)", nullable = false)
	private String noticeTitle; // 공지사항 제목
	
	@Column(columnDefinition = "text", nullable =false)
	private String noticeContent; // 공지사항 내용

	@CreationTimestamp
	@Column(columnDefinition = "timestamp", nullable = false)
	private LocalDate writeAt; // 작성일

	public NoticeDetailDTO toNoticeDetailDTO() {
		return NoticeDetailDTO.builder().noticeNo(noticeNo).employee(employee).noticeTitle(noticeTitle)
				.noticeContent(noticeContent).writeAt(writeAt).build();
	}

	public NoticeEntity update(NoticeUpdateDTO dto) {
		this.noticeTitle = dto.getNoticeTitle();
		this.noticeContent = dto.getNoticeContent();
		return this;
	}

}