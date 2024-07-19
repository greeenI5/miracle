/**
 * ID의 값을 비밀번호에 복사
 */

$(function() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content") || 'X-CSRF-TOKEN'; // 기본값 설정

    console.log('CSRF Token:', token);
    console.log('CSRF Header:', header);

    $(document).ajaxSend(function(e, xhr, options) {
        if (token && header) {
            xhr.setRequestHeader(header, token);
        } else {
            console.error('CSRF Token or Header is missing.');
        }
    });
});

function syncEmpIdWithPassword() {
    const empId = document.getElementById('empId').value;
    const empPassword = document.getElementById('empPassword');
    empPassword.value = empId;
}

/**
 * 비밀번호 가시성 토글
 */
function togglePasswordVisibility() {
    const passwordInput = document.getElementById('empPassword');
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
 * 직원 추가
 */
function addEmployee() {
    var employeeData = {
        empId: $('#empNo').val(),
        empName: $('#name').val(),
        ROLE: $('#roles').val(),
        empPosition: $('#position').val(),
        depCode: $('#depCode').val(),
        empPhone: $('#phone').val(),
        empPassword: $('#password').val(),
        empEmail: $('#email').val()
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
        <td>${employee.empId}</td>
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
    const empId = row.cells[0].innerText;

    for (let i = 1; i < row.cells.length - 1; i++) {
        const cell = row.cells[i];
        const originalValue = cell.innerText;

        cell.dataset.originalValue = originalValue; // 원래 값 저장
        cell.innerHTML = `<input type="text" value="${originalValue}">`; // 입력 필드로 변경
    }

    button.innerText = "완료"; // 버튼 텍스트 변경
    row.cells[6].innerHTML = `
        <button onclick="completeEdit(this)">완료</button>
        <button onclick="cancelEdit(this)">취소</button>
    `;
}

function cancelEdit(button) {
    const row = button.parentElement.parentElement;

    for (let i = 1; i < row.cells.length - 1; i++) {
        const cell = row.cells[i];
        const originalValue = cell.dataset.originalValue;

        cell.innerHTML = originalValue; // 원래 값으로 복원
    }

    button.innerText = "수정"; // 버튼 텍스트 변경
    row.cells[6].innerHTML = `
        <button onclick="editEmployee(this)">수정</button>
        <button onclick="deleteEmployee(this)">삭제</button>
    `;
}

function completeEdit(button) {
    const row = button.parentElement.parentElement;

    for (let i = 1; i < row.cells.length - 1; i++) {
        const cell = row.cells[i];
        const updatedValue = cell.querySelector('input').value;

        cell.innerHTML = updatedValue; // 업데이트된 값으로 변경
    }

    button.innerText = "수정"; // 버튼 텍스트 변경
    row.cells[6].innerHTML = `
        <button onclick="editEmployee(this)">수정</button>
        <button onclick="deleteEmployee(this)">삭제</button>
    `;
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
            url: `/admin/hr/mgm/${empNo}`, // empNo를 URL에 포함시킵니다.
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
