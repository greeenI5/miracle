// CSRF 토큰 설정
const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

// 페이지 로드 시 포지션과 부서를 한국어로 변환
$(document).ready(function() {
    $('.empRank').each(function() {
        const position = $(this).text();
        $(this).text(getPositionInKorean(position));
    });
    $('.teamName').each(function() {
        const depCode = $(this).text();
        $(this).text(getDepCodeInKorean(depCode));
    });
});

// 사원 상세 정보를 로드하는 함수
function loadEmployeeDetail(empNo, event) {
    fetch(`/hr/${encodeURIComponent(empNo)}`, {
        method: 'GET',
        headers: {
            'Accept': 'text/html',  // 서버가 HTML을 반환하도록 요청
            [header]: token
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text();  // HTML 응답을 텍스트로 처리
    })
    .then(data => {
        const detailsCard = document.getElementById('tooltip');
        detailsCard.innerHTML = data;
        convertHtmlToKorean();  // HTML 변환 함수 호출
        showEmployeeDetail(event);  // 이벤트를 넘겨서 클릭 위치를 사용
    })
    .catch(error => console.error('Error loading employee details:', error));
}

// HTML의 텍스트를 한국어로 변환하는 함수
function convertHtmlToKorean() {
    const tooltip = document.getElementById('tooltip');
    tooltip.querySelectorAll('.position').forEach(element => {
        element.textContent = getPositionInKorean(element.textContent.trim());
    });
    tooltip.querySelectorAll('.department').forEach(element => {
        element.textContent = getDepCodeInKorean(element.textContent.trim());
    });
}

// 상세 정보 툴팁을 보여주는 함수
function showEmployeeDetail(event) {
    const tooltip = document.getElementById('tooltip');
    tooltip.classList.add('show-tooltip');
    
    // 클릭한 위치에 툴팁을 표시
    tooltip.style.top = `${event.clientY}px`;
    tooltip.style.left = `${event.clientX}px`;
}

// 툴팁을 닫는 함수
function closeTooltip() {
    const tooltip = document.getElementById('tooltip');
    tooltip.classList.remove('show-tooltip');
}

// 포지션을 한국어로 변환하는 함수
function getPositionInKorean(position) {
    const positions = {
        "EMPLOYEE": "사원",
        "ASSISTANT_MANAGER": "대리",
        "MANAGER": "과장",
        "DEPUTY_GENERAL_MANAGER": "차장",
        "GENERAL_MANAGER": "부장",
        "TEAM_LEADER": "팀장"
    };
    return positions[position] || position;
}

// 부서 코드를 한국어로 변환하는 함수
function getDepCodeInKorean(depCode) {
    const depCodes = {
        "1000": "영업부",
        "1003": "마케팅부",
        "1004": "기획부",
        "1005": "무대연출부"
    };
    return depCodes[depCode] || depCode;
}
