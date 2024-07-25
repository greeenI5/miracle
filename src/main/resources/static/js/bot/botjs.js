// 웹소켓 클라이언트 전역 변수 선언
const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

var stompClient = null;
var key;
var now = new Date();
var time = formatTime(now);
let initialMessageShown = false; // 초기 메시지 1번만 출력되는 플래그

function formatTime(now) {
    var ampm = (now.getHours() > 11) ? "오후" : "오전";
    var hour = now.getHours() % 12;
    if (hour == 0) hour = 12;
    var minute = String(now.getMinutes()).padStart(2, '0');
    return `${ampm} ${hour}:${minute}`;
}

function formatDate(now) {
    const year = now.getFullYear(); // const 상수값, 수정불가
    const month = now.getMonth() + 1;
    const date = now.getDate();
    const dayOfWeek = now.getDay();
    const days = ["일", "월", "화", "수", "목", "금", "토"];
    return `${year}년 ${month}월 ${date}일 ${days[dayOfWeek]}요일`;
}

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
            clearChat(); // 채팅 내용을 초기화
        }
    });
});

// 웹소켓 연결 함수
function connectSocket() {
    stompClient = Stomp.over(new SockJS('/ws-i5-bot'));
    stompClient.connect({}, (frame) => {
        key = new Date().getTime();
        // 구독 설정
        stompClient.subscribe(`/topic/bot/${key}`, (answer) => {
            var msgObj = answer.body;
            var now = new Date();
            var time = formatTime(now);
            var date = formatDate(now);
            if (!initialMessageShown) {
                var tag = `
                        <div class="date flex center">${date}</div>
                        <div class="msg bot flex">
                            <div id="b-icon"></div>
                            <div class="message">
                                <div class="bot-message part">
                                    <p>${msgObj}</p>
                                </div>
                                <div class="time">${time}</div>
                                <div class="button-container">
                                    <button class="bot-category" id="showContact">연락처 알려줘</button>
                                    <button class="bot-category" id="showSchedule">일정 알려줘</button>
                                    <button class="bot-category" id="showNotice">공지사항 알려줘</button>
                                </div>
                            </div>
                        </div>
                        <div id="messageDisplay"></div>
                        `;
                sendMessage(tag);
                initialMessageShown = true; // 메시지가 한 번만 출력되도록 설정
            }
        });

        // 자바스크립트 오브젝트 객체 - JSON
        var data = {
            key: key,
            content: "hello",
            name: "" // principa.getName 이름의 값으로 넣으면 될듯...
        };
        // 접속하자마자 연결 시도
        stompClient.send("/bot/hello", {}, JSON.stringify(data));
    });
}

// 연락처 알려줘 버튼 클릭 시
$(document).on("click", "#showContact", function() {
    fetchContactInfo();
});

// 일정 알려줘 버튼 클릭 시
$(document).on("click", "#showSchedule", function() {
    fetchScheduleInfo();
});

// 공지사항 알려줘 버튼 클릭 시
$(document).on("click", "#showNotice", function() {
    fetchNoticeInfo();
});

// 메시지 전송 버튼(btnMsgSend) 클릭 시
function btnMsgSendClicked() {
    // 입력 필드의 값을 가져와서 처리
    let question = document.getElementById('question').value;
    if (question.trim() === "") {
        return; // 입력 필드가 비어 있으면 아무 작업도 하지 않음
    }
    displayUserMessage(question);
    //구독
	        stompClient.subscribe(`/topic/bot/${key}`, (content) => {
            var msgObj = content.body;
            var tag = `
                  <div class="msg bot flex">
                            <div id="b-icon"></div>
                            <div class="message">
                                <div class="bot-message part">
                                    <p>${msgObj}</p>
                                </div>
                                <div class="time">${time}</div>
                            </div>
                        </div>
                        <div id="messageDisplay"></div>
                        `;
                let messageContainer = document.getElementById('messageDisplay');
                
                messageContainer.innerHTML += tag;
        });
    // 서버로 질문 전송
    var data = {
       	key: key,
        keyword: question,
        content: "" // 사용자 이름을 가져오는 함수
    };
    stompClient.send("/bot/search", {}, JSON.stringify(data));
    // 입력 필드 비우기
    document.getElementById('question').value = '';

}
// 사용자의 메시지를 화면에 출력
function displayUserMessage(content) {
    let messageContainer = document.getElementById('messageDisplay');
    let tag = `
    <div class="user-message">
    	 <div class="user-time">${time}</div>
        <p>${content}</p>
    </div>
    `;
    messageContainer.innerHTML += tag;
    scrollToBottom(messageContainer); // 스크롤을 제일 아래로 이동
}



