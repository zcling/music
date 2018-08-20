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
    <script type="text/javascript">
        $(function () {
        <#if msg??>
            show_loading();
            show_err_msg('${msg}');
        </#if>
        });
    </script>
</head>

<body>
<form name="loginForm" action="login" method="post">
    用户名:<input type="text" name="username" id="username"/>
    <br/>
    <br/>
    密&nbsp;码: <input type="text" name="password" id="password"/>
    <br/>
    <br/>
    <button type="submit" name="submitForm" id="submitForm">登录</button>
</form>
<!-- Javascript -->
</body>
</html>