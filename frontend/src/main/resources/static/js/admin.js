const accessToken = localStorage.getItem("x-access-token");

if(accessToken) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8000/user/admin",
        headers: {'x-access-token': accessToken},
        dataType: "json",
        contentType: "application/json; charset=utf-8",
    }).done((data) => {
        let userList = data.data;
        let str = "";

        $.each(userList, function(i) {
            let rowId = "row" + i;
            str += "<TR id=" + rowId  + ">"
            str += '<TD>' + userList[i].name + '</TD><TD>' + userList[i].role + '</TD><TD>' + userList[i].email + '</TD><TD>' + userList[i].uuid + '</TD><TD>' + userList[i].createdAt + '</TD><TD>' + userList[i].modifiedAt + '</TD><TD id="' + userList[i].uuid + '" class="py-2 my-2 btn btn-danger btn-sm" onclick="removeCheck(this.id)">' + 'Delete' + '</TD>'
            str += '</TR>'
        });
        $("#userTableBody").append(str);
    }).fail((error) => {
        const errorArr = error.responseJSON.message.split(":");
        if(errorArr.length < 2) {
            alert(errorArr[0]);
        } else {
            $("#global-error").text(errorArr[1].trim());
            alert(errorArr[1]);
        }
        console.log(error);
        window.location.href = "http://localhost:8090";
    });
} else {
    alert("토큰이 존재하지 않습니다.");
    window.location.href = "http://localhost:8090";
}

$('#logout-btn').on("click", () => {
    if (confirm("로그아웃 하시겠습니까?") == true) {
        localStorage.removeItem("x-access-token");
        alert('로그아웃 했습니다.');
        window.location.href = "http://localhost:8090";
    } else {
        return false;
    }
});

function removeCheck(uuid) {
    if (confirm("사용자를 삭제하시겠습니까?") == true) {
        let accessToken = localStorage.getItem("x-access-token");
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8000/user/admin?uuid=" + uuid,
            headers: {'x-access-token': accessToken},
            dataType: "json",
            contentType: "application/json; charset=utf-8",
        }).done((data) => {
            alert('삭제 성공');
            window.location.href = "http://localhost:8090/admin";
        }).fail((error) => {
            const errorArr = error.responseJSON.message.split(":");
            if (errorArr.length < 2) {
                alert(errorArr[0]);
            } else {
                $("#global-error").text(errorArr[1].trim());
                alert(errorArr[1]);
            }
            window.location.href = "http://localhost:8090/admin";
        });
    } else {
        return false;
    }
}