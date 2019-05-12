function login() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    $.ajax({
        type: "POST",
        dataType: "json",
        url: '/login',
        contentType: "application/json",
        data:JSON.stringify({
            "userName": username,
            "password": password
        }),
        success: function (result, status) {
            console.log("username is :" + result['username'])
            if (status == 'success' && result["uid"]!=undefined) {
                alert("登录成功");
            }else{
                alert("登录失败")
            }
        }
    });
}

function register() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var email = document.getElementById("email").value;
    $.ajax({
        type: "POST",
        dataType: "json",
        url: '/register',
        contentType: "application/json",
        data:JSON.stringify({
            "userName": username,
            "password": password,
            "email": email
        }),
        success: function (result, status) {
            console.log("username is :" + result['username'])
            if (status == 'success' && result["uid"]!=undefined) {
                alert("注册成功");
            }else{
                alert("注册失败");
            }
        }
    });
}