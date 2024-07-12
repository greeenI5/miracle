/**
 * 
 */
const urlParams = new URLSearchParams(window.location.search);
const empName = urlParams.get('empName');
	// detail.html 에서 사원 정보를 가져오는 로직
	const employeeDetails = {
		'김한솔': {
			department: '영업부',
			name: '김한솔',
			position: '팀장',
			phone: '010-0000-0000',
			email: 'kimhansol@example.com'
		},
		'유해리': {
			department: '영업팀',
			name: '유해리',
			position: '팀장',
			phone: '010-0000-0001',
			email: 'yuhaeri@example.com'
		},
		'박민규': {
			department: '영업팀',
			name: '박민규',
			position: '대리',
			phone: '010-0000-0002',
			email: 'parkmingyu@example.com'
		},
		'신현직': {
			department: '영업팀',
			name: '신현직',
			position: '부장',
			phone: '010-0000-0003',
			email: 'shinhyunjeok@example.com'
		},
		'이지원': {
			department: '영업팀',
			name: '이지원',
			position: '차장',
			phone: '010-1234-5678',
			email: 'leejiwon@gmail.com'
		},
		'정하나': {
			department: '영업팀',
			name: '정하나',
			position: '사원',
			phone: '010-0000-0004',
			email: 'jeonghana@example.com'
		},
		'홍길동': {
			department: '영업팀',
			name: '홍길동',
			position: '사원',
			phone: '010-0000-0005',
			email: 'honggildong@example.com'
		},
		'박태주': {
			department: '마케팅팀',
			name: '박태주',
			position: '팀장',
			phone: '010-0000-0006',
			email: 'parktaeju@example.com'
		},
		'김하얀': {
			department: '마케팅팀',
			name: '김하얀',
			position: '대리',
			phone: '010-0000-0007',
			email: 'kimhayan@example.com'
		},
		'문유리': {
			department: '마케팅팀',
			name: '문유리',
			position: '사원',
			phone: '010-0000-0008',
			email: 'moonyuri@example.com'
		},
		'이혜지': {
			department: '마케팅팀',
			name: '이혜지',
			position: '사원',
			phone: '010-0000-0009',
			email: 'leehyeji@example.com'
		},
		'박세나': {
			department: '관리부',
			name: '박세나',
			position: '부장',
			phone: '010-0000-0010',
			email: 'parksena@example.com'
		},
		'김도영': {
			department: '기획팀',
			name: '김도영',
			position: '팀장',
			phone: '010-0000-0011',
			email: 'kimdoyoung@example.com'
		},
		'손현준': {
			department: '기획팀',
			name: '손현준',
			position: '과장',
			phone: '010-0000-0012',
			email: 'sonhyunjun@example.com'
		},
		'임수현': {
			department: '기획팀',
			name: '임수현',
			position: '대리',
			phone: '010-0000-0013',
			email: 'yimsoohyun@example.com'
		},
		'윤민석': {
			department: '기획팀',
			name: '윤민석',
			position: '사원',
			phone: '010-0000-0014',
			email: 'yoonminseok@example.com'
		},
		'전용준': {
			department: '무대연출팀',
			name: '전용준',
			position: '팀장',
			phone: '010-0000-0015',
			email: 'jeonyongjun@example.com'
		},
		'김준형': {
			department: '무대연출팀',
			name: '김준형',
			position: '대리',
			phone: '010-0000-0016',
			email: 'kimjunhyung@example.com'
		},
		'염가은': {
			department: '무대연출팀',
			name: '염가은',
			position: '부장',
			phone: '010-0000-0017',
			email: 'yeomgaeun@example.com'
		},
		'이지훈': {
			department: '무대연출팀',
			name: '이지훈',
			position: '차장',
			phone: '010-0000-0018',
			email: 'leejihun@example.com'
		},
		'이현호': {
			department: '무대연출팀',
			name: '이현호',
			position: '사원',
			phone: '010-0000-0019',
			email: 'leehyunho@example.com'
		},
		'최재영': {
			department: '무대연출팀',
			name: '최재영',
			position: '사원',
			phone: '010-0000-0020',
			email: 'choijaeyoung@example.com'
		}
	};

	// tooltip에 사원 정보를 보여주는 로직
if (empName && employeeDetails[empName]) {
    showEmployeeDetail(empName);
}

function showEmployeeDetail(empName) {
    const tooltip = document.getElementById('tooltip');
    const employee = employeeDetails[empName];

    if (employee) {
        // 툴팁이 이미 보이고 있는지 확인
        if (tooltip.classList.contains('show-tooltip')) {
            // 내용만 업데이트
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
            // 툴팁이 보이지 않을 때 새로 보여줌
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