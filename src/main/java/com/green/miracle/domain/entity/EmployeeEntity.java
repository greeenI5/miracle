package com.green.miracle.domain.entity;

import java.util.HashSet;
import java.util.Set;

import com.green.miracle.domain.dto.AdminHrListDTO;
import com.green.miracle.domain.dto.AdminHrUpdate;
import com.green.miracle.domain.dto.EmployeeDTO;
import com.green.miracle.domain.dto.EmployeeUpdateDTO;
import com.green.miracle.domain.dto.MyPageDTO;
import com.green.miracle.domain.repository.DepartmentEntityRepository;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter // Setter는 주로 안 씀
@Setter
//시퀀스 생성시켜주는 기능 > name은 generator의 이름, sequenceName이 sequence의 이름
//초기값 : initialValue, 증감 : allocationSize
@SequenceGenerator(name = "gen_employee", sequenceName = "seq_employee", initialValue = 1001, allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee") // 별도로 지정하지 않으면 클래스이름이 테이블명
public class EmployeeEntity {

	@Id // PK 컬럼 설정하는 어노테이션
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_employee")
	private long empNo; // 사원번호

	@Column(nullable = false)
	private String name; // 사원명

	@Column(nullable = false, unique = true)
	private String email; // 이메일

	@Column(nullable = false)
	private String password; // 비밀번호

	@Column(nullable = false)
	private String phone; // 핸드폰번호

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Position position; // 직급

	// ROLE 권한
	@Enumerated(EnumType.STRING) // DB저장 데이터타입 > 문자값으로 저장(DB에 저장될때 role은 string으로 저장 >> default는 숫자)
	@CollectionTable(name = "role", // RoleEntity 이름(물리적)
			joinColumns = @JoinColumn(name = "empNo")) // collection을 entity로 만든다는 것. 여기서 name은 FK컬럼명
	@ElementCollection(fetch = FetchType.EAGER) // 1:N MemberEntity에서만 접근 가능한 내장테이블
	// fetch LAZY : 지연 로딩
	// fetch EAGER : 즉시 로딩
	// MemberEntity로 접근한다음에 Role을 접근해야 한다는 것.
	@Builder.Default // builder의 디폴트 값으로 자동 초기화
	@Column(name = "roleName") // roles의 컬럼명을 role로 바꿈 (자동으로 만들어주긴하나 Role 엔터티에서 컬럼명을 커스텀할때 사용)
	private Set<Role> roles = new HashSet<Role>();

	// role들은 메서드로 저장할 것
	// Role 등록하기 위한 편의메서드
	public EmployeeEntity addRole(Role role) {
		roles.add(role);
		return this;
	}

	public EmployeeEntity addRoleByRange(String role) {
		for (int i = 0; i <= Role.valueOf(role).ordinal(); i++) { // .ordinal() == 범위
			// addRole(Role.values()[i]);
			roles.add(Role.values()[i]); // addRole 메서드가 없는 경우
		}
		return this;
	}

	@ManyToOne
	@JoinColumn(name = "depCode", nullable = false)
	private DepartmentEntity department; // 부서코드
	// camel 표기법은 db에서 자동으로 underscore 표기법으로 적용됨

	public AdminHrListDTO toListDTO() {
		return AdminHrListDTO.builder().empNo(empNo).name(name).roles(roles).position(position)
				.depCode(department.getDepCode()) // DepartmentEntity의 코드를 가져와서 설정
				.phone(phone).build();
	}
	
	public EmployeeDTO toListdto() {
		return EmployeeDTO.builder()
							.name(name).position(position)
							.depCode(department.getDepCode()) // DepartmentEntity의 코드를 가져와서 설정
							.build();
	}

	
	public EmployeeEntity update(EmployeeUpdateDTO dto) {
		this.email = dto.getEmail();
		this.phone = dto.getPhone();
		this.password = dto.getPassword();
		return this;
	}

	public EmployeeEntity update(AdminHrUpdate dto, DepartmentEntity departmentRepository) {
        this.empNo = dto.getEmpNo();
        this.name = dto.getName();
        this.roles = dto.getRoles();
        this.position = dto.getPosition();
        this.department = departmentRepository; // 부서 객체 설정
        this.phone = dto.getPhone();
        return this;
    }
	
}