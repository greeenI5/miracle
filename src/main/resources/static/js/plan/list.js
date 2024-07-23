document.addEventListener("DOMContentLoaded", function() {
    const searchForm = document.getElementById("search-form");
    const searchInput = document.getElementById("search");
    const planList = document.getElementById("plan-list");

    searchForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const query = searchInput.value.toLowerCase();
        filterTable(query);
    });

    function filterTable(query) {
        const rows = planList.querySelectorAll("tr");

        rows.forEach(row => {
            const cells = row.getElementsByTagName("td");
            let shouldDisplay = false;

            for (let i = 0; i < cells.length; i++) {
                const cellText = cells[i].textContent.toLowerCase();
                if (cellText.includes(query)) {
                    shouldDisplay = true;
                    break;
                }
            }

            row.style.display = shouldDisplay ? "" : "none";
        });
    }
});
