package com.green.miracle.domain.entity;

import java.time.LocalDate;

import com.green.miracle.domain.dto.ScheduleChangeDTO;

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
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity //entity임을 칭함
@Table(name="schedule") // 테이블명 설정
public class ScheduleEntity {
	
	@Id // PK
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long schNo; //일정번호
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "empNo",nullable = false)
	private EmployeeEntity employee; //사원번호 (fk)
	
	@Column(nullable=false)//not null
	private LocalDate startAt; //시작일
	
	@Column(nullable=false)//not null
	private LocalDate finishAt; //종료일
	
	@Column(nullable=false)//not null
	private String schTitle; //일정제목
	
	@Builder.Default
	private String schContent = ""; //일정내용

	public ScheduleEntity update(ScheduleChangeDTO dto) {
		this.startAt=dto.getStartAt();
		this.finishAt=dto.getFinishAt();
		this.schTitle=dto.getSchTitle();
		this.schContent=dto.getSchContent();
		return this;	
	}
}