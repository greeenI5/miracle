document.addEventListener('DOMContentLoaded', function () {
    const calendarBody = document.querySelector('.calendar-body');
    const prevBtn = document.querySelector('.prev');
    const nextBtn = document.querySelector('.next');
    const mainDate = document.querySelector('.main-date');
    const contentRightMsg = document.querySelector('.content-right-msg');
    const eventTitle = document.querySelector('.event-title');

    let today = new Date();
    let selectedStartAt = null;
    let selectedFinishAt = null;

    mainDate.textContent = `${today.getFullYear()}년 ${today.getMonth() + 1}월`;

    function initCalendar(year, month) {
        calendarBody.innerHTML = '';
        const firstDayOfMonth = new Date(year, month, 1).getDay();
        const lastDateOfMonth = new Date(year, month + 1, 0).getDate();

        for (let i = 0; i < lastDateOfMonth; i++) {
            const day = i + 1;
            const dayElement = document.createElement('div');
            dayElement.classList.add('day');
            dayElement.textContent = day;
            dayElement.dataset.date = `${year}-${month + 1}-${day}`;
            calendarBody.appendChild(dayElement);
        }

        for (let i = 0; i < firstDayOfMonth; i++) {
            const prevMonthLastDate = new Date(year, month, 0).getDate();
            const dayElement = document.createElement('div');
            dayElement.classList.add('day', 'prev-month');
            dayElement.textContent = prevMonthLastDate - firstDayOfMonth + i + 1;
            dayElement.dataset.date = `${year}-${month}-${prevMonthLastDate - firstDayOfMonth + i + 1}`;
            calendarBody.insertBefore(dayElement, calendarBody.firstChild);
        }

        const totalDays = calendarBody.querySelectorAll('.day').length;
        const nextMonthDays = 42 - totalDays;
        for (let i = 0; i < nextMonthDays; i++) {
            const dayElement = document.createElement('div');
            dayElement.classList.add('day', 'next-month');
            dayElement.textContent = i + 1;
            dayElement.dataset.date = `${year}-${month + 2}-${i + 1}`;
            calendarBody.appendChild(dayElement);
        }

        const todayElement = calendarBody.querySelector(`[data-date="${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}"]`);
        if (todayElement) {
            todayElement.classList.add('active');
        }

        const dayElements = calendarBody.querySelectorAll('.day');
        dayElements.forEach(day => {
            day.addEventListener('click', function () {
                const selectedDate = new Date(this.dataset.date);
                const formattedDate = `${selectedDate.getFullYear()}-${selectedDate.getMonth() + 1}-${selectedDate.getDate()}`;

                if (!selectedStartAt) {
                    selectedStartAt = formattedDate;
                    removeSelection();
                    this.classList.add('selected-start');
                } else if (!selectedFinishAt && selectedDate >= new Date(selectedStartAt)) {
                    selectedFinishAt = formattedDate;
                    this.classList.add('selected-end');
                    updateEventTitle(selectedStartAt, selectedFinishAt);
                    loadDetailPage(selectedStartAt);
                } else {
                    selectedStartAt = formattedDate;
                    selectedFinishAt = null;
                    removeSelection();
                    this.classList.add('selected-start');
                }
            });
        });
    }

    function updateEventTitle(startAt, finishAt) {
        eventTitle.innerHTML = `<p>일정 제목: ${startAt} ~ ${finishAt}</p>`;
    }

    function removeSelection() {
        const dayElements = calendarBody.querySelectorAll('.day');
        dayElements.forEach(day => {
            day.classList.remove('selected-start', 'selected-end');
        });
    }

    function loadDetailPage(date) {
        fetch(`/schedule/detail?date=${date}`)
            .then(response => response.text())
            .then(data => {
                contentRightMsg.innerHTML = data;

                document.getElementById('save-memo').addEventListener('click', saveMemo);
            })
            .catch(error => {
                console.error('Error loading detail page:', error);
                contentRightMsg.innerHTML = '<p>상세 정보를 로드하는 중 오류가 발생했습니다.</p>';
            });
    }

    function saveMemo() {
        const startAt = document.getElementById('start-date').value;
        const finishAt = document.getElementById('end-date').value;
        const schTitle = document.getElementById('memo-title').value;

        if (!startAt || !finishAt || !schTitle) {
            alert('시작일, 종료일, 메모 제목은 필수입니다.');
            return;
        }

        const formData = new FormData();
        formData.append('schTitle', schTitle);
        formData.append('schContent', document.getElementById('memo-content').value);
        formData.append('startAt', startAt);
        formData.append('finishAt', finishAt);

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

        return false;
    }

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

    initCalendar(today.getFullYear(), today.getMonth());
    const todayFormatted = `${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}`;
    loadDetailPage(todayFormatted);
});
