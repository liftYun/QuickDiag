$(document).ready(function() {
    $('.formclass').on('submit', function(event) {
        if (!$('#user_agree').is(':checked')) {
            event.preventDefault(); // 폼 제출 방지
            alert('개인정보 이용 동의에 체크해주세요.'); // 경고 메시지
        }
    });
});
