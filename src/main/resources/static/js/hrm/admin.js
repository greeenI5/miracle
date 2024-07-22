/**
 * ID의 값을 비밀번호에 복사
 */

$(function() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content") || 'X-CSRF-TOKEN'; // 기본값 설정

    $(document).ajaxSend(function(e, xhr, options) {
        if (token && header) {
            xhr.setRequestHeader(header, token);
        }
    });
});

function syncEmpNoWithPassword() {
    const empNo = document.getElementById('empNo').value;
    const password = document.getElementById('password');
    password.value = empNo;
}

/**
 * 비밀번호 가시성 토글
 */
function togglePasswordVisibility() {
    const passwordInput = document.getElementById('password');
    const toggleIcon = document.querySelector('.toggle-password');
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        toggleIcon.textContent = '🔐';
    } else {
        passwordInput.type = 'password';
        toggleIcon.textContent = '🔒';
    }
}

/**
 * 이미지 미리보기
 */
function previewProfilePic(event) {
    const input = event.target;
    const reader = new FileReader();

    reader.onload = function() {
        const dataURL = reader.result;
        const profilePic = document.getElementById('profilePic');
        profilePic.src = dataURL;
    };

    reader.readAsDataURL(input.files[0]);
}

/**
 * 직원 등록
 */
function addEmployee() {

	var employeeData = {
		empNo: $('#empNo').val(),
		name: $('#name').val(),
		roles: $('#roles').val(),
		position: $('#position').val(),
		depCode: $('#depCode').val(),
		phone: $('#phone').val(),
		password: $('#password').val(),
		email: $('#email').val()
	};

	console.log(employeeData)

	$.ajax({
		type: "POST",
		url: "/admin/hr/mgm",
		contentType: "application/json",
		data: JSON.stringify(employeeData),
		success: function(response) {
			alert("직원이 성공적으로 추가되었습니다.");
			// 테이블에 새 직원 추가
			addEmployeeToTable(response);
			// 조직도에 직원 추가
			addEmployeeToOrgChart(response);
		},
		error: function(error) {
			alert("직원 추가 중 오류가 발생했습니다.");
		}
	});
}

/**
 * 조직도에 직원 추가
 */
function addEmployeeToOrgChart(employee) {
    let teamClass;

    switch (parseInt(employee.depCode)) {
        case 1000:
            teamClass = '.sales-team';
            break;
        case 1003:
            teamClass = '.marketing-team';
            break;
        case 1004:
            teamClass = '.planning-team';
            break;
        case 1005:
            teamClass = '.stage-production-team';
            break;
        default:
            console.error('Unknown team code.');
            return;
    }

    const teamContainer = document.querySelector(`${teamClass} .team`);

    if (teamContainer) {
        const newEmployee = document.createElement("div");
        newEmployee.className = "emp";
        newEmployee.innerHTML = `
            <span class="empName" onclick="showEmployeeDetail('${employee.name}')">${employee.name}</span>
            <span class="bar">|</span>
            <span class="empRank">${employee.ROLE}</span>
        `;
        teamContainer.appendChild(newEmployee);
    }
}




/**
 * 직원 테이블에 추가
 */
function addEmployeeToTable(employee) {
    const tableBody = document.getElementById("employee-table-body");
    const newRow = document.createElement("tr");
    
    newRow.id = `employee-${employee.empNo}`; // 직원 ID로 행 ID 설정
    newRow.innerHTML = `
        <td>${employee.empNo}</td>
        <td data-original-name="${employee.name}">${employee.name}</td>
        <td>${employee.ROLE}</td>
        <td>${employee.position}</td>
        <td>${employee.depCode}</td>
        <td>${employee.phone}</td>
        <td>
            <button onclick="editEmployee(this)">수정</button>
            <button onclick="deleteEmployee(this)">삭제</button>
        </td>
    `;
    
    tableBody.appendChild(newRow);
}



/**
 * 직원 수정
 */
