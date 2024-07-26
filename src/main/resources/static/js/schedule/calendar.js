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
        const prevMonthLastDate = new Date(year, month, 0).getDate();
        for (let i = 0; i < firstDayOfMonth; i++) {
            const dayElement = document.createElement('div');
            dayElement.classList.add('day', 'prev-month');
            dayElement.textContent = prevMonthLastDate - firstDayOfMonth + i + 1;
            dayElement.dataset.date = formatDate(new Date(year, month - 1, prevMonthLastDate - firstDayOfMonth + i + 1));
            calendarBody.appendChild(dayElement);
        }

        // Add days of the current month
        for (let i = 1; i <= lastDateOfMonth; i++) {
            const dayElement = document.createElement('div');
            dayElement.classList.add('day');
            dayElement.textContent = i;
            dayElement.dataset.date = formatDate(new Date(year, month, i));
            calendarBody.appendChild(dayElement);
        }

        // Add days for the next month to make up 42 days in total
        const totalDays = calendarBody.querySelectorAll('.day').length;
        const nextMonthDays = 42 - totalDays;
        for (let i = 1; i <= nextMonthDays; i++) {
            const dayElement = document.createElement('div');
            dayElement.classList.add('day', 'next-month');
            dayElement.textContent = i;
            dayElement.dataset.date = formatDate(new Date(year, month + 1, i));
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
            day.addEventListener('click', handleDayClick);
        });
    }

    function handleDayClick() {
        const dayElements = calendarBody.querySelectorAll('.day');
        dayElements.forEach(d => d.classList.remove('active', 'selected-start', 'selected-end'));

        this.classList.add('active');

        const selectedDate = new Date(this.dataset.date);
        const formattedDate = formatDate(selectedDate);

        removeSelection();

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

        handleDateChange(formattedDate);
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
        dayElements.forEach(day => day.classList.remove('selected-start', 'selected-end'));
    }

    function handleDateChange(date) {
        if (!date) {
            return;
        }

        fetch(`/schedule/detail?date=${date}`)
            .then(response => response.text())
            .then(data => {
                contentRightMsg.innerHTML = data;
                attachSaveMemoListeners();
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
        .catch(error => {
            console.error('Error:', error);
        });
    }

    function updateScheduleList(schedules) {
        const scheduleContainer = document.querySelector('.content-right-msg');
        if (!scheduleContainer) {
            console.error('Element with class "content-right-msg" not found.');
            return;
        }

        scheduleContainer.innerHTML = '';

        if (schedules.length > 0) {
            const maxSchedules = 4;
            const count = Math.min(schedules.length, maxSchedules);

            for (let i = 0; i < count; i++) {
                const schedule = schedules[i];
                const listItem = document.createElement('form');
                listItem.classList.add('memo-form');
                listItem.innerHTML = `
                    <label for="start-date-${i}">시작일:</label>
                    <input type="date" id="start-date-${i}" name="startAt" value="${schedule.startAt}" required>
                    <label for="end-date-${i}">종료일:</label>
                    <input type="date" id="end-date-${i}" name="finishAt" value="${schedule.finishAt}" required>
                    <hr>
                    <div class="memo-section">
                        <label for="memo-title-${i}">메모 제목:</label>
                        <input type="text" id="memo-title-${i}" name="schTitle" value="${schedule.schTitle}" placeholder="메모 제목 입력" required>
                        <label for="memo-content-${i}">메모 내용:</label>
                        <textarea id="memo-content-${i}" name="schContent" placeholder="메모 내용 입력" required>${schedule.schContent}</textarea>
                        <button type="button" data-index="${i}">메모 저장</button>
                    </div>
                `;
                scheduleContainer.appendChild(listItem);
            }
        }

        // 일정이 없는 경우에도 입력 폼 추가
        const newForm = document.createElement('form');
        newForm.classList.add('memo-form');
        newForm.innerHTML = `
            <label for="new-start-date">시작일:</label>
            <input type="date" id="new-start-date" name="startAt" required>
            <label for="new-end-date">종료일:</label>
            <input type="date" id="new-end-date" name="finishAt" required>
            <hr>
            <div class="memo-section">
                <label for="new-memo-title">메모 제목:</label>
                <input type="text" id="new-memo-title" name="schTitle" placeholder="메모 제목 입력" required>
                <label for="new-memo-content">메모 내용:</label>
                <textarea id="new-memo-content" name="schContent" placeholder="메모 내용 입력" required></textarea>
                <button type="button" id="save-new-memo">메모 저장</button>
            </div>
        `;
        scheduleContainer.appendChild(newForm);

        // 저장 버튼 이벤트 리스너 추가
        attachSaveMemoListeners();
    }

    function saveMemo(event) {
        event.preventDefault(); // 기본 폼 제출 방지

        const form = event.target.closest('form'); // 가장 가까운 폼 요소를 가져옵니다
        if (!form) {
            console.error('폼을 찾을 수 없습니다.');
            return;
        }

        const formData = new FormData(form);
        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        fetch('/schedule/check-data', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            },
            body: JSON.stringify(Object.fromEntries(formData.entries()))
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(`Network response was not ok: ${response.status} ${response.statusText} - ${text}`);
                });
            }
            return response.json();
        })
        .then(result => {
            const endpoint = result.hasData ? '/schedule/upload' : '/schedule/save';
            return fetch(endpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [header]: token
                },
                body: JSON.stringify(Object.fromEntries(formData.entries()))
            });
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(`Network response was not ok: ${response.status} ${response.statusText} - ${text}`);
                });
            }
            return response.text();
        })
        .then(result => {
            if (result.includes('success')) {
                alert('메모가 저장되었습니다.');
                initCalendar(today.getFullYear(), today.getMonth()); // 캘린더 재초기화
            } else {
                alert('메모 저장에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error saving memo:', error);
            alert('메모 저장 중 오류가 발생했습니다.');
        });
    }

    function attachSaveMemoListeners() {
        document.querySelectorAll('.memo-form button[type="submit"]').forEach(button => {
            button.addEventListener('click', saveMemo);
        });
    }

    prevBtn.addEventListener('click', function () {
        today.setMonth(today.getMonth() - 1);
        initCalendar(today.getFullYear(), today.getMonth());
        mainDate.textContent = `${today.getFullYear()}년 ${today.getMonth() + 1}월`;
    });

    nextBtn.addEventListener('click', function () {
        today.setMonth(today.getMonth() + 1);
        initCalendar(today.getFullYear(), today.getMonth());
        mainDate.textContent = `${today.getFullYear()}년 ${today.getMonth() + 1}월`;
    });

    initCalendar(today.getFullYear(), today.getMonth());
    attachSaveMemoListeners();
});
