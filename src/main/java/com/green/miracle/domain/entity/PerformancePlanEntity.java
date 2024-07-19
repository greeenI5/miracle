package com.green.miracle.domain.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	
	@Enumerated(EnumType.STRING)
	@Column(name = "perType", nullable=false)//not null
	private PerType perType; // 장르
	
	@Column(nullable=false)//not null
	private LocalDate startAt; //시작일
	
	@Column(nullable=false)//not null
	private LocalDate finishAt; // 종료일
	
	@Column(nullable=false)//not null
	private String location; // 공연장소
	
	private String perPoster; // 포스터
	
	@CreationTimestamp
	@Column(columnDefinition = "timestamp", nullable=false)
	private LocalDate writeAt; // 작성일
	
	@Column(nullable=true)
	@ColumnDefault("0")
	private int approval; //승인여부

}
