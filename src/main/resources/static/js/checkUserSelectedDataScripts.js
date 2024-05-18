document.addEventListener('DOMContentLoaded', function() {
    // 모든 폼 요소 선택
    var formElements = document.querySelectorAll('input, select, textarea, button');

    // 모든 폼 요소를 읽기 전용으로 설정
    formElements.forEach(function(element) {
        element.setAttribute('readonly', 'readonly'); // 읽기 전용 속성 추가
        element.setAttribute('disabled', 'disabled'); // 비활성화 속성 추가
    });
});
