window.onload = function () {
	
	let today = new Date();
	const calendarBody = document.querySelector('.calendar-body');
	const prevEl = document.querySelector('.prev');
	const nextEl = document.querySelector('.next');
	const inputBox = document.querySelector('.input-box');
	const inputBtn = document.querySelector('.input-btn');
	const inputList = document.querySelector('.todoList');
	const showList = document.querySelector('.showList');
	const listText = document.querySelector('.listText');
	const createDate = document.querySelector('.createDate');
	const bgblack = document.querySelector('.bgblack');
	const closedBtn = document.querySelector('.closed');
	let currentDate;
	
	
	buildCalendar();
	function buildCalendar() {
	  let firstDate = new Date(today.getFullYear(), today.getMonth(), 1);
	  const monthList = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
	  const leapYear = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	  const notLeapYear = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	  const headerYear = document.querySelector('.current-year-month');
	  // 윤년 체크하기
	  if (firstDate.getFullYear() % 4 === 0) {
	    pageYear = leapYear;
	  } else {
	    pageYear = notLeapYear;
	  }
	  headerYear.innerHTML = `${monthList[firstDate.getMonth()]}&nbsp;&nbsp;&nbsp;&nbsp;${today.getFullYear()}`;
	  makeElement(firstDate);
	  showMain();
	  currentDateget();
	  resetInsert();
	}
	
	function showMain() {
	  // '.main-day' 클래스를 가진 요소를 선택하고, 그 요소의 텍스트 내용을 현재 요일로 설정
	  document.querySelector('.main-day').textContent = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][today.getDay()];
	  // '.main-date' 클래스를 가진 요소를 선택하고, 그 요소의 텍스트 내용을 현재 날짜로 설정
	  document.querySelector('.main-date').textContent = today.getDate();
	}
	
	function makeElement(firstDate) {
	  let weekly = 100;
	  let dateSet = 1;
	  for (let i = 0; i < 6; i++) {
	    let weeklyEl = document.createElement('div');
	    weeklyEl.setAttribute('class', weekly);
	    weeklyEl.setAttribute('id', "weekly");
	    for (let j = 0; j < 7; j++) {
	      // i === 0이여야 하는 이유는 첫 날짜를 찍고 그 다음 날짜가 0번째 칸부터 다시 그려져야 하기 때문
	      // firstDate.getMonth() => 현재 달의 일수가 몇일인지 반환해주고, 이 조건은 반환 된 값에 따라 출력해 준 후, 달력 출력 종료조건이다.
	      if (i === 0 && j < firstDate.getDay() || dateSet > pageYear[firstDate.getMonth()]) {
	        // 만약 해당 칸에 날짜가 없으면 div엘리먼트만 생성한다.
	        let dateEl = document.createElement('div');
	        weeklyEl.appendChild(dateEl);
	      } else {
	        // 해당 칸에 날짜가 있으면 div엘리먼트 생성 후 해당 날짜 넣어주기
	        let dateEl = document.createElement('div');
	        dateEl.textContent = dateSet;
	        dateEl.setAttribute('class', dateSet);
	        dateEl.setAttribute('id', `${today.format2()}-${dateSet}`);
	        weeklyEl.appendChild(dateEl);
	        dateSet++;
	      }
	    }
	    weekly++;
	    calendarBody.appendChild(weeklyEl);
	  }
	  // 현재 내가 선택한 날짜가 있으면 이전 달, 다음 달로 넘어가도 화면에 보여주기 위해 써줌
	  let clickedDate = document.getElementsByClassName(today.getDate());
	  clickedDate[0].classList.add('active');
	}
	
	function removeCalendar() {
	  let divEls = document.querySelectorAll('.calendar-body > #weekly > div');
	  for (let i = 0; i < divEls.length; i++) {
	    divEls[i].remove();
	  }
	}
	
	// 공통 작업을 수행하는 함수
	const updateCalendar = (monthOffset) => {
	  today = new Date(today.getFullYear(), today.getMonth() + monthOffset, today.getDate());
	  removeCalendar();
	  buildCalendar();
	  resetInsert();
	  redrawLi();
	};
	
	// 이전 달로 이동
	prevEl.addEventListener('click', () => updateCalendar(-1));
	// 다음 달로 이동
	nextEl.addEventListener('click', () => updateCalendar(1));
	
	
	function currentDateget() {
	  // format()을 이용해서 현재 날짜를 보기좋게 출력해주기 위해 사용.
	  currentDate = today.format();
	}
	
	// 각 날짜 클릭 시 처리할 공통 함수
	const handleDateClick = (e) => {
	  const target = e.target;
	  if (target.innerHTML === '') return;
	
	  document.querySelectorAll('.calendar-body > #weekly > div').forEach(div => div.classList.remove('active'));
	  target.classList.add('active');
	
	  today = new Date(today.getFullYear(), today.getMonth(), target.innerHTML);
	  showMain();
	  currentDateget();
	  redrawLi();
	  resetInsert();
	};
	// 할 일 입력 버튼 클릭 시 처리할 함수
	const handleInputClick = (e) => {
	  e.preventDefault();
	  insertTodo(inputBox.value);
	};
	// 각 날짜 클릭 이벤트 리스너 등록
	calendarBody.addEventListener('click', handleDateClick);
	// 할 일 입력 버튼 클릭 이벤트 리스너 등록
	inputBtn.addEventListener('click', handleInputClick);
	
	function insertTodo(text) {
	  const todoObj = {
	    todo: text,
	    id: DATA[currentDate] ? DATA[currentDate].length + 1 : 1
	  };
	
	  if (!DATA[currentDate]) {
	    DATA[currentDate] = [];
	  }
	  DATA[currentDate].push(todoObj);
	
	  const liEl = document.createElement('li');
	  liEl.setAttribute('id', todoObj.id);
	
	  const spanEl = document.createElement('span');
	  spanEl.innerHTML = text;
	
	  const delBtn = document.createElement('button');
	  delBtn.innerText = "DEL";
	  delBtn.setAttribute('class', 'del-data');
	  delBtn.addEventListener('click', delWork);
	
	  liEl.append(spanEl, delBtn);
	  liEl.addEventListener('dblclick', showTodo);
	  
	  inputList.appendChild(liEl);
	
	  save();
	  inputBox.value = '';
	}
	function clearTodoList() {
	  document.querySelectorAll('li').forEach(li => inputList.removeChild(li));
	}
	
	function createTodoElement(todo) {
	  const liEl = document.createElement('li');
	  liEl.setAttribute('id', todo.id);
	
	  const spanEl = document.createElement('span');
	  spanEl.innerHTML = todo.todo;
	
	  const delBtn = document.createElement('button');
	  delBtn.innerText = "DEL";
	  delBtn.setAttribute('class', 'del-data');
	  delBtn.addEventListener('click', delWork);
	
	  liEl.append(spanEl, delBtn);
	  liEl.addEventListener('dblclick', showTodo);
	
	  return liEl;
	}
	
	function redrawLi() {
	  clearTodoList();
	
	  if (DATA[currentDate]) {
	    DATA[currentDate].forEach(todo => {
	      const liEl = createTodoElement(todo);
	      inputList.appendChild(liEl);
	    });
	  }
	}
	// 다음달,이전달 다른날, 첫 로드 될 때 마다 todo 목록이 있으면(if로 조건문 처리) 다 지우고 다시 그려주는 함수 
	function resetInsert() {
	  let storeObj = localStorage.getItem(currentDate);
	  if (storeObj !== null) {
	    let liEl = document.querySelectorAll('LI');
	    for (let i = 0; i < liEl.length; i++) {
	      inputList.removeChild(liEl[i]);
	    }
	    // parse 해주기 전에는 localStorage는 string만 가져오니까 parse해준다.
	    const parsed = JSON.parse(localStorage.getItem(currentDate));
	    // forEach로 작성되있는 모든 todolist의 항목들을 돌면서 로컬에 저장되어 있는 목록을 화면에 만들어준다.
	    parsed.forEach(function (todo) {
	      if (todo) {
	        let lili = document.createElement('li');
	        let spanspan = document.createElement('span');
	        let deldel = document.createElement('button');
	        deldel.setAttribute('class', 'del-data');
	        deldel.innerText = "DEL";
	        lili.setAttribute('id', todo.id);
	        spanspan.innerHTML = todo.todo;
	        lili.appendChild(spanspan);
	        lili.appendChild(deldel);
	        inputList.appendChild(lili);
	        deldel.addEventListener('click', delWork);
	        lili.addEventListener('dblclick', showTodo);
	      }
	    });
	  }
	}
	resetInsert();
	
	function delWork(e) {
	  e.preventDefault();
	  let delParentLi = e.target.parentNode;
	  inputList.removeChild(delParentLi);
	  // DATA[currentDate]를 filter함수를 이용해 todo로 돌면서 todo의 아이디값과 현재 내가 누른 아이디값이 같지 않은 것을 배열에 담아 리턴해주어서 
	  // 내가 지우고자 하는 요소를 뺀 나머지 요소를 배열에 담아 리턴해준다. 
	  // 그 배열을 다시 DATA[currentDate]에 할당하여 save();를 통해 localStorage에 넣어준다.
	  const cleanToDos = DATA[currentDate].filter(function (todo) {
	    return todo.id !== parseInt(delParentLi.id);
	  });
	  DATA[currentDate] = cleanToDos;
	  save();
	}
	
	function showTodo(e){
	  showList.style.display = "block"
	  bgblack.style.display = "block"
	  listText.textContent = e.target.textContent;
	  createDate.textContent = currentDate;
	}
	
	closedBtn.addEventListener('click', function(e){
	  showList.style.display = "none";
	  bgblack.style.display = "none";
	});
	
	function save() {
	  localStorage.setItem(currentDate, JSON.stringify(DATA[currentDate]));
	}

}