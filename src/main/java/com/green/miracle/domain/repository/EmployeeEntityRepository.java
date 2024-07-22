package com.green.miracle.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.green.miracle.domain.entity.EmployeeEntity;

public interface EmployeeEntityRepository extends JpaRepository<EmployeeEntity, Long> {

	Optional<EmployeeEntity> findByEmail(String email);

	@Query("SELECT MAX(e.empNo) FROM EmployeeEntity e")
    Integer findMaxEmployeeNumber();

}
