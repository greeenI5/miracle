document.addEventListener('DOMContentLoaded', function () {
    // 페이지 로드가 완료된 후 실행되어야 할 코드
    window.onload = function () {
        const calendarBody = document.querySelector('.calendar-body');
        const prevBtn = document.querySelector('.prev');
        const nextBtn = document.querySelector('.next');
        const mainDate = document.querySelector('.main-date');
        const contentRightMsg = document.querySelector('.content-right-msg');
        const eventTitle = document.querySelector('.event-title'); // 일정 제목을 표시할 요소

        let today = new Date();
        let selectedStartAt = null; // 선택된 시작일 
        let selectedFinishAt = null; // 선택된 종료일

        // 메인 헤더 날짜 업데이트
        mainDate.textContent = `${today.getFullYear()}년 ${today.getMonth() + 1}월`;

        // 캘린더 초기화 함수
        function initCalendar(year, month) {
            calendarBody.innerHTML = ''; // 기존 캘린더 내용 초기화

            // 해당 월의 첫 날과 마지막 날 구하기
            const firstDayOfMonth = new Date(year, month, 1).getDay(); // 0: 일요일, ..., 6: 토요일
            const lastDateOfMonth = new Date(year, month + 1, 0).getDate();

            // 현재 월의 날짜를 캘린더에 추가
            for (let i = 0; i < lastDateOfMonth; i++) {
                const day = i + 1;
                const dayElement = document.createElement('div');
                dayElement.classList.add('day');
                dayElement.textContent = day;
                dayElement.dataset.date = `${year}-${month + 1}-${day}`;
                calendarBody.appendChild(dayElement);
            }

            // 이전 달의 마지막 일들 채우기
            for (let i = 0; i < firstDayOfMonth; i++) {
                const prevMonthLastDate = new Date(year, month, 0).getDate();
                const dayElement = document.createElement('div');
                dayElement.classList.add('day', 'prev-month');
                dayElement.textContent = prevMonthLastDate - firstDayOfMonth + i + 1;
                dayElement.dataset.date = `${year}-${month}-${prevMonthLastDate - firstDayOfMonth + i + 1}`;
                calendarBody.insertBefore(dayElement, calendarBody.firstChild);
            }

            // 다음 달의 시작 일들 채우기 (42칸 기준)
            const totalDays = calendarBody.querySelectorAll('.day').length;
            const nextMonthDays = 42 - totalDays;
            for (let i = 0; i < nextMonthDays; i++) {
                const dayElement = document.createElement('div');
                dayElement.classList.add('day', 'next-month');
                dayElement.textContent = i + 1;
                dayElement.dataset.date = `${year}-${month + 2}-${i + 1}`;
                calendarBody.appendChild(dayElement);
            }

            // 현재 날짜 강조 표시
            const todayElement = calendarBody.querySelector(`[data-date="${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}"]`);
            if (todayElement) {
                todayElement.classList.add('active');
            }

            // 각 날짜 클릭 이벤트 추가
            const dayElements = calendarBody.querySelectorAll('.day');
            dayElements.forEach(day => {
                day.addEventListener('click', function () {
                    const selectedDate = new Date(this.dataset.date);
                    const formattedDate = `${selectedDate.getFullYear()}-${selectedDate.getMonth() + 1}-${selectedDate.getDate()}`;

                    if (!selectedStartAt) {
                        selectedStartAt = formattedDate; // 시작일 설정
                        removeSelection(); // 기존 선택 제거
                        this.classList.add('selected-start'); // 시작일에 스타일 추가
                    } else if (!selectedFinishAt && selectedDate >= new Date(selectedStartAt)) {
                        selectedFinishAt = formattedDate; // 종료일 설정
                        this.classList.add('selected-end'); // 종료일에 스타일 추가
                        updateEventTitle(selectedStartAt, selectedFinishAt); // 제목 업데이트
                        loadDetailPage(selectedStartAt); // 상세 페이지 로드
                    } else {
                        selectedStartAt = formattedDate; // 새로운 시작일로 설정
                        selectedFinishAt = null; // 종료일 초기화
                        removeSelection(); // 기존 선택 제거
                        this.classList.add('selected-start'); // 새 시작일에 스타일 추가
                    }
                });
            });
        }

        // 일정 제목 업데이트 함수
        function updateEventTitle(startAt, finishAt) {
            eventTitle.innerHTML = `<p>일정 제목: ${startAt} ~ ${finishAt}</p>`;
        }

        // 선택 상태 제거 함수
        function removeSelection() {
            const dayElements = calendarBody.querySelectorAll('.day');
            dayElements.forEach(day => {
                day.classList.remove('selected-start', 'selected-end');
            });
        }

        // 비동기 상세페이지 로드 함수
        function loadDetailPage(date) {
            fetch(`/schedule/detail?date=${date}`)
                .then(response => response.text())  // 텍스트 형식으로 응답 받기
                .then(data => {
                    contentRightMsg.innerHTML = data;

                    // 저장 버튼 클릭 이벤트 처리
                    document.getElementById('save-memo').addEventListener('click', function () {
                        const startAt = document.getElementById('start-date').value;
                        const finishAt = document.getElementById('end-date').value;
                        const schTitle = document.getElementById('memo-title').value;

                        // 필수 필드 확인
                        if (!startAt || !finishAt || !schTitle) {
                            alert('시작일, 종료일, 메모 제목은 필수입니다.');
                            return;
                        }

                        const formData = new FormData();
                        formData.append('schTitle', schTitle);
                        formData.append('schContent', document.getElementById('memo-content').value);
                        formData.append('startAt', startAt);
                        formData.append('finishAt', finishAt);

                        // 메모 저장 API 호출 (서버로 POST 요청)
                        fetch('/schedule/save', {
                            method: 'POST',
                            body: formData // FormData 객체를 요청 본문에 포함
                        })
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error('Network response was not ok');
                                }
                                return response.text(); // 필요에 따라 응답 형식 변경
                            })
                            .then(result => {
                                if (result.includes('success')) { // 서버의 응답이 "success"를 포함하는지 확인
                                    alert('메모가 저장되었습니다.');
                                    // 메모 저장 후 캘린더 업데이트
                                    initCalendar(today.getFullYear(), today.getMonth());
                                } else {
                                    alert('메모 저장에 실패했습니다.');
                                }
                            })
                            .catch(error => {
                                console.error('Error saving memo:', error);
                                alert('메모 저장 중 오류가 발생했습니다.');
                            });
                    });
                })
                .catch(error => {
                    console.error('Error loading detail page:', error);
                    contentRightMsg.innerHTML = '<p>상세 정보를 로드하는 중 오류가 발생했습니다.</p>';
                });
        }

        // 이전 달 버튼 클릭 이벤트
        prevBtn.addEventListener('click', function () {
            today.setMonth(today.getMonth() - 1);
            mainDate.textContent = `${today.getFullYear()}년 ${today.getMonth() + 1}월`;
            initCalendar(today.getFullYear(), today.getMonth());
        });

        // 다음 달 버튼 클릭 이벤트
        nextBtn.addEventListener('click', function () {
            today.setMonth(today.getMonth() + 1);
            mainDate.textContent = `${today.getFullYear()}년 ${today.getMonth() + 1}월`;
            initCalendar(today.getFullYear(), today.getMonth());
        });

        // 페이지 로드 시 캘린더 초기화
        initCalendar(today.getFullYear(), today.getMonth());

        // 페이지 로드 시 오늘 날짜의 일정 로드
        const todayFormatted = `${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}`;
        loadDetailPage(todayFormatted);
    };
});
