
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
		$('.message-container2').css('display', 'flex');
		
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

	

$(document).ready(function() {
    $('.chatbot-toggler').on("click", function() {
        $('body').toggleClass("show-chatbot");
    });
});