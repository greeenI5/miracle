// 페이지가 로드될 때 실행할 함수
window.onload = function() {
    let dateTime = getCurrentDateTime();
    document.getElementById("current-date").textContent = dateTime.date;
    document.getElementById("current-time").textContent = dateTime.time;
};

// 현재 날짜와 시간을 가져오는 함수
function getCurrentDateTime() {
    let currentDateTime = new Date();

    // 날짜 포맷팅
    let year = currentDateTime.getFullYear();
    let month = ('0' + (currentDateTime.getMonth() + 1)).slice(-2);
    let day = ('0' + currentDateTime.getDate()).slice(-2);
    let dayOfWeek = ['일', '월', '화', '수', '목', '금', '토'][currentDateTime.getDay()];
    let formattedDate = `${year}-${month}-${day} (${dayOfWeek})`;

    // 시간 포맷팅
    let hours = ('0' + currentDateTime.getHours()).slice(-2);
    let minutes = ('0' + currentDateTime.getMinutes()).slice(-2);
    let seconds = ('0' + currentDateTime.getSeconds()).slice(-2);
    let formattedTime = `${hours}:${minutes}:${seconds}`;

    return {
        date: formattedDate,
        time: formattedTime
    };
}

// 현재 시간을 가져오고 포맷팅하여 특정 요소에 출력하는 함수
function updateCurrentTime() {
    let currentDateTime = new Date();

    // 시간 포맷팅
    let hours = ('0' + currentDateTime.getHours()).slice(-2);
    let minutes = ('0' + currentDateTime.getMinutes()).slice(-2);
    let seconds = ('0' + currentDateTime.getSeconds()).slice(-2);
    let formattedTime = `${hours}:${minutes}:${seconds}`;

    // HTML 업데이트
    document.getElementById("current-time").textContent = formattedTime;
}

updateCurrentTime(); //페이지가 처음 로드 될때
setInterval(updateCurrentTime, 1000); // 1초마다 시간 업데이트


//////////달력
  let currentDate = new Date();
  let currentMonth = currentDate.getMonth();
  let currentYear = currentDate.getFullYear();
  let selectedDate = currentDate.getDate(); // 기본적으로 오늘 날짜를 선택함

  let monthNames = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];
  let monthEnglishNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
  
  function updateCalendar(month, year) {
    // Update current month/year display
    document.getElementById('currentMonthYear').textContent = year + '년 ' + monthNames[month];

    // Calculate days in the month
    let daysInMonth = new Date(year, month + 1, 0).getDate();

    // Generate calendar body
    let dateCounter = 1;
    let html = '';
    for (let i = 0; i < 6; i++) { // Maximum 6 weeks (6 rows)
      html += '<tr>';
      for (let j = 0; j < 7; j++) { // 7 days (7 columns)
        if (dateCounter <= daysInMonth) {
          if (i === 0 && j < new Date(year, month, 1).getDay()) {
            html += '<td></td>'; // Empty cells before the first day of the month
          } else {
            if (dateCounter === selectedDate && month === currentMonth && year === currentYear) {
              html += '<td class="selected" onclick="selectDate(' + dateCounter + ')">' + dateCounter + '</td>';
            } else {
              html += '<td class="hover-date" onclick="selectDate(' + dateCounter + ')">' + dateCounter + '</td>';
            }
            dateCounter++;
          }
        } else {
          html += '<td></td>'; // Empty cells after the last day of the month
        }
      }
      html += '</tr>';
    }

    document.getElementById('calendarBody').innerHTML = html; // Insert generated HTML into calendar body
  }

  function previousMonth() {
    currentMonth--;
    if (currentMonth < 0) {
      currentMonth = 11;
      currentYear--;
    }
    updateCalendar(currentMonth, currentYear);
  }

  function nextMonth() {
    currentMonth++;
    if (currentMonth > 11) {
      currentMonth = 0;
      currentYear++;
    }
    updateCalendar(currentMonth, currentYear);
  }

  function selectDate(date) {
    selectedDate = date;
    updateCalendar(currentMonth, currentYear);
    document.getElementById('selectedMonth').innerHTML = monthEnglishNames[currentMonth] +' ';
    document.getElementById('selectedDay').innerHTML = selectedDate;
  }

  // Initialize calendar on page load
  updateCalendar(currentMonth, currentYear);
  document.getElementById('selectedMonth').innerHTML = monthEnglishNames[currentMonth] +' ';
  document.getElementById('selectedDay').innerHTML = selectedDate;
  

