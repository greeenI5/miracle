/**
 * 
 */

/*백엔드 기능 구현 후 확인해보기*/

document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const empName = urlParams.get('empName');
    
    if (empName) {
        fetch(`/employeeDetail?empName=${empName}`)
            .then(response => response.text())
            .then(html => {
                document.body.innerHTML = html;
                showEmployeeDetail(empName);  // 사원 정보 로드 후 툴팁을 보여주는 로직 호출
            })
            .catch(error => console.error('Error loading employee details:', error));
    }
});

function showEmployeeDetail(empName) {
    const tooltip = document.getElementById('tooltip');
    
    // 동적으로 가져온 HTML에서 사원 정보를 파싱
    const employee = {
        name: document.querySelector('.name').textContent,
        department: document.querySelector('.department').textContent,
        position: document.querySelector('.position').textContent,
        phone: document.querySelector('.phone').textContent,
        email: document.querySelector('.email').textContent
    };

    if (employee) {
        if (tooltip.classList.contains('show-tooltip')) {
            tooltip.innerHTML = `
                <div class="details-card">
                    <img src="../images/hrm/profile.png" alt="${employee.name}">
                    <div class="details-info">
                        <div class="name">${employee.name}</div>
                        <div class="department">${employee.department}</div>
                        <div class="position"><span>직급 :</span> ${employee.position}</div>
                        <div><span>전화번호 :</span> ${employee.phone}</div>
                        <div><span>이메일 :</span> ${employee.email}</div>
                    </div>
                    <button onclick="closeTooltip()">닫기</button>
                </div>
            `;
        } else {
            tooltip.innerHTML = `
                <div class="details-card">
                    <img src="../images/hrm/profile.png" alt="${employee.name}">
                    <div class="details-info">
                        <div class="name">${employee.name}</div>
                        <div class="department">${employee.department}</div>
                        <div class="position"><span>직급 :</span> ${employee.position}</div>
                        <div><span>전화번호 :</span> ${employee.phone}</div>
                        <div><span>이메일 :</span> ${employee.email}</div>
                    </div>
                    <button onclick="closeTooltip()">닫기</button>
                </div>
            `;
            tooltip.classList.add('show-tooltip');
        }
    }
}

function closeTooltip() {
    const tooltip = document.getElementById('tooltip');
    tooltip.classList.remove('show-tooltip');
}
