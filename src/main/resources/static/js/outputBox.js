document.addEventListener('DOMContentLoaded', function() {
    // checkbox 요소들에 대한 참조를 가져옵니다.
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');

    // 각 checkbox에 클릭 이벤트 리스너를 추가합니다.
    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('click', function() {
            // Output 요소의 ID를 생성합니다.
            var outputId = checkbox.id + 'Output';
            var outputElement = document.getElementById(outputId);

            // checkbox가 체크되었다면 Output 요소를 표시하고, 그렇지 않다면 숨깁니다.
            if (checkbox.checked) {
                outputElement.style.display = 'block';
            } else {
                outputElement.style.display = 'none';
            }
        });
    });

    // 각 버튼에 대한 클릭 이벤트 리스너를 추가합니다.
    var buttons = document.querySelectorAll('button[id$="Output_1"]'); // id가 'Output_1'로 끝나는 모든 버튼

    buttons.forEach(function(button) {
        button.addEventListener('click', function(event) {
            event.preventDefault(); // 폼 제출 방지

            // 버튼 ID에서 연결된 체크박스의 ID를 추출합니다.
            var checkboxId = button.id.replace('Output_1', '');
            var checkbox = document.getElementById(checkboxId);

            // 연결된 체크박스의 체크 해제
            if(checkbox.checked) {
                checkbox.checked = false;
            }

            // 버튼을 다시 숨김
            var buttonDiv = button.parentElement; // 버튼이 포함된 div를 찾습니다.
            buttonDiv.style.display = 'none';
        });
    });
});
