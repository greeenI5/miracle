document.addEventListener("DOMContentLoaded", function() {
    const planList = document.getElementById("plan-list");

    // Function to add plans to the table
    function addPlansToTable(plans) {
        plans.forEach(plan => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${plan.planNo}</td>
                <td><a href="/performance/plan/${plan.planNo}" class="plan-name">${plan.perTitle}</a></td>
                <td>${plan.employee.name}</td>
                <td>${plan.writeAt}</td>
            `;

            planList.appendChild(row);
        });
    }

    // Fetch plans from the backend
    fetch('/performance/plan')
        .then(response => response.json())
        .then(plansData => {
            addPlansToTable(plansData);
        })
        .catch(error => console.error('Error fetching plans:', error));

    // Handle search form submission
    const searchForm = document.getElementById("search-form");
    searchForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const searchInput = document.getElementById("search").value.toLowerCase();

        fetch('/performance/plan')
            .then(response => response.json())
            .then(plansData => {
                const filteredPlans = plansData.filter(plans =>
                    plans.perTitle.toLowerCase().includes(searchInput) ||
                    plans.employee.name.toLowerCase().includes(searchInput)
                );

                // Clear existing table rows
                while (planList.firstChild) {
                    planList.removeChild(planList.firstChild);
                }

                // Add filtered plans to the table
                addPlansToTable(filteredPlans);
            })
            .catch(error => console.error('Error filtering plans:', error));
    });
});
