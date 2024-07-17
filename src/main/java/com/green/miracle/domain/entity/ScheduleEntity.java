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

@SequenceGenerator(name="gen_schedule", // no키를 1씩 증가시켜 저장함
	sequenceName = "seq_schedule", initialValue = 1, allocationSize = 1)
@Getter
@Entity //entity임을 칭함
@Table(name="schedule") // 테이블명 설정
public class ScheduleEntity {
	
	@Id // PK
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "gen_schedule")
	//@GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY가 바로 Auto_increment
	private long schNo; //일정번호
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "employee_no" , nullable =false ) // 가져올 테이블명
	private long no; // 사원번호
	
	@Column(nullable=false)//not null
	private LocalDate startAt; //시작일
	
	@Column(nullable=false)//not null
	private LocalDate finishAt; //종료일
	
	@Column(nullable=false)//not null
	private String schTitle; //일정제목
	
	private String schContent; //일정내용
}