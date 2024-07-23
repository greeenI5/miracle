package com.green.miracle.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.ScheduleEntity;

@Repository
public interface ScheduleEntityRepository extends JpaRepository<ScheduleEntity, Long>{

	List<ScheduleEntity> findByEmployeeAndStartAt(EmployeeEntity employee, LocalDate cilckDate);
	
}
