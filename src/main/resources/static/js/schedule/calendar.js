document.addEventListener('DOMContentLoaded', function () {
    const calendarBody = document.querySelector('.calendar-body');
    const prevBtn = document.querySelector('.prev');
    const nextBtn = document.querySelector('.next');
    const mainDate = document.querySelector('.main-date');
    const contentRightMsg = document.querySelector('.content-right-msg');
    const eventTitle = document.querySelector('.event-title');
    const selectedDateElem = document.getElementById('selected-date');

    let today = new Date();
    let selectedStartAt = null;
    let selectedFinishAt = null;

    function formatDate(date) {
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    function initCalendar(year, month) { 
    calendarBody.innerHTML = '';
    const firstDayOfMonth = new Date(year, month, 1).getDay();
    const lastDateOfMonth = new Date(year, month + 1, 0).getDate();

    // Add days from previous month
    for (let i = 0; i < firstDayOfMonth; i++) {
        const prevMonthLastDate = new Date(year, month, 0).getDate();
        const dayElement = document.createElement('div');
        dayElement.classList.add('day', 'prev-month');
        dayElement.textContent = prevMonthLastDate - firstDayOfMonth + i + 1;
        dayElement.dataset.date = formatDate(new Date(year, month - 1, prevMonthLastDate - firstDayOfMonth + i + 1));
        calendarBody.appendChild(dayElement);
    }

    // Add days of the current month
    for (let i = 0; i < lastDateOfMonth; i++) {
        const day = i + 1;
        const dayElement = document.createElement('div');
        dayElement.classList.add('day');
        dayElement.textContent = day;
        dayElement.dataset.date = formatDate(new Date(year, month, day));
        calendarBody.appendChild(dayElement);
    }

    // Add days for the next month
    const totalDays = calendarBody.querySelectorAll('.day').length;
    const nextMonthDays = 42 - totalDays;
    for (let i = 0; i < nextMonthDays; i++) {
        const dayElement = document.createElement('div');
        dayElement.classList.add('day', 'next-month');
        dayElement.textContent = i + 1;
        dayElement.dataset.date = formatDate(new Date(year, month + 1, i + 1));
        calendarBody.appendChild(dayElement);
    }

    // Highlight today's date
    const todayElement = calendarBody.querySelector(`[data-date="${formatDate(today)}"]`);
    if (todayElement) {
        todayElement.classList.add('active');
    }

    // Add event listener to each day
    const dayElements = calendarBody.querySelectorAll('.day');
    dayElements.forEach(day => {
        day.addEventListener('click', function () {
            // Remove 'active' class from all days
            dayElements.forEach(d => d.classList.remove('active'));
            
            // Add 'active' class to the clicked day
            this.classList.add('active');

            const selectedDate = new Date(this.dataset.date);
            const formattedDate = formatDate(selectedDate);

            // Remove previous selection
            removeSelection();

            // Update selection based on the clicked date
            if (!selectedStartAt) {
                selectedStartAt = formattedDate;
                this.classList.add('selected-start');
            } else if (!selectedFinishAt && selectedDate >= new Date(selectedStartAt)) {
                selectedFinishAt = formattedDate;
                this.classList.add('selected-end');
                updateEventTitle(selectedStartAt, selectedFinishAt);
            } else {
                selectedStartAt = formattedDate;
                selectedFinishAt = null;
                this.classList.add('selected-start');
            }

            handleDateChange(formattedDate); // 통합 함수 호출
        });
    });
}


    function updateEventTitle(startAt, finishAt) {
        if (eventTitle) {
            eventTitle.innerHTML = `<p>일정 제목: ${startAt} ~ ${finishAt}</p>`;
        } else {
            console.error('Event title element not found.');
        }
    }

    function removeSelection() {
    const dayElements = calendarBody.querySelectorAll('.day');
    dayElements.forEach(day => {
        day.classList.remove('selected-start', 'selected-end');
        // Remove 'active' class if needed
        // If you want to preserve 'active' class behavior in some cases, you can adjust this part.
    });
}


    function handleDateChange(date) {
        if (!date) {
            return;
        }

        fetch(`/schedule/detail?date=${date}`)
            .then(response => response.text())
            .then(data => {
                contentRightMsg.innerHTML = data;
                document.getElementById('save-memo')?.addEventListener('click', saveMemo);
            })
            .catch(error => {
                console.error('Error loading detail page:', error);
                contentRightMsg.innerHTML = '<p>상세 정보를 로드하는 중 오류가 발생했습니다.</p>';
            });

        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        fetch('/schedule/date', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            },
            body: JSON.stringify({ date })
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            updateScheduleList(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
    }

    function updateScheduleList(schedules) {
        const scheduleContainer = document.querySelector('.content-right-msg');
        scheduleContainer.innerHTML = '';

        if (schedules.length > 0) {
            const maxSchedules = 4;
            const count = Math.min(schedules.length, maxSchedules);

            for (let i = 0; i < count; i++) {
                const schedule = schedules[i];
                const listItem = document.createElement('form');
                listItem.id = 'memo-form';
                listItem.innerHTML = `
                    <label for="start-date">시작일:</label>
                    <input type="date" id="start-date" name="startAt" value="${schedule.startAt}" required>
                    <label for="end-date">종료일:</label>
                    <input type="date" id="end-date" name="finishAt" value="${schedule.finishAt}" required>
                    <hr>
                    <div class="memo-section">
                        <label for="memo-title">메모 제목:</label>
                        <input type="text" id="memo-title" name="schTitle" value="${schedule.schTitle}" placeholder="메모 제목 입력" required>
                        <label for="memo-content">메모 내용:</label>
                        <textarea id="memo-content" name="schContent" placeholder="메모 내용 입력" required>${schedule.schContent}</textarea>
                        <button id="save-memo" type="button">메모 저장</button>
                    </div>
                `;
                scheduleContainer.appendChild(listItem);
            }
        } else {
            scheduleContainer.innerHTML = '<div class="checklist">일정이 없습니다.</div>';
        }
    }

    function saveMemo() {
        const form = document.getElementById('memo-form');
        const formData = new FormData(form);

        fetch('/schedule/save', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(result => {
            if (result.includes('success')) {
                alert('메모가 저장되었습니다.');
                initCalendar(today.getFullYear(), today.getMonth());
            } else {
                alert('메모 저장에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error saving memo:', error);
            alert('메모 저장 중 오류가 발생했습니다.');
        });

        return false; // 기본 폼 제출 방지
    }
    
    // Update mainDate to show the current month and year
    mainDate.textContent = `${today.getFullYear()}년 ${today.getMonth() + 1}월`;

    // Initialize the calendar for the current month
    initCalendar(today.getFullYear(), today.getMonth());

    const todayFormatted = formatDate(today);
    handleDateChange(todayFormatted); // 초기화 시 오늘 날짜의 스케줄과 상세 정보 로드
    
    prevBtn.addEventListener('click', function () {
        today.setMonth(today.getMonth() - 1);
        mainDate.textContent = `${today.getFullYear()}년 ${today.getMonth() + 1}월`;
        initCalendar(today.getFullYear(), today.getMonth());
    });

    nextBtn.addEventListener('click', function () {
        today.setMonth(today.getMonth() + 1);
        mainDate.textContent = `${today.getFullYear()}년 ${today.getMonth() + 1}월`;
        initCalendar(today.getFullYear(), today.getMonth());
    });
	/*
    initCalendar(today.getFullYear(), today.getMonth());
    const todayFormatted = formatDate(today);
    handleDateChange(todayFormatted); // 초기화 시 오늘 날짜의 스케줄과 상세 정보 로드
	*/
});
