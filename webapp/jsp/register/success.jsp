<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>注册成功</title>
	</head>
	<body onload="time_out(5);">
		<div id="settle_agreement">
		  	<h2>账号注册成功!</h2>
			<p>您可以开始浏览/发布信息了，去吧，皮卡丘~
			</p>
			<p>页面将在<text id="time"></text>秒后跳转，如未跳转，请点击<a href="${ctx}/ttd/message/show">这里</a>。
			</p>
		</div>
	</body>
</html>

<script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/1.8.3/jquery.min.js"> </script>
<script type='text/javascript'>
function time_out(obj)
{
	var i = obj ;
	if(i==0)
	document.location.href="${ctx}/ttd/message/show";
	document.getElementById("time").innerHTML=i;
	i--;
	setTimeout("time_out("+i+")",1000);
}
</script>

