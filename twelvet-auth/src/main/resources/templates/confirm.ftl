<!doctype html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>TwelveT 第三方授权</title>
    <link rel="shortcut icon" type="image/vnd.microsoft.icon" href="/assets/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="/assets/css/bootstrap.min.css"/>
</head>
<body>

<nav class="navbar navbar-default container-fluid">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">TwelveT - 开放平台</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-5">
            <p class="navbar-text navbar-right">
                <a target="_blank" href="https://twelvet.cn">欢迎您，${principalName}</a>
            </p>
        </div>
    </div>
</nav>
<div style="padding-top: 80px;width: 300px; color: #555; margin:0 auto;">
    <form style="text-align: center" id='confirmationForm' name='confirmationForm' action="/oauth2/authorize"
          method='post'>
        <input type="hidden" name="client_id" value="${clientId}">
        <input type="hidden" name="state" value="${state}">
        <p>
            <a href="${appSite!''}" target="_blank">${appName!'未定义应用名称'}</a> 将获得以下权限：
        </p>
        <ul class="list-group">
            <li class="list-group-item"> <span>
              <#list scopeList as scope>
                  <input type="checkbox" checked="checked" name="scope" value="${scope}"/><label>${scope}</label>
              </#list>
        </ul>
        <p class="help-block">授权后表明你已同意 <a>服务协议</a></p>
        <button class="btn btn-success" type="submit" id="write-email-btn">授权</button>
    </form>

    <footer style="clear:both;text-align: center">
        <a href="https://twelvet.cn" target="_blank">Powered By TwelveT</a>
    </footer>
</div>

</body>
</html>
