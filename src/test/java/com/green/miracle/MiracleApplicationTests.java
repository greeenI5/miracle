package com.green.miracle;

import java.time.LocalDate;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.green.miracle.domain.entity.ChatBotCategoryEntity;
import com.green.miracle.domain.entity.DepartmentEntity;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.Position;
import com.green.miracle.domain.entity.Role;
import com.green.miracle.domain.entity.ScheduleEntity;
import com.green.miracle.domain.repository.ChatBotRepository;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.domain.repository.ScheduleEntityRepository;

@SpringBootTest
class MiracleApplicationTests {

	@Autowired
	private EmployeeEntityRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;
    @Autowired
    private ChatBotRepository chatBotRepository;
    @Autowired
    private ScheduleEntityRepository scheduleRepository;
	
	//@Test
    //Employee 추가 코드
	public void insertEmployee(){
		DepartmentEntity department = new DepartmentEntity();
		department.setDepCode(1000);
		IntStream.rangeClosed(1, 9).forEach(i -> {
			EmployeeEntity employee = EmployeeEntity.builder()
					.email("test0"+i+"@test.com")
					.password(passwordEncoder.encode("1234"))
					.name("user"+i)
					.phone("010-0000-0000")
					.position(Position.EMPLOYEE)
					.department(department)
					.build();
			employee.addRole(Role.EMP);
			repository.save(employee);
		});
	}
	
	//@Test
	//Admin 권한 Employee 추가 코드
	public void insertAdmin(){
		DepartmentEntity department = new DepartmentEntity();
		department.setDepCode(1000);
		
		EmployeeEntity employee = EmployeeEntity.builder()
				.email("test10@test.com")
				.password(passwordEncoder.encode("1234"))
				.name("user10")
				.phone("010-0000-0000")
				.position(Position.GENERAL_MANAGER)
				.department(department)
				.build();
		employee.addRole(Role.ADMIN);
		repository.save(employee);
	}
	
	//@Test
	//일정 추가
	public void addPlan() {
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpNo(1004);
		
		ScheduleEntity schedule = ScheduleEntity.builder()
				.employee(employee)
				.startAt(LocalDate.now())
				.finishAt(LocalDate.now())
				.schTitle("10시 회의")
				.schContent("줌 열고 10분 전 세팅해놔야함.")
				.build();
		scheduleRepository.save(schedule);
	}


}
