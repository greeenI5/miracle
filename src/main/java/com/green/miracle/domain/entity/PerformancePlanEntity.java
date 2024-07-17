package com.green.miracle.domain.entity;

import java.time.LocalDate;

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

@SequenceGenerator(name="gen_plan", //no키를 1씩 증가시켜 저장
	sequenceName = "seq_plan", initialValue = 1, allocationSize = 1)
@Getter
@Entity
@Table(name="performance_plan")
public class PerformancePlanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_plan")
	private long planNo; //기획번호
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "employee_no" , nullable =false ) // 가져올 테이블명
	private EmployeeEntity employee; // 사원번호
	
	@Column(nullable=false)//not null
	private String perTitle; // 공연제목
	
	@Column(nullable=false)//not null
	private String perContent; // 기획내용
	
	@Column(nullable=false)//not null
	private LocalDate startAt; //시작일
	
	@Column(nullable=false)//not null
	private LocalDate finalAt; // 종료일
	
	@Column(nullable=false)//not null
	private String locatein; // 공연장소
	
	private String perPoster; // 포스터
	
	@Column(nullable=false)//not null
	private LocalDate writeAt; // 작성일
	
	private int approval;
}
