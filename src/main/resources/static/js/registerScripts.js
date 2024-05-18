//아이디 중복을 체크하는ajax
const idCheck = () => {
    const id = document.getElementById("user_id").value; //id에 사용자가 입력한 값을 가져옴.
    const checkResult = document.getElementById("id_check_result");
    $.ajax({
        type:"post", //post타입으로 보내겠다
        url: "/user/id_check", // 컨트롤러 url과 일치시킬 것
        data:{
            "user_id" : id // user_id를 id라는 이름으로 서버로 전송한다
        },
        success : function (res) { // res는 컨트롤러로 부터 받아온다
            if(res === "ok") {
                checkResult.style.color = "green";
                checkResult.innerHTML = "사용가능한 ID";
            } else if (res === "duplicate") {
                checkResult.style.color = "red";
                checkResult.innerHTML = "이미 사용중인 ID";
            }
        },
        error: function (err) {
        }
    });
};

//전화번호 중복을 체크하는 ajax
const phoneNumberCheck = () => {
    const phoneNumber = document.getElementById("user_phoneNumber").value;
    const checkResult = document.getElementById("phoneNumber_check_result");
    $.ajax({
        type: "post",
        url: "/user/phone_check",
        data: {
            "user_phoneNumber": phoneNumber
        },
        success: function (res) {
            if (res === "ok") {
                checkResult.style.color = "green";
                checkResult.innerHTML = "사용 가능한 전화번호";
            } else if (res === "duplicate") {
                checkResult.style.color = "red";
                checkResult.innerHTML = "이미 사용 중인 전화번호";
            }
        },
        error: function (err) {
            // 에러 처리
        }
    });
};

//생년월일에 미래 값을 입력할 수 없도록 하는 js.
const today = new Date().toISOString().split('T')[0];
document.getElementById("user_birthday").max = today;
