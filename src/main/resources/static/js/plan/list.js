// /js/plan/list.js

document.addEventListener("DOMContentLoaded", function() {
    const planList = document.getElementById("plan-list");

    // 가짜 데이터 (실제 프로젝트에서는 서버에서 가져옵니다)
    const plans = [
        { no: 1, name: "프로젝트 A", author: "기획자 A", date: "2024-01-01" },
        { no: 2, name: "프로젝트 B", author: "기획자 B", date: "2024-02-01" },
        { no: 3, name: "프로젝트 C", author: "기획자 C", date: "2024-03-01" },
    ];

    // 테이블에 데이터 추가
    plans.forEach(plan => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${plan.no}</td>
            <td>${plan.name}</td>
            <td>${plan.author}</td>
            <td>${plan.date}</td>
        `;

        planList.appendChild(row);
    });

    // 검색 기능 추가
    const searchForm = document.getElementById("search-form");
    searchForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const searchInput = document.getElementById("search").value.toLowerCase();

        const filteredPlans = plans.filter(plan => 
            plan.name.toLowerCase().includes(searchInput) ||
            plan.author.toLowerCase().includes(searchInput)
        );

        // 기존 리스트 지우기
        while (planList.firstChild) {
            planList.removeChild(planList.firstChild);
        }

        // 필터링된 데이터 추가
        filteredPlans.forEach(plan => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${plan.no}</td>
                <td>${plan.name}</td>
                <td>${plan.author}</td>
                <td>${plan.date}</td>
            `;

            planList.appendChild(row);
        });
    });
});