// 봇의 메시지를 화면에 출력-연락처
function displayMessage(message) {
    let messageContainer = document.getElementById('messageDisplay');

    let tag = "";
    message.forEach(function(dto) {
        tag += `
            <div>
                <button class="child-category" data-content="${dto.content}"> <p>${dto.content}</p> </button>
            </div>
        `;
    });
    let tag1 = `
    <div class="flex">
        <div id="b-icon"></div>
        <div class="part">
        	<p>메뉴를 선택해주세요.</p>
        	<div class="bot-time">${time}</div>
        	<div class="button-container">${tag}</div>
        </div>
    </div>
    `;
    messageContainer.innerHTML += tag1;
    scrollToBottom(tag1); // 스크롤을 제일 아래로 이동

    // 버튼 클릭 이벤트 리스너 추가
	let buttons = document.querySelectorAll('.child-category');
		buttons.forEach((button, index) => {
    	button.addEventListener('click', function() {
        // 클릭된 버튼의 content 추출
        let content = button.getAttribute('data-content');
        // 사용자 메시지로 출력
        displayUserMessage(content);

        // 인덱스를 사용하여 각각의 버튼에 다른 이벤트 처리
        if (index === 0) {
            // 첫 번째 버튼 클릭 시의 작업
            console.log("첫 번째 버튼 클릭됨");
            fetchContactDpt()
        } else if (index === 1) {
            // 두 번째 버튼 클릭 시의 작업
            console.log("두 번째 버튼 클릭됨");
			searchNameMessage();
        }
    });
});

    
}
function searchNameMessage() {
	let messageContainer = document.getElementById('messageDisplay');
 	let tag = `
 		<div class="bot-message part">
 			<div id="b-icon"></div>
   			<p>이름을 검색해보세요.</p>
   			<div class="bot-time flex">${time}</div>
		 </div>
			`;
			 messageContainer.innerHTML += tag;    
    			scrollToBottom(messageContainer); // 스크롤을 제일 아래로 이동
}
// 봇의 메시지를 화면에 출력-일정
function displayMessage3(message) {
    let messageContainer = document.getElementById('messageDisplay');

    let tag = "";
    message.forEach(function(dto) {
        tag += `
            <div>
                <button class="child-category" data-content="${dto.content}"> <p>${dto.content}</p> </button>
            </div>
        `;
    });
    let tag1 = `
    <div class="flex">
        <div id="b-icon"></div>
        <div class="part">
        	<p>메뉴를 선택해주세요.</p>
        	<div class="bot-time">${time}</div>
        	<div class="button-container">${tag}</div>
        </div>
    </div>
    `;
    messageContainer.innerHTML += tag1;

    // 버튼 클릭 이벤트 리스너 추가
	let buttons = document.querySelectorAll('.child-category');
		buttons.forEach((button, index) => {
    	button.addEventListener('click', function() {
        // 클릭된 버튼의 content 추출
        let content = button.getAttribute('data-content');
        // 사용자 메시지로 출력
        displayUserMessage(content);

        // 인덱스를 사용하여 각각의 버튼에 다른 이벤트 처리
        if (index === 0) {
            // 첫 번째 버튼 클릭 시의 작업
            console.log("첫 번째 버튼 클릭됨");
            searchScheduleMessage1();
        } else if (index === 1) {
            // 두 번째 버튼 클릭 시의 작업
            console.log("두 번째 버튼 클릭됨");
			searchScheduleMessage2();
        }
    });
});

    scrollToBottom(messageContainer); // 스크롤을 제일 아래로 이동
}
function searchScheduleMessage1() {
	let messageContainer = document.getElementById('messageDisplay');
 	let tag = `
 		<div class="bot-message part">
 			<div id="b-icon"></div>
   			<p>개인일정이 없습니다.</p>
   			<div class="bot-time">${time}</div>
		 </div>
			`;
			 messageContainer.innerHTML += tag;    
    			scrollToBottom(messageContainer); // 스크롤을 제일 아래로 이동
}
function searchScheduleMessage2() {
	let messageContainer = document.getElementById('messageDisplay');
 	let tag = `
 		<div class="bot-message part">
 			<div id="b-icon"></div>
   			<p>공연일정이 없습니다.</p>
   			<div class="bot-time">${time}</div>
		 </div>
			`;
			 messageContainer.innerHTML += tag;    
    			scrollToBottom(messageContainer); // 스크롤을 제일 아래로 이동
}
// 봇의 메시지를 화면에 출력-공지
function displayMessage4(message) {
    let messageContainer = document.getElementById('messageDisplay');

    let tag = "";
    message.forEach(function(dto) {
        tag += `
            <div>
                <button class="child-category" data-content="${dto.content}"> <p>${dto.content}</p> </button>
            </div>
        `;
    });
    let tag1 = `
    <div class="flex">
        <div id="b-icon"></div>
        <div class="part">
        	<p>메뉴를 선택해주세요.</p>
        	<div class="bot-time">${time}</div>
        	<div class="button-container">${tag}</div>
        </div>
    </div>
    `;
    messageContainer.innerHTML += tag1;

    // 버튼 클릭 이벤트 리스너 추가
	let buttons = document.querySelectorAll('.child-category');
		buttons.forEach((button, index) => {
    	button.addEventListener('click', function() {
        // 클릭된 버튼의 content 추출
        let content = button.getAttribute('data-content');
        // 사용자 메시지로 출력
        displayUserMessage(content);

        // 인덱스를 사용하여 각각의 버튼에 다른 이벤트 처리
        if (index === 0) {
            // 첫 번째 버튼 클릭 시의 작업
            console.log("첫 번째 버튼 클릭됨");
            searchNoticeMessage1();
        } else if (index === 1) {
            // 두 번째 버튼 클릭 시의 작업
            console.log("두 번째 버튼 클릭됨");
			searchNoticeMessage2();
        }
    });
});

    scrollToBottom(messageContainer); // 스크롤을 제일 아래로 이동
}
function searchNoticeMessage1() {
	let messageContainer = document.getElementById('messageDisplay');
 	let tag = `
 		<div class="bot-message part">
 			<div id="b-icon"></div>
   			<p>오늘의 공지사항이 없습니다.</p>
   			<div class="bot-time">${time}</div>
		 </div>
			`;
			 messageContainer.innerHTML += tag;    
    			scrollToBottom(messageContainer); // 스크롤을 제일 아래로 이동
}
function searchNoticeMessage2() {
	let messageContainer = document.getElementById('messageDisplay');
 	let tag = `
 		<div class="bot-message part">
 			<div id="b-icon"></div>
   			<p>중요 공지사항이 없습니다.</p>
   			<div class="bot-time">${time}</div>
		 </div>
			`;
			 messageContainer.innerHTML += tag;    
    			scrollToBottom(messageContainer); // 스크롤을 제일 아래로 이동
}
// 봇의 메시지를 화면에 출력-child
function displayMessage1(message) {
    let messageContainer = document.getElementById('messageDisplay');

    let tag = "";
    message.forEach(function(dto) {
        tag += `
            <div>
                <button class="grand-child-category" data-content="${dto.content}"> <p>${dto.content}</p> </button>
            </div>
        `;
    });

    // 메뉴 출력
    let tag1 = `
    <div class="flex">
        <div id="b-icon"></div>
        <div class="part">
        	<p>부서를 선택해주세요.</p>
        	<div class="bot-time">${time}</div>
        	<div class="button-container">${tag}</div>
        </div>
    </div>
    `;
    messageContainer.innerHTML += tag1;

    // 버튼 클릭 이벤트 리스너 추가
	let buttons = document.querySelectorAll('.grand-child-category');
		buttons.forEach((button, index) => {
    	button.addEventListener('click', function() {
        // 클릭된 버튼의 content 추출
        let content = button.getAttribute('data-content');
        // 사용자 메시지로 출력
        displayUserMessage(content);

        // 인덱스를 사용하여 각각의 버튼에 다른 이벤트 처리
        if (index === 0) {
            // 첫 번째 버튼 클릭 시의 작업
            console.log("첫 번째 버튼 클릭됨");
            fetchContactDptS1();
        } else if (index === 1) {
            // 두 번째 버튼 클릭 시의 작업
            console.log("두 번째 버튼 클릭됨");
            // 여기에 두 번째 버튼 클릭 시 수행할 작업을 추가
        }
    });
});

    scrollToBottom(messageContainer); // 스크롤을 제일 아래로 이동
    
}
// 봇의 메시지를 화면에 출력-child
function displayMessage2(message) {
    let messageContainer = document.getElementById('messageDisplay');

    let tag = "";
    message.forEach(function(dto) {
        tag += `
            <div>
                <div class="grand-child-category1" data-content="${dto.content}"> <p>${dto.content}</p> </div>
            </div>
        `;
    });

    // 메뉴 출력
    let tag1 = `
    <div class="flex">
        <div id="b-icon"></div>
        <div class="part">
        
        	<p>영업팀의 연락처입니다.</p>
       
        	<div class="bot-time">${time}</div>
        <div class="message-wrap">
        	<div class="salesteam">${tag}</div>
         </div>
        	
        </div>
    </div>
    `;
    messageContainer.innerHTML += tag1;

    // 버튼 클릭 이벤트 리스너 추가
    let buttons = document.querySelectorAll('grand-child-category1');
    buttons.forEach(button => {
        button.addEventListener('click', function() {
            // 클릭된 버튼의 content 추출
            let content = button.getAttribute('data-content');
            // 사용자 메시지로 출력
            displayUserMessage(content);
            
        });
    });

    scrollToBottom(messageContainer); // 스크롤을 제일 아래로 이동
    
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

// 채팅 내용을 초기화하는 함수
function clearChat() {
    let messageContainer = document.getElementById('messageDisplay');
    messageContainer.innerHTML = '';
}

// 메시지 전송 함수
function sendMessage(message) {
    var messageContainer = document.getElementById('bot-main'); // 메시지를 표시할 컨테이너의 ID
    if (messageContainer) {
        messageContainer.innerHTML += message;
        scrollToBottom(messageContainer); // 스크롤을 제일 아래로 이동
    }

}

// 스크롤을 항상 아래로 이동시키는 함수
function scrollToBottom(element) {
    element.scrollTop = element.scrollHeight;
}

$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader(header, token);
    }
});

