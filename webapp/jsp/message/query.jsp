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
			border="1" class="buy-table table table-striped table-bordered">
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

	    maintable = $("#messageTable").dataTable({
	        "sAjaxSource": "${ctx}/message/query/" + $('#state').val(),
	        "bFilter": true,
	        "bStateSave": false,
	        "sDom": '<"top"ip>rt<"bottom"lp>',
	        "fnRowCallback" : processTableRow,
	        "sPaginationType": "ellipses",
	        "aaSorting" : [[1, 'asc']],
	        "aoColumns": [
				{"mData": "p_productsId", "bSortable": false, "bSearchable" : false, "sWidth":"5%",
				    "mRender": function(data) {
				        return '<input type="checkbox" value="'+data+'" class="chkbox" onClick="clickRow()">';
				    }
				},
	            {"mData": "p_lsin", "bSortable": true, "bSearchable" : true, "sWidth":"8%",
	                "mRender":parseLsin
	            },
	            { "mData": "p_alias", "sName":"p_alias", "bSortable":true, "bSearchable" : true, "sWidth":"15%" ,
	                "mRender": function ( o ) {
	                    return o;
	                }
	            },                       
	            { "mData": "pc_productsName", "sName":"pc_productsName", "bSortable":true, "bSearchable" : true, "sWidth":"15%" ,
	                "mRender": function ( o ) {
	                    return o;
	                }
	            },
	            { "mData": "p_productsImage", "sWidth":"6%" , "bSortable":false,
	                "mRender": function ( o ) {
	                    if (o == null) return null;
	                    var url = o;
	                    tooltip = 'rel="tooltip" data-placement="left" title="<img src='+url+' >"';
	                    o = '<img src="'+ url + '" style="width:60px" '+tooltip+'/>';
	                    return o;
	                },
	                "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
	                    $("img", nTd).tooltip();
	                }
	            },
	            { "mData": "pe_extendPrice", "sName":"pe_extendPrice", "sWidth":"6%" ,"bSortable":true,
	                "mRender": function ( o , t, f) {
	                    var content = '';
	                    content += (!o)?o:Utils.xround(o,6);
	                    if (f.pt_shippingPrice && typeof(f.pt_shippingPrice)=='number' && f.pt_shippingPrice>0) {
	                        content += '<p style="color:blue">[' + Utils.xround(f.pt_shippingPrice,6) + ']</p>';
	                    }
	                    return content;
	                }
	            },
	            { "mData": "freeShipping", "sName":"freeShipping", "sWidth":"5%" ,"bSortable":true,
	                "mRender": function ( o, t, f ) {
	                    if (o==null || o==undefined) return o;
	                    if (o) {
	                        return '<img src="${ctx}/resources/images/freeshipping.png">';
	                    }
	                    return '';
	                }
	            },            
	            { "mData": "p_status", "sName":"p_status", "sWidth":"6%" , "bSortable":true,
	                "mRender": function ( o ) {
	                   return mcStateMap[o];
	                }
	            },           
	            {"mData": "p_productsId", "bSortable": false, "bSearchable" : false, "sWidth":"15%",
	                "mRender" : function(o, t, f) {
	                    var content = '';
//	                    content += '<button id="detail" class="btn btn-normal op_detail" href="#">详细信息</button> ';
	                    content += '<button id="update" class="btn btn-normal op_update" href="#">普通编辑</button> ';
	                    content += '<button id="updaterequest" class="btn btn-normal op_updaterequest" href="#">高级编辑</button> ';
	                    if (f.p_status == 0) { //下架状态
	                    	content += '<button id="onshelf" class="btn btn-normal op_onshelf" href="#">上架</button> ';
	                    }else{
	                    	content += '<button id="offshelf" class="btn btn-normal op_offshelf" href="#">下架</button> ';
	                    }
	                    content += '<button id="modifyprice" class="btn btn-normal op_modifyprice" href="#">修改价格</button> ';
	                    content += '<button id="editSku" class="btn btn-normal op_editSku" href="#">SKU管理</button> ';
//	                    content += '<button id="saveas" class="btn btn-normal op_saveas" href="#">复制新品</button>';
	                    return content;
	                }
	            }
	        ]
	    });
	    bindTableEvents(maintable);
		
	});
	

	function processTableRow(nRow){
		
	}
</script>

