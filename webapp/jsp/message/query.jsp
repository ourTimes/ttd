<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>query</title>
	</head>
	<body>
		<div class="filter">
			<h3 id="pageTitle">消息列表</h3>
			<div>
				<form id="queryForm">
					<div class="control-group">
						<div class="left">
	                        <label>消息类型:</label>
	                        <select id="messageType">
	                            <option value="0">所有</option>
	                            <option value="1">货源信息</option>
	                            <option value="2">车源信息</option>                                                     
	                        </select>
	                        <label>出发省:</label>
	                        <select id="messageType">
	                            <option value="0">所有</option>
	                            <option value="1">北京</option>
	                            <option value="2">山东</option>                                                     
	                        </select>
	                        <label>市:</label>
	                        <select id="messageType">
	                            <option value="0">临沂</option>
	                            <option value="1">菏泽</option>
	                            <option value="2">济南</option>                                                     
	                        </select>
	                        
	                        <input type="checkbox" id="viewTypeBtn">
		 					<div id="viewTypeBtn" style="display:inline">仅显示认证信息</div>
						</div>
						
						<div class="form-search">
							<input type="text" id="searchQueryInput" class="input-medium search-query" placeholder="随便填点啥呢？" />						
							<button id="searchButton" type="button" class="btn">搜索</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<table id="messageTable" cellpadding="1" cellspacing="0"
				border="1">
				<thead>
					<tr>
						<th>标题	</th>
	                    <th>描述	</th>
						<th>出发地</th>
						<th>目的地</th>
	                    <th>出发时间</th>
						<th>报价</th>
					</tr>
				</thead>
				<tbody><tr></tr></tbody>
				<tfoot></tfoot>
		</table>
	</body>
</html>
<script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/1.8.3/jquery.min.js"> </script>
<script type='text/javascript'>
	$(document).ready(function(){
		
		$("#searchButton").click(function(){
			$.ajax({
				type:"GET",
				url:"/ttd/message/query",
				dataType:"json",
				success:function(data){
					$("#messageTable").empty();
					var html = '<thead><tr><th>标题</th><th>描述</th><th>出发地</th><th>目的地</th><th>出发时间</th><th>报价</th></tr></thead>';
					html += "<tbody>";
					$.each(data, function(contentIndex, content){
						html += "<tr>";
						html = html + "<td>" + content.title + "</td>";
						html = html + "<td>" + content.desc + "</td>";
						html = html + "<td>" + content.fromAddr + "</td>";
						html = html + "<td>" + content.toAddr + "</td>";
						html = html + "<td>" + content.sendTime + "</td>";
						html = html + "<td>" + content.quote + "</td>";
						html += "</tr>";
						console.log(content);
					});
					html += "</tbody>";
					$("#messageTable").html(html);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("Ajax error !");
				}
			});
		});
	});

</script>