// 서버에서 연락처 정보를 가져와서 화면에 출력하는 함수
function fetchContactInfo() {
    $.ajax({
        url: "/chatbot/contact",
        method: "GET",
        data: { type: 1 },
        success: function(data) {
			displayUserMessage("연락처 알려줘");
            displayMessage(data);
        },
        error: function(error) {
            console.error("요청하신 연락처를 찾을 수 없습니다.", error);
            displayMessage(error);
        }
    });
}

function fetchScheduleInfo() {
    $.ajax({
        url: "/chatbot/schedule",
        method: "GET",
        data: { type: 4 },
        success: function(data) {
			displayUserMessage("일정 알려줘");
            displayMessage3(data);
        },
        error: function(error) {
            console.error("요청하신 일정을 찾을 수 없습니다.", error);
            displayMessage3(error);
        }
    });
}

function fetchNoticeInfo() {
    $.ajax({
        url: "/chatbot/notice",
        method: "GET",
        data: { type: 7 },
        success: function(data) {
			displayUserMessage("공지사항 알려줘");
            displayMessage4(data);
        },
        error: function(error) {
            console.error("요청하신 공지를 찾을 수 없습니다.", error);
            displayMessage4(error);
        }
    });
}

function fetchContactDpt() {
    $.ajax({
        url: "/chatbot/contact/dpt",
        method: "GET",
        data: { type: 2 },
        success: function(data) {
            displayMessage1(data);
        },
        error: function(error) {
            console.error("요청하신 부서별 검색 결과를 찾을 수 없습니다.", error);
            displayMessage1(error);
        }
    });
}
function fetchContactDptS1() {
    $.ajax({
        url: "/chatbot/contact/dpt/s1",
        method: "GET",
        data: { type: 10 },
        success: function(data) {
            displayMessage2(data);
        },
        error: function(error) {
            console.error("요청하신 검색 결과를 찾을 수 없습니다.", error);
            displayMessage2(error);
        }
    });
}

