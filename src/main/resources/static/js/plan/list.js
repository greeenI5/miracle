document.addEventListener("DOMContentLoaded", function() {
    const planList = document.getElementById("plan-list");

    // Function to add plans to the table
    function addPlansToTable(plans) {
        plans.forEach(plan => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${plan.no}</td>
                <td><a th:href="@{/performance/plan/{plan_no}(plan_no=${plan.no})}" class="plan-name">${plan.name}</a></td>
                <td>${plan.author}</td>
                <td>${plan.date}</td>
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
            plan.name.toLowerCase().includes(searchInput) ||
            plan.author.toLowerCase().includes(searchInput)
        );

        // Clear existing table rows
        while (planList.firstChild) {
            planList.removeChild(planList.firstChild);
        }

        // Add filtered plans to the table
        addPlansToTable(filteredPlans);
    });
});
