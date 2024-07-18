// 웹소켓 클라이언트 전역 변수 선언
var stompClient = null;

// 문서가 로드된 후 실행되는 jQuery 코드
$(function() {
    // 토글 버튼(.chatbot-toggler) 클릭 시
	$('.chatbot-toggler').on("click", function() {
    // body 요소에 show-chatbot 클래스를 토글
    $('body').toggleClass("show-chatbot");

    // show-chatbot 클래스 유무에 따라 소켓 연결 및 해제
        if ($('body').hasClass("show-chatbot")) {
            connectSocket(); // 소켓 연결 함수 호출
        } else {
            disconnectSocket(); // 소켓 연결 해제 함수 호출
        }
    });

    // 연락처 알려줘 버튼 클릭 시
    $('#showContact').on("click", function() {
        $('.message-container1').css('display', 'flex');
    });

    // 일정 알려줘 버튼 클릭 시
    $('#showSchedule').on("click", function() {
        $('.message-container2').css('display', 'flex');
        $('.schedules-buttons').css('display', 'flex');
    });

    // 공지사항 버튼 클릭 시
    $('#showNotice').on("click", function() {
        $('.message-container3').css('display', 'flex');
    });
});

// 웹소켓 연결 함수
function connectSocket() {
    var socket = new SockJS('/ws-i5-bot');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);

        // /topic/messages 구독하여 메시지 수신
        stompClient.subscribe('/topic/messages', function(message) {
            console.log("Received: " + message.body);
        });

        // 서버로 메시지 보내기
        sendMessage();
    });
}

// 웹소켓 연결 해제 함수
function disconnectSocket() {
    if (stompClient !== null) {
        stompClient.disconnect(function() {
            console.log("Disconnected");
        });
    } else {
        console.log("Socket is not connected.");
    }
}

// 메시지 전송 함수
function sendMessage() {
	
/*			var data={
			key: key,
			content: "hello",
			name: "guest"//principa.getName 이름의 값으로 넣으면 될듯...
		}*/
	
    if (stompClient !== null) {
        stompClient.send("/bot/hello", {}, JSON.stringify({'name': 'Hello WebSocket!'}));
        console.log("Message sent");
    } else {
        console.log("Socket is not connected.");
    }
}

// 메시지 전송 버튼(btnMsgSend) 클릭 시
function btnMsgSendClicked() {
    // 입력 필드의 값을 가져와서 처리
    let question = document.getElementById('question').value;
    let messagePart = document.getElementById('messageDisplay');
    messageDisplay.innerText = question;
    document.getElementById('question').value = '';
}

function displayMessage(message) {
    // 메시지를 표시할 요소를 찾습니다.
    let messageContainer = document.getElementById('serverBotMessage');

    // 새로운 메시지 요소를 생성합니다.
    let newMessage = document.createElement('div');
    newMessage.classList.add('bot-message');
    newMessage.innerText = message;

    // 메시지 요소를 메시지 컨테이너에 추가합니다.
    messageContainer.appendChild(newMessage);
}