function editEmployee(button) {
    const row = button.parentElement.parentElement;

    console.log('Editing row:', row); // 디버깅: 현재 수정 중인 행 확인

    for (let i = 1; i < row.cells.length - 1; i++) {
        const cell = row.cells[i];
        const originalValue = cell.innerText;

        console.log(`Original value for cell ${i}:`, originalValue); // 디버깅: 원래 값 확인

        cell.dataset.originalValue = originalValue; // 원래 값 저장

        if (i === 4) { // 부서코드 셀
            const options = `
                <option value="1000">영업팀</option>
                <option value="1003">마케팅팀</option>
                <option value="1004">기획팀</option>
                <option value="1005">무대연출팀</option>
            `;
            cell.innerHTML = `<select>${options}</select>`;
            cell.querySelector('select').value = originalValue;
        } else if (i === 2) { // 권한 셀
            const roles = ["EMP", "ADMIN"];
            const options = roles.map(role => `<option value="${role}">${role}</option>`).join('');
            cell.innerHTML = `<select>${options}</select>`;
            cell.querySelector('select').value = originalValue;
        } else if (i === 3) { // 직급 셀
            const positions = ["EMPLOYEE", "ASSISTANT_MANAGER", "MANAGER", "DEPUTY_GENERAL_MANAGER", "GENERAL_MANAGER", "TEAM_LEADER"]; // 예시
            const options = positions.map(position => `<option value="${position}">${position}</option>`).join('');
            cell.innerHTML = `<select>${options}</select>`;
            cell.querySelector('select').value = originalValue;
        } else {
            cell.innerHTML = `<input type="text" value="${originalValue}">`;
        }
    }

    button.innerText = "완료"; // 버튼 텍스트 변경
    row.cells[6].innerHTML = `
        <button onclick="completeEdit(this)">완료</button>
        <button onclick="cancelEdit(this)">취소</button>
    `;
}

function cancelEdit(button) {
    const row = button.parentElement.parentElement;

    console.log('Cancelling edit for row:', row); // 디버깅: 수정 취소 중인 행 확인

    for (let i = 1; i < row.cells.length - 1; i++) {
        const cell = row.cells[i];
        const originalValue = cell.dataset.originalValue;

        console.log(`Restoring original value for cell ${i}:`, originalValue); // 디버깅: 복원할 값 확인

        cell.innerHTML = originalValue; // 원래 값으로 복원
    }

    row.cells[6].innerHTML = `
        <button onclick="editEmployee(this)">수정</button>
        <button onclick="deleteEmployee(this)">삭제</button>
    `;
}

function completeEdit(button) {
    const row = button.parentElement.parentElement;
    const empNo = row.cells[0].innerText;
    const updatedData = {
        empNo: empNo,
        name: row.cells[1].querySelector('input').value,
        roles: row.cells[2].querySelector('select').value,
        position: row.cells[3].querySelector('select').value,
        depCode: row.cells[4].querySelector('select').value,
        phone: row.cells[5].querySelector('input').value
    };

    $.ajax({
        type: "PUT",
        url: `/admin/hr/mgm/${empNo}`,
        contentType: "application/json",
        data: JSON.stringify(updatedData),
        success: function(response) {
            // 성공 처리
        },
        error: function(error) {
            console.error('Update error:', error);
            alert("업데이트 중 오류가 발생했습니다.");
        }
    });
}

/**
 * 직원 삭제
 */
function deleteEmployee(button) {
    const row = button.parentElement.parentElement;
    const empNo = row.id.replace('employee-', ''); // 'employee-'를 제거하여 empNo 추출

    console.log('Employee No:', empNo);

    const confirmDelete = confirm("정말로 삭제하시겠습니까?");

    if (confirmDelete) {
        $.ajax({
            type: "DELETE",
            url: `/admin/hr/mgm/${empNo}`, // 정확한 empNo URL 사용
            success: function(response) {
                alert("직원이 성공적으로 삭제되었습니다.");
                row.remove(); // 행 삭제
            },
            error: function(error) {
                console.error("직원 삭제 중 오류가 발생했습니다.", error);
                alert("직원 삭제 중 오류가 발생했습니다.");
            }
        });
    }
}

document.addEventListener('DOMContentLoaded', function() {
    // 부서 코드와 부서명 매핑
    const departmentMap = {
        1000: '영업팀',
        1003: '마케팅팀',
        1004: '기획팀',
        1005: '무대연출팀'
    };

    // 부서코드로 부서명 업데이트
    function updateDepartmentNames() {
        const rows = document.querySelectorAll('#employee-table-body .depCode');
        rows.forEach(row => {
            const depCode = parseInt(row.textContent.trim(), 10); // 부서코드 가져오기
            const depName = departmentMap[depCode] || '알 수 없음'; // 매핑된 부서명 또는 기본값
            row.textContent = depName; // 부서명으로 업데이트
        });
    }

    // 페이지 로드 시 부서명 업데이트
    updateDepartmentNames();
});
