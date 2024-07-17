document.addEventListener("DOMContentLoaded", function() {
    const planList = document.getElementById("plan-list");

    // 가짜 데이터 (실제 프로젝트에서는 서버에서 가져옵니다)
    const plans = [
        { id: 1, name: "프로젝트 A", author: "기획자 A", date: "2024-01-01", approved: false },
        { id: 2, name: "프로젝트 B", author: "기획자 B", date: "2024-02-01", approved: false },
        { id: 3, name: "프로젝트 C", author: "기획자 C", date: "2024-03-01", approved: true },
    ];

    // 테이블에 데이터 추가 (결재 요청된 기획서만)
    function renderPlans(plans) {
        // 기존 리스트 지우기
        while (planList.firstChild) {
            planList.removeChild(planList.firstChild);
        }

        // 결재 요청된 기획서만 추가
        plans.filter(plan => !plan.approved).forEach(plan => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${plan.id}</td>
                <td><a href="/approval/view/${plan.id}">${plan.name}</a></td>
                <td>${plan.author}</td>
                <td>${plan.date}</td>
                <td><button class="approve-button" data-id="${plan.id}">승인</button></td>
            `;

            planList.appendChild(row);
        });
    }

    // 초기에 결재 요청된 기획서만 표시
    renderPlans(plans);

    // 검색 기능 추가
    const searchForm = document.getElementById("search-form");
    searchForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const searchInput = document.getElementById("search").value.toLowerCase();

        const filteredPlans = plans.filter(plan => 
            (plan.name.toLowerCase().includes(searchInput) || 
            plan.author.toLowerCase().includes(searchInput)) && 
            !plan.approved
        );

        // 필터링된 데이터 추가
        renderPlans(filteredPlans);
    });

    // 승인 버튼 클릭 이벤트 핸들러
    planList.addEventListener("click", function(event) {
        if (event.target.classList.contains("approve-button")) {
            const planId = event.target.getAttribute("data-id");
            approvePlan(planId);
        }
    });

    function approvePlan(planId) {
        // 실제 구현에서는 fetch나 axios 등을 사용하여 서버로 데이터 전송
        fetch(`/approval/approve/${planId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(result => {
            if (result.success) {
                // 승인된 기획서 목록에서 제거
                const index = plans.findIndex(plan => plan.id == planId);
                if (index > -1) {
                    plans[index].approved = true;
                }
                renderPlans(plans);
            } else {
                alert('결재 승인 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('결재 승인 중 오류가 발생했습니다.');
        });
    }
});
/*
주요 기능
결재 요청된 기획서만 목록에 출력: approved 속성이 false인 기획서만 목록에 추가합니다.
검색 기능: 검색 입력값에 따라 기획서를 필터링하고 목록에 표시합니다.
결재 승인 버튼: 각 기획서 옆에 "승인" 버튼을 추가하고, 클릭 시 해당 기획서를 서버에 승인 요청하고 목록에서 제거합니다.
뷰 페이지 링크: 기획서 이름을 클릭하면 /approval/view/{id} URL로 이동합니다.
 */