package com.green.miracle.domain.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name="performance_plan")
public class PerformancePlanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long planNo; //기획번호
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "empNo", nullable = false)
	private EmployeeEntity employee; //사원번호 (fk)
	
	@Column(nullable=false)//not null
	private String perTitle; // 공연제목
	
	@Column(nullable=false)//not null
	private String perContent; // 기획내용
	
	@Column(nullable=false)//not null
	private LocalDate startAt; //시작일
	
	@Column(nullable=false)//not null
	private LocalDate finalAt; // 종료일
	
	@Column(nullable=false)//not null
	private String location; // 공연장소
	
	private String perPoster; // 포스터
	
	@CreationTimestamp
	@Column(columnDefinition = "timestamp", nullable=false)
	private LocalDate writeAt; // 작성일
	
	private int approval;
}
