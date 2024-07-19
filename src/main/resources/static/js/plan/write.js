document.addEventListener('DOMContentLoaded', function() {
    const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
	const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    
    // 이미지 미리보기 업데이트
    const perPoster = document.getElementById('perPoster');
    const imagePreview = document.getElementById('imagePreview');

	
	
    if (perPoster && imagePreview) { // perPoster과 imagePreview 요소가 존재하는지 확인
        perPoster.addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    imagePreview.src = e.target.result;
                    imagePreview.style.display = 'block'; // 이미지 보이도록 설정
                };
                
                reader.readAsDataURL(file); // 이미지 파일을 데이터 URL로 읽기
            } else {
                imagePreview.src = '#';
                imagePreview.style.display = 'none'; // 이미지 숨기기
            }
        });
    } else {
        console.error('perPoster 또는 imagePreview 요소를 찾을 수 없습니다.');
    }

    // 폼 제출 이벤트 처리
    const performanceForm = document.getElementById('performanceForm');
    
    if (performanceForm) { // 폼 요소가 존재하는지 확인
        performanceForm.addEventListener('submit', function(event) {
            event.preventDefault(); // 폼 제출 기본 동작 방지

            const formData = new FormData(performanceForm);
			
			// perType 값을 폼 데이터에 추가
	        // 실제 선택된 값을 사용해야 합니다.
	        const selectedPerType = document.querySelector('input[name="perType"]:checked').value;
	        formData.append('perType', selectedPerType); 
			
            fetch('/performance/plan/{planNo}', {
                method: 'POST',
                headers: {
			        'Content-Type': 'application/json',
			        [header]: token
			    },
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('공연 기획서가 저장되었습니다.');
                    // 저장 후 필요한 동작 추가
                } else {
                    alert('저장에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('저장 중 오류가 발생했습니다.');
            });
        });
    } else {
        console.error('performanceForm 요소를 찾을 수 없습니다.');
    }
});
