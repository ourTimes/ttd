<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<html>
	<head>
		<meta charset="UTF-8">
		<title>发布消息</title>
		
		<link rel="stylesheet" type="text/css" href="../css/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../css/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="../css/style.css">
		<script type="text/javascript" src="../js/jquery/jquery.js"></script>
		<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
		<script type="text/javascript">
			$(function() {
				//初始化日历
				$('#dd').datebox({
					editable : false,
					value : new Date()
				}).datebox('calendar').calendar({
					validator : function(date) {
						var now = new Date();
						var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
						var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 30);
						return d1 <= date && date <= d2;
					}
				});
				//货物类型
				$('#cc').combobox({
					url : 'data.json',
					valueField : 'id',
					textField : 'text',
					editable : false
				});
			});

			function submitForm() {
				if ($('#departure').val() == "" || $('#departure').val().trim() == "") {
					alert("请输入始发地");
					return;
				}
				if ($('#destination').val() == "" || $('#destination').val().trim() == "") {
					alert("请输入目的地");
					return;
				}
				$('#cc').combobox('getText');
				$('#dd').datebox('getValue');
				var paras = "departure=" + $('#departure').val().trim() + "&destination=" + $('#destination').val().trim() + "&date=" + $('#dd').datebox('getValue') + "&type=" + $('#cc').combobox('getText');
				if ($('#message').val().trim() != "") {
					paras += "&message=" + $('#message').val().trim();
				}
				
				var options = {
					type : 'POST',
					url : '/ttd/message/create',
					dataType : 'text',
					data : paras,
					success : function() {
						alert('success!');
					}
				};
				$.ajax(options);
			}
		</script>
	</head>
	<body>
		<div class="easyui-panel" title="发布消息" style="width:400px">
			<div style="padding:10px 60px 20px 60px">
				<form id="ff">
					<table cellpadding="5">
						<tr>
							<td>出发地：</td>
							<td>
							<input class="easyui-textbox" type="text" id="departure" required placeholder="始发地">
							</td>
						</tr>
						<tr>
							<td>目的地：</td>
							<td>
							<input class="easyui-textbox" type="text" id="destination" required placeholder="目的地">
							</td>
						</tr>
						<tr>
							<td>发货日期：</td>
							<td>
							<input id="dd" required>
							</td>
						</tr>
						<tr>
							<td>货物类别：</td>
							<td>
							<input id="cc" name="goodsCatagories">
							</td>
						</tr>
						<tr>
							<td>附加信息：</td>
							<td>
							<input class="easyui-textbox" type="text" id="message" multiple="multiple" style="height:60px" placeholder="添加附加信息">
							</td>
						</tr>
					</table>
				</form>
				<div style="text-align:center;padding:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">发布消息</a>
				</div>
			</div>
		</div>
	</body>
</html>

