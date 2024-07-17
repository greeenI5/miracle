document.addEventListener('DOMContentLoaded', function() {
    // 서버로부터 데이터를 가져오는 가짜 함수 (실제 구현에서는 fetch나 axios 등을 사용)
    function getPlanData() {
        return {
            logo: '/images/logo/m-logo.png',
            image: '/path/to/saved/image.jpg',
            description: '여기에 저장된 기획의도가 표시됩니다.',
            date: '2024-07-15',
            planner: '기획자명',
            title: '공연명',
            genre: '뮤지컬',
            startDate: '2024-08-01',
            endDate: '2024-08-31',
            location: '서울'
        };
    }

    const data = getPlanData();

    // 데이터를 HTML 요소에 삽입
    document.getElementById('logoPreview').src = data.logo;
    document.getElementById('imagePreview').src = data.image;
    document.getElementById('description').textContent = data.description;
    document.getElementById('date').textContent = data.date;
    document.getElementById('planner').textContent = data.planner;
    document.getElementById('title').textContent = data.title;
    document.getElementById('genre').textContent = data.genre;
    document.getElementById('startDate').textContent = data.startDate;
    document.getElementById('endDate').textContent = data.endDate;
    document.getElementById('location').textContent = data.location;

    // 결재 요청 버튼 클릭 이벤트 핸들러
    document.getElementById('approvalRequestButton').addEventListener('click', function() {
        // 실제 구현에서는 fetch나 axios 등을 사용하여 서버로 데이터 전송
        fetch('/approval/request', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(result => {
            if (result.success) {
                // 데이터 전송 성공 시 결재 목록 페이지로 이동
                window.location.href = '/approval/list';
            } else {
                alert('결재 요청 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('결재 요청 중 오류가 발생했습니다.');
        });
    });
});
