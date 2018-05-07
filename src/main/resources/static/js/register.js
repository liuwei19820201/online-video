function register() {
    if(!registerValid()){
        return false;
    }
    var params = {};
    params.userName = $("#userName").val();
    params.password = $("#password").val();
    $.ajax({
        url: basePath + 'register',
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
                console.log(errorThrown)
                alert("fail");
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("fail");
        }
    });
}

function registerValid(){
    var userName = $("#userName").val();
    var password = $("#password").val();
    var rePassword = $("#rePassword").val();
    if(!userName){
        alert("请输入登录名");
        return false;
    }
    if(!password){
        alert("请输入密码");
        return false;
    }
    if(!rePassword){
        alert("请再次输入密码");
        return false;
    }
    if(password != rePassword){
        alert("两次输入的密码不一致");
        return false;
    }
    return true;
}