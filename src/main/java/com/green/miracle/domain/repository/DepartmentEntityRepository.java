package com.green.miracle.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.miracle.domain.entity.DepartmentEntity;

public interface DepartmentEntityRepository extends JpaRepository<DepartmentEntity, Long>{
	
	DepartmentEntity findByDepCode(long depCode);
	
}
