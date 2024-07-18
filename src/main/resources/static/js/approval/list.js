document.addEventListener("DOMContentLoaded", function() {
    const planList = document.getElementById("plan-list");

    // Function to add plans to the table
    function addPlansToTable(plans) {
        plans.forEach(plan => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${plan.plan_no}</td>
                <td><a th:href="@{/approval/plan/{plan_no}(plan_no=${plan.no})}" class="plan-name">${plan.name}</a></td>
                <td>${plan.author}</td>
                <td>${plan.date}</td>
                <td><button class="approve-button" data-id="${plan.no}">승인</button></td>
            `;

            planList.appendChild(row);
        });
    }

    // Fetch plans from the backend using Thymeleaf model attribute
    const plansData = /*[[${plans}]]*/ [];  // This will be replaced with actual model data from Thymeleaf

    addPlansToTable(plansData);

    // Handle search form submission
    const searchForm = document.getElementById("search-form");
    searchForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const searchInput = document.getElementById("search").value.toLowerCase();

        const filteredPlans = plansData.filter(plan =>
            (plan.name.toLowerCase().includes(searchInput) || 
            plan.author.toLowerCase().includes(searchInput)) && 
            !plan.approved
        );

        // Clear existing table rows
        while (planList.firstChild) {
            planList.removeChild(planList.firstChild);
        }

        // Add filtered plans to the table
        addPlansToTable(filteredPlans);
    });

    // 승인 버튼 클릭 이벤트 핸들러
    planList.addEventListener("click", function(event) {
        if (event.target.classList.contains("approve-button")) {
            const planNo = event.target.getAttribute("data-id");
            approvePlan(planNo);
        }
    });

    function approvePlan(planNo) {
        // 실제 구현에서는 fetch나 axios 등을 사용하여 서버로 데이터 전송
        fetch(`/approval/approve/${planNo}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(result => {
            if (result.success) {
                // 승인된 기획서 목록에서 제거
                const index = plansData.findIndex(plan => plan.plan_no == planNo);
                if (index > -1) {
                    plansData[index].approved = true;
                }
                addPlansToTable(plansData);
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
