function checkBeforeRedirect(event){
    fetch('/user/checkUserData', { //get요청에 사용할 주소
        method: 'GET'
    })
        .then(response =>{
            if (response.ok){
                window.location.href = '/user/selectDiag'; //성공 시 리다이렉션
            }
            else{
                return response.text(); //실패 시 에러 출력
            }
        }).then(message => {
            if(message) {
                alert(message); //경고 메시지 출력
                window.location.href = '/user/userData'; // /user/userData로 리디렉션
                event.preventDefault(); // 페이지 이동 차단
            }
    })
        .catch(error => console.error('Error:',error));
}