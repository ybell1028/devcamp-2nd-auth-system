const main = {
    init: function () {
        let _this = this;

        $("#login-btn").on("click", () => {
            _this.login();
        });

        $("#clear-btn").on("click", () => {
            _this.clear();
        });
    },
    login: () => {
        let data = {
            email: $("#email").val(),
            password: $("#password").val(),
        };
        $.ajax({
            type: "POST",
            url: "http://localhost:8000/auth/login",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
        }).done((data) => {
            if(!localStorage.getItem("accessToken"))
                localStorage.setItem("accessToken", data.token);
            alert(data.token);
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
    },
    toAdminPage: () => {
        let data = {
            token: localStorage.getItem("accessToken"),
        };
        $.ajax({
            type: "POST",
            url: "http://localhost:8000/auth/login",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
        }).done((data) => {

            console.log(data.token);
            alert(data.token);
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
    },
    clear: () => {
        $("#email").val("");
        $("#password").val("");
    }
};

main.init();