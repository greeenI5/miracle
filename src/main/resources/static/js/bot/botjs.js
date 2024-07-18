function resetChatbot() {
	$('.message-container1').hide();
	$('.contact-buttons').hide();
	}

/*연락처알려줘 클릭시 부서선택나옴*/
	window.showContact = function() {
		$('.message-container1').css('display', 'flex');
		$('.contact-buttons').css('display', 'flex');
	}
/*일정 알려줘 클릭시 일정종류선택*/
	window.showSchedule = function() {
		$('.message-container2').css('display', 'flex');
		$('.schedules-buttons').css('display', 'flex');
	}
/*공지사항 알려줘*/
		window.showNotice = function() {
		$('.message-container3').css('display', 'flex');
		
	}

	/*부서 하나씩만 선택*/
	window.showMessage1 = function() {
		$('#reqMessage1').css('display', 'flex');
		$('#reqMessage2, #reqMessage3, #reqMessage4').hide();
	}

	window.showMessage2 = function() {
		$('#reqMessage2').css('display', 'flex');
		$('#reqMessage1, #reqMessage3, #reqMessage4').hide();
	}

	window.showMessage3 = function() {
		$('#reqMessage3').css('display', 'flex');
		$('#reqMessage1, #reqMessage2, #reqMessage4').hide();
	}

	window.showMessage4 = function() {
		$('#reqMessage4').css('display', 'flex');
		$('#reqMessage1, #reqMessage2, #reqMessage3').hide();
	}

	window.showMessage5 = function() {
		$('#scheduleAnswer1').css('display', 'flex');
		$('#scheduleAnswer2').hide();
	}

	window.showMessage6 = function() {
		$('#scheduleAnswer2').css('display', 'flex');
		$('#scheduleAnswer1').hide();
	}

/*var socket = new SockJS('/ws');
var stompClient = Stomp.over(socket);*/


function btnMsgSendClicked() {
    // 입력 필드의 값을 가져오기
    let question = document.getElementById('question').value;

    // 메시지를 표시할 요소에 값 설정
    let messagePart = document.getElementById('messageDisplay');
    messageDisplay.innerText = question;

    // 입력 필드를 비웁니다
    document.getElementById('question').value = '';
}




$(document).ready(function() {
    $('.chatbot-toggler').on("click", function() {
        $('body').toggleClass("show-chatbot");
    });
});