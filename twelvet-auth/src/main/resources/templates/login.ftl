<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="description" content="oauth2">
    <meta name="author" content="授权中心">

    <title>twelvet 微服务统一认证</title>

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body{height:100%;background:#F8F8F8;}
        .container{max-width:350px}
        .img-responsive{margin:50pt auto;max-width:90pt}
        #welcome{padding:20px;text-align:center;font-weight:700;color:#000;font-size: 30px}
        .copyright{text-align:center}
        .copyright > a{color:#000;text-decoration:none}
        .copyright > a:hover{color:#72afd2}
    </style>
</head>

<body class="sign_body">
<div class='container'>
    <div class='row'>
        <div class='col-xs-12'>
            <img src="/assets/images/logo.svg" class='img-responsive' alt="logo">
            <p id='welcome'>
                统一登录 - OAuth2
            </p>
            <form action='/token/form' method="POST">
                <div class='form-group'>
                    <input type="text" class='form-control' name='username' placeholder='账号' id='username'>
                </div>
                <div class='form-group'>
                    <input type="password" class='form-control' name='password' placeholder='密码' id='password'>
                </div>
                <div class='form-group'>
                    <input type="submit" class='btn btn-primary' style='width:100%' value='登录'>
                </div>
            </form>
            <footer class='copyright'>
                <a href="https://www.twelvet.cn" target="_blank">Powered By TwelveT</a>
            </footer>
        </div>
    </div>
</div>
</body>
</html>
