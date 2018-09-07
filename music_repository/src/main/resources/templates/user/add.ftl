<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <meta charset="utf-8">
    <title>html5响应式后台登录界面模板</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- CSS -->


    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <![endif]-->
    <script src="/js/jquery-1.8.2.min.js"></script>
</head>

<body>
<form action="login" name="loginForm">

    用户名:<input type="text" name = "username" id = "username" />
    <br />
    <br />
    密&nbsp;码: <input type="text" name = "password" id = "password" />
    <br />
    <br />
    <button type="button" name = "submitForm" id = "submitForm" onclick="doLogin()">登录</button>
</form>
<!-- Javascript -->
<script type="text/javascript">

    function doLogin(){

        var username = $("#username").val();
        var password = $("#password").val();
        $.ajax({
            type: "POST",
            url: "login",
            data: "username="+username+"&password=" + password,
            success: function(msg){
                alert( "Data Saved: " + msg );
            }
        });
    }

</script>
</body>
</html>