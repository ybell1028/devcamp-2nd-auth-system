function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}

console.log(searchParam("sign"));

$.ajax({
    type: "GET",
    url: "http://localhost:8000/user/confirmation?sign=" + searchParam("sign"),
    dataType: "json",
    contentType: "application/json; charset=utf-8",
}).done((data) => {
    if(data.data.success) {
        alert("인증에 성공했습니다. 로그인 페이지로 이동합니다.");
    } else {
        alert("인증에 실패했습니다.");
    }
    window.location.href = "http://localhost:8090";
}).fail((error) => {
    const errorArr = error.responseJSON.message.split(":");
    if(errorArr.length < 2) {
        alert(errorArr[0]);
    } else {
        $("#global-error").text(errorArr[1].trim());
        alert(errorArr[1]);
    }
    window.location.href = "http://localhost:8090";
});