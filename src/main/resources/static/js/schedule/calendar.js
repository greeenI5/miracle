document.addEventListener('DOMContentLoaded', function () {
  const calendarBody = document.querySelector('.calendar-body');
  const prevBtn = document.querySelector('.prev');
  const nextBtn = document.querySelector('.next');
  const mainDate = document.querySelector('.main-date');
  const contentRightMsg = document.querySelector('.content-right-msg');
  const eventTitle = document.querySelector('.event-title'); // 일정 제목을 표시할 요소

  let today = new Date();
  let selectedStartDate = null; // 선택된 시작일 
  let selectedEndDate = null; // 선택된 종료일

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
        
        if (!selectedStartDate) {
          selectedStartDate = formattedDate; // 시작일 설정
          this.classList.add('selected-start'); // 시작일에 스타일 추가
        } else if (!selectedEndDate && selectedDate >= new Date(selectedStartDate)) {
          selectedEndDate = formattedDate; // 종료일 설정
          this.classList.add('selected-end'); // 종료일에 스타일 추가
          updateEventTitle(selectedStartDate, selectedEndDate); // 제목 업데이트
          drawMemoBox(selectedStartDate, selectedEndDate); // 메모 박스 그리기
        } else {
          selectedStartDate = formattedDate; // 새로운 시작일로 설정
          selectedEndDate = null; // 종료일 초기화
          removeSelection(); // 기존 선택 제거
          this.classList.add('selected-start'); // 새 시작일에 스타일 추가
        }

        // 클릭한 날짜의 data-date 값을 가져와 상세 페이지를 로드
        loadDetailPage(formattedDate);
      });
    });
  }

  // 일정 제목 업데이트 함수
  function updateEventTitle(startDate, endDate) {
    eventTitle.innerHTML = `<p>일정 제목: ${startDate} ~ ${endDate}</p>`;
  }

  // 선택 상태 제거 함수
  function removeSelection() {
    const dayElements = calendarBody.querySelectorAll('.day');
    dayElements.forEach(day => {
      day.classList.remove('selected-start', 'selected-end');
    });
  }

  // 비동기로 상세 페이지를 로드하는 함수
  function loadDetailPage(date) {
    fetch(`/schedule/detail?date=${date}`)
      .then(response => response.json())  // JSON 형식으로 응답 받기
      .then(data => {
        // 현재 날짜 구하기
        const today = new Date();
        const todayDate = `${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}`;
        
        // 템플릿 문자열을 사용하여 HTML 생성
        contentRightMsg.innerHTML = `
          <div class="selected-date">
            <p>선택한 날짜: ${date}</p>
          </div>
          
          <!-- 시작일과 종료일 선택 -->
          <div class="date-selection">
            <label for="start-date">시작일:</label>
            <input type="date" id="start-date" name="start-date" value="${data.startDate || ''}">
        
            <label for="end-date">종료일:</label>
            <input type="date" id="end-date" name="end-date" value="${data.endDate || ''}">
          </div>
          <hr></hr>
          <div class="memo-section">
            <label for="memo-title">메모 제목:</label>
            <input type="text" id="memo-title" name="memo-title" placeholder="메모 제목 입력" value="${data.title || ''}">
            
            <label for="memo-content">메모 내용:</label>
            <textarea id="memo-content" name="memo-content" placeholder="메모 내용 입력">${data.content || ''}</textarea>
            
            <button id="save-memo">메모 저장</button>
          </div>
        `;

        // 저장 버튼 클릭 이벤트 처리
        document.getElementById('save-memo').addEventListener('click', function () {
          const title = document.getElementById('memo-title').value;
          const content = document.getElementById('memo-content').value;
          const startDate = document.getElementById('start-date').value;
          const endDate = document.getElementById('end-date').value;

          // 메모 저장 API 호출 (서버로 POST 요청)
          fetch('/schedule/saveMemo', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({ date, title, content, startDate, endDate })
          })
          .then(response => response.json())
          .then(result => {
            if (result.success) {
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

  // 메모 박스를 캘린더에 그리는 함수
  function drawMemoBox(startDate, endDate) {
    const start = new Date(startDate);
    const end = new Date(endDate);

    const startDateElements = calendarBody.querySelectorAll(`.day[data-date="${start.getFullYear()}-${start.getMonth() + 1}-${start.getDate()}"]`);
    const endDateElements = calendarBody.querySelectorAll(`.day[data-date="${end.getFullYear()}-${end.getMonth() + 1}-${end.getDate()}"]`);

    if (startDateElements.length > 0 && endDateElements.length > 0) {
      const startElement = startDateElements[0];
      const endElement = endDateElements[0];
      const startIndex = Array.from(calendarBody.children).indexOf(startElement);
      const endIndex = Array.from(calendarBody.children).indexOf(endElement);

      // 메모 박스 스타일 추가
      for (let i = startIndex; i <= endIndex; i++) {
        const dayElement = calendarBody.children[i];
        dayElement.classList.add('memo-box');
      }
    }
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
});