////일정 체크
$(document).ready(function() {
    loadChecklist();
});
// 체크리스트 상태를 저장하는 함수
function saveChecklist() {
    const checkItems = document.querySelectorAll('.check-item');
    const checklistState = {};
    // 각 체크 항목의 상태를 객체에 저장
    checkItems.forEach(item => {
        checklistState[item.dataset.label] = item.checked;
    });
    // 체크리스트 상태를 로컬 스토리지에 JSON 형태로 저장
    localStorage.setItem('checklistState', JSON.stringify(checklistState));
    // UI 업데이트
    updateChecklistUI();
}
// 체크리스트를 로드하는 함수
function loadChecklist() {
    // 로컬 스토리지에서 체크리스트 상태를 가져옴 (없으면 빈 객체 사용)
    const checklistState = JSON.parse(localStorage.getItem('checklistState')) || {};
    // 모든 체크 항목에 대해 상태 적용
    document.querySelectorAll('.check-item').forEach(item => {
        if (checklistState[item.dataset.label]) {
            item.checked = true;
            item.parentElement.classList.add('checked');
        } else {
            item.checked = false;
            item.parentElement.classList.remove('checked');
        }
    });
}
// 체크리스트 UI를 업데이트하는 함수
function updateChecklistUI() {
    // 모든 체크 항목에 대해 UI 상태 업데이트
    document.querySelectorAll('.check-item').forEach(item => {
        if (item.checked) {
            item.parentElement.classList.add('checked');
        } else {
            item.parentElement.classList.remove('checked');
        }
    });
}

  
//출근하기, 퇴근하기 버튼
function activateButton(button) {
    var startButton = document.getElementById('start-work-button');
    var endButton = document.getElementById('end-work-button');

    if (button === startButton) {
        if (confirm('정말 출근하시겠습니까?')) {
            startButton.style.color = 'var(--main-grey)';
            startButton.style.border = '2px solid var(--main-grey)'
            endButton.style.color = 'var(--main-navy)';
            endButton.style.border = '2px solid var(--main-navy)'
            startButton.disabled = true;
            endButton.disabled = false;
        }
    } else {
        if (confirm('정말 퇴근하시겠습니까?')) {
            startButton.style.color = 'var(--main-navy)';
            startButton.style.border = '2px solid var(--main-navy)'
            endButton.style.color = 'var(--main-grey)';
            endButton.style.border = '2px solid var(--main-grey)'
            startButton.disabled = true;
            endButton.disabled = true;
        }
    }
}


// 출퇴근 시간 기록
let startTime = null;
let endTime = null;

function recordStartTime() {
    startTime = new Date();
    document.getElementById('start-time').textContent = formatTime(startTime);
}
function recordEndTime() {
    if (startTime === null) { // 출근 시간이 기록되지 않았을 경우 경고 메시지 출력
        alert('출근 시간을 먼저 기록해주세요!');
        return;
    }
    endTime = new Date();
    document.getElementById('end-time').textContent = formatTime(endTime);

    // 근무 시간 계산
    const totalTime = calculateTimeDifference(startTime, endTime); // 출근 시간과 퇴근 시간의 차이 계산
    document.getElementById('total-time').textContent = formatTimeDuration(totalTime); 
}

// 시간 차이 계산 함수
function calculateTimeDifference(start, end) {
    const diffInMilliseconds = end - start; // 퇴근 시간과 출근 시간의 차이(ms) 계산
    const diffInSeconds = Math.floor(diffInMilliseconds / 1000); // 차이를 초로 변환
    const hours = Math.floor(diffInSeconds / 3600); // 차이를 시간으로 변환
    const minutes = Math.floor((diffInSeconds % 3600) / 60); // 남은 초를 분으로 변환
    const seconds = diffInSeconds % 60; // 남은 초
    return { hours, minutes, seconds }; // 차이를 객체로 반환
}
// 시간 형식을 HH:mm:ss 형식으로 포맷팅하는 함수
function formatTime(date) {
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const seconds = date.getSeconds().toString().padStart(2, '0');
    return `${hours}:${minutes}:${seconds}`;
}
function formatTimeDuration(duration) {
    const hours = duration.hours; // 시간
    const minutes = duration.minutes; // 분
    const seconds = duration.seconds; // 초
    return `${hours}h ${minutes}m ${seconds+1}s`; // 시간, 분, 초 형식의 문자열 반환
}
