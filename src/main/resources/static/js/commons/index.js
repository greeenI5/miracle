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

// 페이지가 로드될 때 실행할 함수
window.onload = function() {
    let dateTime = getCurrentDateTime();
    document.getElementById("current-date").textContent = dateTime.date;
    document.getElementById("current-time").textContent = dateTime.time;
};