//회원 탈퇴 시 경고창 생성
document.getElementById("deleteUser").addEventListener("click", function (event) {
    event.preventDefault();
    if(confirm("회원 탈퇴하겠습니까?")){
        fetch("/user/delete", {
            method : "get"
        })
            .then(respone => {
                if(respone.redirected){
                    window.location.href = respone.url;
                }
            })
            .catch(error =>{
                console.error("탈퇴 중 오류 발생", error);
            })
    }

})