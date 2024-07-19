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
        fetchContactInfo();
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
           
            var msgObj = JSON.parse(message.body);
            displayMessage(msgObj.answer);
        });

  
    // 서버로 메시지 보내기 (기본 메시지 전송 예시)
        var data = {
            key: new Date().getTime(),
            content: 'question',
            name: "guest"
        };
        stompClient.send("/bot/hello", {}, JSON.stringify(data));
    });

}


// 메시지 전송 버튼(btnMsgSend) 클릭 시
function btnMsgSendClicked() {
    // 입력 필드의 값을 가져와서 처리
    let question = document.getElementById('question').value;
    if (question.trim() === "") {
        return; // 입력 필드가 비어 있으면 아무 작업도 하지 않음
    }
    displayUserMessage(question);

    // 서버로 질문 전송
    stompClient.send("/bot/hello", {}, JSON.stringify({ question: question }));
    document.getElementById('question').value = '';
}

// 사용자의 메시지를 화면에 출력
function displayUserMessage(message) {
    let messageContainer = document.getElementById('messageDisplay');
    let newMessage = document.createElement('div');
    newMessage.classList.add('user-message');
    newMessage.innerText = message;
    messageContainer.appendChild(newMessage);
    messageContainer.scrollTop = messageContainer.scrollHeight; // 스크롤을 제일 아래로 이동
}

// 봇의 메시지를 화면에 출력
function displayMessage(message) {
    let messageContainer = document.getElementById('messageDisplay');
    let newMessage = document.createElement('div');
    newMessage.classList.add('bot-message');
    newMessage.innerText = message;
    messageContainer.appendChild(newMessage);
    messageContainer.scrollTop = messageContainer.scrollHeight; // 스크롤을 제일 아래로 이동
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

// 서버에서 연락처 정보를 가져와서 화면에 출력하는 함수
function fetchContactInfo() {
    $.ajax({
        url: "/api/chatbots",
        method: "GET",
        success: function(data) {
            displayContactInfo(data);
        },
        error: function(error) {
            console.error("Error fetching contact info", error);
        }
    });
}

// 연락처 정보를 화면에 출력하는 함수
function displayContactInfo(data) {
    let container = document.querySelector('.message-container1');
    container.innerHTML = ''; // 기존 내용을 지우기
    data.forEach(function(contact) {
        let contactDiv = document.createElement('div');
        contactDiv.classList.add('contact-info');
        contactDiv.innerText = `Name: ${contact.name}, Phone: ${contact.phoneNumber}`;
        container.appendChild(contactDiv);
    });
    container.style.display = 'flex';
}
