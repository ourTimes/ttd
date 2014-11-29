<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>登录</title>
	</head>
	<body>
		<form id="loginForm" name="loginForm" method="post" action="/ttd/login/doLogin" autocomplete=false>
			<div>
				<input type="text" placeholder="用户名" value="" name="username" id="username">
			</div>
			<div>
				<input type="password" placeholder="密码" value="" name="password" id="password">
			</div>
			<div>
				<input type="button" value="提交" onclick="this.form.submit()">
				<a id="forgetpwd" href="#">忘记密码？</a>
				<a id="registernow" href="/ttd/register/apply">现在注册</a>
			</div>
		</form>
	</body>
</html>