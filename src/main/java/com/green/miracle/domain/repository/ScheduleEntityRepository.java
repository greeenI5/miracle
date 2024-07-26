package com.green.miracle.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.miracle.domain.dto.ScheduleReadDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.ScheduleEntity;

@Repository
public interface ScheduleEntityRepository extends JpaRepository<ScheduleEntity, Long>{

	List<ScheduleEntity> findByEmployeeAndStartAt(EmployeeEntity employee, LocalDate startAt);
    List<ScheduleEntity> findByStartAtBetween(LocalDate startAt, LocalDate finishAt);
	boolean existsByStartAtAndFinishAtAndSchTitle(LocalDate startAt, LocalDate finishAt, String schTitle);
}
