// DOMContentLoaded 이벤트가 발생하면, 모든 DOM 요소가 로드되었음을 의미합니다.
document.addEventListener('DOMContentLoaded', function() {
    // 파일 입력 요소와 이미지 미리보기 요소를 변수에 할당합니다.
    const fileInput = document.getElementById('perPoster');
    const imagePreview = document.getElementById('imagePreview');
    const uploadForm = document.getElementById('uploadForm');

    // 파일 입력 요소에 change 이벤트 리스너를 추가합니다.
    fileInput.addEventListener('change', function() {
        // 파일 입력 요소에서 첫 번째 파일을 가져옵니다.
        const file = fileInput.files[0];
        if (file) {
            // FileReader 객체를 생성합니다.
            const reader = new FileReader();
            // FileReader가 파일을 다 읽으면 발생하는 이벤트를 처리합니다.
            reader.onload = function(event) {
                // 읽은 파일 데이터를 이미지 미리보기 요소에 설정합니다.
                imagePreview.src = event.target.result;
                imagePreview.style.display = 'block';
            };
            // 파일을 Data URL로 읽습니다. 이는 base64로 인코딩된 문자열로 파일을 표현합니다.
            reader.readAsDataURL(file);
        } else {
            // 파일이 없으면 이미지 미리보기를 숨깁니다.
            imagePreview.style.display = 'none';
        }
    });

    // 폼 제출 이벤트 리스너를 추가합니다.
    uploadForm.addEventListener('submit', function(event) {
        // 폼의 기본 제출 동작을 방지합니다.
        event.preventDefault();

        // FormData 객체를 생성하고, 파일 데이터를 추가합니다.
        const formData = new FormData();
        formData.append('perPoster', fileInput.files[0]);
        
        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
				const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        // fetch API를 사용하여 파일을 서버로 전송합니다.
        fetch('/plan/create', {
            method: 'POST',
            headers: {
				        [header]: token
				    },
            body: formData
        })
        .then(response => {
            // 서버 응답이 성공적이면 텍스트로 변환하여 반환합니다.
            if (response.ok) {
                return response.text();
            }
            // 응답이 성공적이지 않으면 에러를 던집니다.
            throw new Error('Network response was not ok.');
        })
        .then(responseText => {
            // 서버 응답을 콘솔에 출력합니다.
            console.log('Success:', responseText);
        })
        .catch(error => {
            // 에러가 발생하면 콘솔에 에러를 출력합니다.
            console.error('Error:', error);
        });
    });
});
