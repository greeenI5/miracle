/**
 * 
 */



function showMessage1() {
    var reqMessage1 = document.getElementById('reqMessage1');
    reqMessage1.style.display = 'flex';
    reqMessage2.style.display = 'none';
    reqMessage3.style.display = 'none';
    reqMessage4.style.display = 'none';
}
function showMessage2() {
    var reqMessage2 = document.getElementById('reqMessage2');
   	reqMessage1.style.display = 'none';
    reqMessage2.style.display = 'flex';
    reqMessage3.style.display = 'none';
    reqMessage4.style.display = 'none';
}
function showMessage3() {
    var reqMessage3 = document.getElementById('reqMessage3');
    reqMessage1.style.display = 'none';
    reqMessage2.style.display = 'none';
    reqMessage3.style.display = 'flex';
    reqMessage4.style.display = 'none';
}
function showMessage4() {
    var reqMessage4 = document.getElementById('reqMessage4');
    reqMessage1.style.display = 'none';
    reqMessage2.style.display = 'none';
    reqMessage3.style.display = 'none';
    reqMessage4.style.display = 'flex';
}

function showMessage5() {
    var scheduleAnswer1 = document.getElementById('scheduleAnswer1');
    scheduleAnswer1.style.display = 'flex';
    scheduleAnswer2.style.display = 'none';
}

function showMessage6() {
    var scheduleAnswer2 = document.getElementById('scheduleAnswer2');
    scheduleAnswer1.style.display = 'none';
    scheduleAnswer2.style.display = 'flex';
}