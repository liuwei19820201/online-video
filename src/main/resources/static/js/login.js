function login() {
    if(!loginValid()){
        return false;
    }
    var params = {};
    params.userName = $("#userName").val();
    params.password = $("#password").val();
    $.ajax({
        url: basePath + 'login',
        type: 'POST',
        data: params,
        success: function (response) {
            console.log(response);
            if(response.code == 200){
                localStorage.setItem("member", JSON.stringify(response.data));
                window.location.href = basePath + "hplus/index.html";
                // var jsonData = localStorage.getItem("member");
                // var data = JSON.parse(jsonData);
                // alert(data.account);
            }else{
                alert(response.message);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown)
            alert("fail");
        }
    });
}

function loginValid(){
    var userName = $("#userName").val();
    var password = $("#password").val();
    if(!userName){
        alert("请输入登录名");
        return false;
    }
    if(!password){
        alert("请输入密码");
        return false;
    }
    return true;
}

function logout() {
    $.ajax({
        url: basePath + 'api/logout',
        type: 'GET',
        success: function (response) {
            if(response.code == 200){
                localStorage.clear();
                window.location.href = basePath;
            }else{
                alert(response.message);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown)
            alert("fail");
        }
    });
}

