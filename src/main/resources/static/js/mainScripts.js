function checkBeforeRedirect(event) {
    fetch('/user/checkUserData', { // get 요청에 사용할 주소
        method: 'GET'
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/user/selectDiag'; // 성공 시 리다이렉션
            } else {
                return response.text(); // 실패 시 에러 출력
            }
        }).then(message => {
        if (message) {
            alert(message); // 경고 메시지 출력
            window.location.href = '/user/userData'; // /user/userData로 리디렉션
            event.preventDefault(); // 페이지 이동 차단
        }
    })
        .catch(error => console.error('Error:', error));
}

// 챗봇 툴바 및 컨테이너 토글 로직
document.addEventListener('DOMContentLoaded', function() {
    const chatbotToolbar = document.getElementById('chatbotToolbar');
    const chatbotContainer = document.getElementById('chatbotContainer');
    const closeChatbot = document.getElementById('closeChatbot');
    const sendMessage = document.getElementById('sendMessage');
    const userMessage = document.getElementById('userMessage');
    const chatbotMessages = document.getElementById('chatbotMessages');

    chatbotToolbar.addEventListener('click', function() {
        chatbotContainer.style.display = 'block';
    });

    closeChatbot.addEventListener('click', function() {
        chatbotContainer.style.display = 'none';
    });

    sendMessage.addEventListener('click', function() {
        const message = userMessage.value;
        if (message) {
            // 사용자의 메시지를 채팅에 추가
            const userMsgDiv = document.createElement('div');
            userMsgDiv.classList.add('user-message');
            userMsgDiv.innerText = message;
            chatbotMessages.appendChild(userMsgDiv);

            // 입력 필드를 비움
            userMessage.value = '';

            // 여기서 일반적으로 메시지를 챗봇 서버로 보내고
            // 응답을 처리하여 chatbotMessages div에 표시합니다.
            // 이 예제에서는 단순한 에코 응답을 가정합니다.
            setTimeout(() => {
                const botMsgDiv = document.createElement('div');
                botMsgDiv.classList.add('bot-message');
                botMsgDiv.innerText = 'Bot: ' + message;
                chatbotMessages.appendChild(botMsgDiv);
            }, 1000);
        }
    });
});
document.addEventListener("DOMContentLoaded", function () {

    const header = document.getElementById("typingHeader"); // id가 typingHeader html을 사용
    const text = header.innerHTML; //header 안에 있는 내용
    header.innerHTML = ''; // html안에있는 내용을 초기화

    let i = 0;
    const speed = 50; //타이핑 속도, 단위 밀리초

    function typeWriter() {
        if(i < text.length){
            //문자열 길이만큼 반복
            header.innerHTML += text.charAt(i); // 초기화된 html에 다음 문자를 더함
            i++;
            setTimeout(typeWriter,speed);
        }
    }

    typeWriter();
})
document.addEventListener("DOMContentLoaded", function() {
    let elements = document.querySelectorAll('.image-container');
    elements.forEach((element, index) => {
        setTimeout(() => {
            element.style.opacity = '1'; // opacity를 1로 설정하여 서서히 나타나도록 함
        }, 2000 + index * 500); // 1초 후부터 시작하여 1초 간격으로 나타남
    });
});