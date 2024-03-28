window.onload = function() {
    // 모든 체크박스를 숨깁니다.
    hideAllCheckboxes();

    // 라디오 버튼들에 대한 참조를 얻습니다.
    var radios = document.querySelectorAll('input[type="radio"][name="radio2"]');

    // 각 라디오 버튼에 이벤트 리스너를 추가합니다.
    radios.forEach(function(radio) {
        radio.addEventListener('change', function() {
            // 라디오 버튼이 변경될 때마다 모든 체크박스를 숨기고,
            // 선택된 라디오 버튼에 따라 해당 체크박스를 표시합니다.
            hideAllCheckboxes();
            if (document.getElementById('tab-1').checked) {
                document.getElementById('tab-1Checkbox').style.display = 'block';
            }
            // 필요한 경우 여기서 다른 조건들을 추가할 수 있습니다.
        });
    });

    // 라디오 버튼이 이미 선택되어 있는 경우(예: 페이지 새로고침 후), 체크박스를 표시합니다.
    if (document.getElementById('tab-1').checked) {
        document.getElementById('tab-1Checkbox').style.display = 'block';
    }
};

// 모든 체크박스를 숨기는 함수
function hideAllCheckboxes() {
    var checkboxes = document.querySelectorAll('.checkbox');
    checkboxes.forEach(function(checkbox) {
        checkbox.style.display = 'none';
    });
}