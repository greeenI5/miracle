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
    document.getElementById('password').value = document.getElementById('empNo').value;
}

function togglePasswordVisibility() {
    const passwordInput = document.getElementById('password');
    const toggleIcon = document.querySelector('.toggle-password');
    const isPassword = passwordInput.type === 'password';
    passwordInput.type = isPassword ? 'text' : 'password';
    toggleIcon.textContent = isPassword ? '🔐' : '🔒';
}

function previewProfilePic(event) {
    const input = event.target;
    const reader = new FileReader();
    reader.onload = function() {
        document.getElementById('profilePic').src = reader.result;
    };
    reader.readAsDataURL(input.files[0]);
}

function addEmployee() {
    var formData = new FormData(document.getElementById('employee-form'));

    $.ajax({
        url: "/admin/hr/mgm",
        type: "post",
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
            alert("직원이 성공적으로 추가되었습니다.");
            addEmployeeToTable(response);
            addEmployeeToOrgChart(response);
        },
        error: function(error) {
            alert("직원 추가 중 오류가 발생했습니다.");
        }
    });
}

function addEmployeeToOrgChart(employee) {
    const teamClasses = {
        1000: '.sales-team',
        1003: '.marketing-team',
        1004: '.planning-team',
        1005: '.stage-production-team'
    };

    const teamClass = teamClasses[employee.depCode];
    if (teamClass) {
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
    } else {
        console.error('Unknown team code.');
    }
}

function addEmployeeToTable(employee) {
    const tableBody = document.getElementById("employee-table-body");
    const newRow = document.createElement("tr");
    newRow.id = `employee-${employee.empNo}`;
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

const departmentMap = {
    "1000": "영업팀",
    "1003": "마케팅팀",
    "1004": "기획팀",
    "1005": "무대연출팀"
};

const roleNameMap = {
    "EMP": "사원",
    "ADMIN": "관리자"
};

const positionNameMap = {
    "EMPLOYEE": "사원",
    "ASSISTANT_MANAGER": "대리",
    "MANAGER": "과장",
    "DEPUTY_GENERAL_MANAGER": "차장",
    "GENERAL_MANAGER": "부장",
    "TEAM_LEADER": "팀장"
};

function createSelectOptions(map, selectedValue) {
    return Object.keys(map).map(key => `<option value="${key}" ${key === selectedValue ? 'selected' : ''}>${map[key]}</option>`).join('');
}

function editEmployee(button) {
    const row = button.parentElement.parentElement;
    for (let i = 1; i < row.cells.length - 1; i++) {
        const cell = row.cells[i];
        const originalValue = cell.innerText.trim();
        cell.dataset.originalValue = originalValue;

        if (i === 4) { // 부서코드 셀
            cell.innerHTML = `<select>${createSelectOptions(departmentMap, originalValue)}</select>`;
        } else if (i === 2) { // 권한 셀
            cell.innerHTML = `<select>${createSelectOptions(roleNameMap, originalValue)}</select>`;
        } else if (i === 3) { // 직급 셀
            cell.innerHTML = `<select>${createSelectOptions(positionNameMap, originalValue)}</select>`;
        } else {
            cell.innerHTML = `<input type="text" value="${originalValue}">`;
        }
    }

    button.innerText = "완료";
    row.cells[6].innerHTML = `
        <button onclick="completeEdit(this)">완료</button>
        <button onclick="cancelEdit(this)">취소</button>
    `;
}

function cancelEdit(button) {
    const row = button.parentElement.parentElement;
    for (let i = 1; i < row.cells.length - 1; i++) {
        const cell = row.cells[i];
        cell.innerHTML = cell.dataset.originalValue;
    }
    row.cells[6].innerHTML = `
        <button onclick="editEmployee(this)">수정</button>
        <button onclick="deleteEmployee(this)">삭제</button>
    `;
}

function completeEdit(button) {
    const row = button.parentElement.parentElement;
    const empNo = row.cells[0].innerText;
    const name = row.cells[1].querySelector('input').value;
    const roles = row.cells[2].querySelector('select').value;
    const position = row.cells[3].querySelector('select').value;
    const depCode = row.cells[4].querySelector('select').value;
    const phone = row.cells[5].querySelector('input').value;

    const formData = new FormData();
    formData.append('empNo', empNo);
    formData.append('name', name);
    formData.append('roles', roles);
    formData.append('position', position);
    formData.append('depCode', depCode);
    formData.append('phone', phone);

    $.ajax({
        type: "PUT",
        url: `/admin/hr/mgm/${empNo}`,
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
            alert("업데이트가 완료되었습니다.");
            row.cells[1].innerText = name;
            row.cells[2].innerText = roleNameMap[roles];
            row.cells[3].innerText = positionNameMap[position];
            row.cells[4].innerText = departmentMap[depCode];
            row.cells[5].innerText = phone;
            row.cells[6].innerHTML = `
                <button onclick="editEmployee(this)">수정</button>
                <button onclick="deleteEmployee(this)">삭제</button>
            `;
        },
        error: function(error) {
            alert("업데이트 중 오류가 발생했습니다.");
        }
    });
}

function deleteEmployee(button) {
    const row = button.parentElement.parentElement;
    const empNo = row.id.replace('employee-', '');

    if (confirm("정말로 삭제하시겠습니까?")) {
        $.ajax({
            type: "DELETE",
            url: `/admin/hr/mgm/${empNo}`,
            success: function() {
                alert("직원이 성공적으로 삭제되었습니다.");
                row.remove();
            },
            error: function(error) {
                alert("직원 삭제 중 오류가 발생했습니다.");
            }
        });
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const departmentMap = {
        1000: '영업팀',
        1003: '마케팅팀',
        1004: '기획팀',
        1005: '무대연출팀'
    };

    function updateDepartmentNames() {
        document.querySelectorAll('#employee-table-body .depCode').forEach(row => {
            const depCode = parseInt(row.textContent.trim(), 10);
            row.textContent = departmentMap[depCode] || '알 수 없음';
        });
    }

    updateDepartmentNames();
});
