document.addEventListener('DOMContentLoaded', function() {
    // 서버로부터 데이터를 가져오는 가짜 함수 (실제 구현에서는 fetch나 axios 등을 사용)
    function getPlanData() {
        return {
            logo: '/images/logo/m-logo.png',
            perPoster: '/path/to/saved/image.jpg',
            perContent: '여기에 저장된 기획의도가 표시됩니다.',
            writeAt: '2024-07-15',
            employee: '기획자명',
            perTitle: '공연명',
            perType: '뮤지컬',
            startAt: '2024-08-01',
            finishAt: '2024-08-31',
            location: '서울'
        };
    }

    const data = getPlanData();

    // 데이터를 HTML 요소에 삽입
    document.getElementById('logoPreview').src = data.logo;
    document.getElementById('perPoster').src = data.perPoster;
    document.getElementById('perContent').textContent = data.perContent;
    document.getElementById('writeAt').textContent = data.writeAt;
    document.getElementById('employee').textContent = data.employee;
    document.getElementById('perTitle').textContent = data.perTitle;
    document.getElementById('perType').textContent = data.perType;
    document.getElementById('startAt').textContent = data.startAt;
    document.getElementById('finishAt').textContent = data.finishAt;
    document.getElementById('location').textContent = data.location;

    // 결재 승인 버튼 클릭 이벤트 핸들러
    document.getElementById('approvalButton').addEventListener('click', function() {
        // 실제 구현에서는 fetch나 axios 등을 사용하여 서버로 데이터 전송
        fetch('/approval/plan', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(result => {
            if (result.success) {
                // 데이터 전송 성공 시 공연 목록 페이지로 이동
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
