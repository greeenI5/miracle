document.addEventListener("DOMContentLoaded", function() {
    const searchForm = document.getElementById("search-form");
    const searchInput = document.getElementById("search");
    const planList = document.getElementById("plan-list");
    const pagination = document.getElementById("pagination");
    const rowsPerPage = 9;
    let currentPage = 1;
    let allRows;

    // 검색 폼 이벤트 리스너
    searchForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const query = searchInput.value.toLowerCase();
        filterTable(query);
    });

    // 테이블 필터링 함수
    function filterTable(query) {
        allRows = Array.from(planList.querySelectorAll("tr"));
        console.log("All rows:", allRows);  // 디버깅: 모든 행을 확인합니다.
        
        allRows.forEach(row => {
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
        updatePagination();
        displayPage(1);
    }

    // 페이지 업데이트 함수
    function updatePagination() {
        pagination.innerHTML = "";
        const totalPages = Math.ceil(allRows.length / rowsPerPage);

        for (let i = 1; i <= totalPages; i++) {
            const pageButton = document.createElement("button");
            pageButton.innerText = i;
            pageButton.classList.add("page-btn");
            if (i === currentPage) {
                pageButton.classList.add("active");
            }
            pageButton.addEventListener("click", function() {
                displayPage(i);
            });
            pagination.appendChild(pageButton);
        }
    }

    // 특정 페이지 표시 함수
    function displayPage(page) {
        currentPage = page;
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;

        allRows.forEach((row, index) => {
            row.style.display = (index >= start && index < end) ? "" : "none";
        });

        document.querySelectorAll(".page-btn").forEach(button => {
            button.classList.remove("active");
        });
        pagination.children[page - 1].classList.add("active");
    }

    // 초기화
    allRows = Array.from(planList.querySelectorAll("tr"));
    console.log("All rows:", allRows); // 디버깅: 데이터 확인
    updatePagination();
    displayPage(currentPage);
});
