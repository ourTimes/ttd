<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>注册</title>
	</head>
	<body>
		<div id="register">
			<form id="registerForm" name="registerForm" method="post" action="/ttd/register/submit"  autocomplete=false>
				<h2 class="area-title">账号信息</h2>
				<div id="acctInfo">
					<table class="info-show">
						<tr>
							<td class="td-label"><span class="required_star">*</span>账号：</td>
							<td class="td-content">
								<input type="text" id="loginName" maxlength="45" name="loginName" placeholder="账号">
							</td>
						</tr>
						<tr>
						    <td class="td-label"><span class="required_star">*</span>登录密码：</td>
						    <td class="td-content">
						        <input type="password" class="basicInfo" id="password" maxlength="255" name="password">
						    </td>
						</tr>
						<tr>
						   	<td class="td-label"><span class="required_star">*</span>确认密码：</td>
						   	<td class="td-content">
						     <input type="password" class="basicInfo" id="confirmPassword" maxlength="255" name="confirmPassword">
						   	</td>
						</tr>
						<tr>
							<td class="td-label"><span class="required_star">*</span>姓名：</td>
							<td class="td-content">
								<input type="text" id="name" maxlength="45" name="name" placeholder="姓名">
							</td>
						</tr>
						<tr>
							<td class="td-label"><span class="required_star">*</span>手机号码：</td>
							<td class="td-content">
								<input type="text" id="mobile" name="mobile">
							</td>
						</tr>
					</table>
				</div>
	   
		   		<h2 class="area-title">更多信息</h2>
		        <div id="moreInfo">	             
					<table class="info-show">
						<tr>
							<td class="td-label"><span class="required_star">*</span>电子邮箱：</td>
							<td class="td-content">
							     <input type="text" class="basicInfo" id="email" maxlength="255" name="email">
							</td>
						</tr>
						<tr>
							<td class="td-label"><span class="required_star">*</span>所在地：</td>
							<td class="td-content">
								<select id="province" name="province">
							  		<option value="0">省</option>
								</select>
								&nbsp;&nbsp;
							 	<select id="city" name="city">
									<option value="0">市</option>
								</select>
								&nbsp;&nbsp;
							 	<select id="dist" name="dist">
									<option value="0">区(县)</option>
								</select>
								&nbsp;&nbsp;
							 	<input type="text" class="basicInfo" id="addr" maxlength="255" name="addr" placeholder="乡镇，街道，地址">
							</td>
						</tr>
						<tr>
							<td class="td-label"><span class="required_star">*</span>公司名称：</td>
							<td class="td-content">
								<input type="text" class="basicInfo" id="companyName" maxlength="255" name="companyName"></td>
						</tr>                                                                    
					</table>
				</div>
				<input type="button" value="提交" onclick="this.form.submit()">
			</form>
		</div>
	</body>
</html>

<script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/1.8.3/jquery.min.js"> </script>
<script type='text/javascript'>
$(document).ready(function(){
	refreshDistrict(1, 0);//level=1, parentId=0
	$("#province").change(provinceChange);
	$("#city").change(cityChange);
});

function provinceChange(){
	districtChange(1, $(this));//省变化
}

function cityChange(){
	districtChange(2, $(this));//市变化
}

function distChange(){
	districtChange(3, $(this));//TODO 区县变化
}

function districtChange(level, comp){
	if(level>3)return;//
	var selectedId = comp.children('option:selected').val();
	refreshDistrict(level+1, selectedId);
}

function refreshDistrict(level, parentId){
	$.ajax({
	    async:false,
	    method: 'GET',
	    url:"/ttd/districts/get",
	    data:{parentId:parentId, level:level},
	    success:function(data){
	    	if (data == null) {
	    		return;
	    	}
	    	var comp = null;
	    	if(level==1){//省
	    		comp = $("#province");
	    	}else if(level==2){//市
	    		comp = $("#city");
	    	}else if(level==3){//区县
	    		comp = $("#dist");
	    	}
	    	comp.empty();
	    	comp.append("<option value='0'>所有</option>"); 
	    	for(i = 0; i<data.length; i++){
	    		comp.append("<option value='"+data[i].id+"'>"+data[i].name+"</option>"); 
	    	}
	    }
	});
}
</script>

