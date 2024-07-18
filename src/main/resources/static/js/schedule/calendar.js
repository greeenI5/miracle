document.addEventListener('DOMContentLoaded', function () {
  const calendarBody = document.querySelector('.calendar-body');
  const prevBtn = document.querySelector('.prev');
  const nextBtn = document.querySelector('.next');
  const mainDate = document.querySelector('.main-date');
  const contentRightMsg = document.querySelector('.content-right-msg');
  const eventTitle = document.querySelector('.event-title'); // 일정 제목을 표시할 요소

  let today = new Date();
  let selectedstartAt = null; // 선택된 시작일 
  let selectedfinishAt = null; // 선택된 종료일

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
        
        if (!selectedstartAt) {
          selectedstartAt = formattedDate; // 시작일 설정
          this.classList.add('selected-start'); // 시작일에 스타일 추가
        } else if (!selectedfinishAt && selectedDate >= new Date(selectedstartAt)) {
          selectedfinishAt = formattedDate; // 종료일 설정
          this.classList.add('selected-end'); // 종료일에 스타일 추가
          updateEventTitle(selectedstartAt, selectedfinishAt); // 제목 업데이트
          drawMemoBox(selectedstartAt, selectedfinishAt); // 메모 박스 그리기
        } else {
          selectedstartAt = formattedDate; // 새로운 시작일로 설정
          selectedfinishAt = null; // 종료일 초기화
          removeSelection(); // 기존 선택 제거
          this.classList.add('selected-start'); // 새 시작일에 스타일 추가
        }

        // 클릭한 날짜의 data-date 값을 가져와 상세 페이지를 로드
        loadDetailPage(formattedDate);
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
	// 비동기 상세페이지
	function loadDetailPage(date) {
	    fetch(`/schedule/detail?date=${date}`)
	        .then(response => response.text())  // 텍스트 형식으로 응답 받기
	        .then(data => {
	            // 현재 날짜 구하기
	            const today = new Date();
	            const todayDate = `${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}`;
	            
	            // 템플릿 문자열을 사용하여 HTML 생성 (data는 서버에서 받아온 텍스트 데이터로 가정)
	            contentRightMsg.innerHTML = `
	                <div class="selected-date">
	                    <p>선택한 날짜: ${date}</p>
	                </div>
	                
	                <div class="date-selection">
	                    <label for="start-date">시작일:</label>
	                    <input type="date" id="start-date" name="start-date">
	        
	                    <label for="end-date">종료일:</label>
	                    <input type="date" id="end-date" name="end-date">
	                </div>
	                <hr>
	                <div class="memo-section">
	                    <label for="memo-title">메모 제목:</label>
	                    <input type="text" id="memo-title" name="memo-title" placeholder="메모 제목 입력">
	                    
	                    <label for="memo-content">메모 내용:</label>
	                    <textarea id="memo-content" name="memo-content" placeholder="메모 내용 입력"></textarea>
	                    
	                    <button id="save-memo">메모 저장</button>
	                </div>
	            `;
	
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
	                fetch('/schedule/writeSchedule', {
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



  // 메모 박스를 캘린더에 그리는 함수
  function drawMemoBox(startAt, finishAt) {
    const start = new Date(startAt);
    const end = new Date(finishAt);

    const startAtElements = calendarBody.querySelectorAll(`.day[data-date="${start.getFullYear()}-${start.getMonth() + 1}-${start.getDate()}"]`);
    const finishAtElements = calendarBody.querySelectorAll(`.day[data-date="${end.getFullYear()}-${end.getMonth() + 1}-${end.getDate()}"]`);

    if (startAtElements.length > 0 && finishAtElements.length > 0) {
      const startElement = startAtElements[0];
      const endElement = finishAtElements[0];
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
