const main = {
    init: function () {
        let _this = this;

        $("#register-btn").on("click", () => {
            _this.register();
        });

        $("#cancel-btn").on("click", () => {
            _this.cancel();
        });
    },
    register: () => {
        let password = $("#password").val();
        let passwordConfirm = $("#passwordConfirm").val();
        if(password === passwordConfirm) {
            let data = {
                email: $("#email").val(),
                name: $("#name").val(),
                password: password,
            };
            $.ajax({
                type: "POST",
                url: "http://localhost:8000/user",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data),
            }).done((data) => {
                alert(data.name + "님! 회원가입을 축하드립니다!");
                window.location.href = "http://localhost:8090/verification/" + data.email; // 리다이렉트
            }).fail((error) => {
                const errorArr = error.responseJSON.message.split(":");
                if(errorArr.length < 2) {
                    $("#global-error").text(errorArr[0]);
                } else {
                    $("#global-error").text(errorArr[1].trim());
                    console.log(errorArr[1]);
                }
                console.log(errorArr);
            });
        } else {
            $("#global-error").text("비밀번호 확인이 일치하지 않습니다.");
        }

    }
};

main.init();