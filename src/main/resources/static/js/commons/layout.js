function updateRemainingTime() {
    // 남은 시간 요소를 가져옴
    const remainingTimeElement = document.getElementById('remaining-time');
    // 현재 남은 시간을 시:분:초 형식으로 나눔
    let timeParts = remainingTimeElement.innerText.split(':');
    let hours = parseInt(timeParts[0]);
    let minutes = parseInt(timeParts[1]);
    let seconds = parseInt(timeParts[2]);

    // 초 단위를 1 감소
    if (seconds > 0) {
        seconds -= 1;
    } else {
        // 초가 0일 경우 분 단위를 1 감소하고 초를 59로 설정
        if (minutes > 0) {
            minutes -= 1;
            seconds = 59;
        } else {
            // 분도 0일 경우 시간 단위를 1 감소하고 분과 초를 59로 설정
            if (hours > 0) {
                hours -= 1;
                minutes = 59;
                seconds = 59;
            } else {
                // 시간이 모두 0이 되었을 때 타이머 중지
                clearInterval(interval);
            }
        }
    }

    // 시:분:초 형식으로 남은 시간 업데이트
    remainingTimeElement.innerText = 
        String(hours).padStart(2, '0') + ':' +
        String(minutes).padStart(2, '0') + ':' +
        String(seconds).padStart(2, '0');
}

// 1초마다 남은 시간을 업데이트하도록 설정
const interval = setInterval(updateRemainingTime, 1000);