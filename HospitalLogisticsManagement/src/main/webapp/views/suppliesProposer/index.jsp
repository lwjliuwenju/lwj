<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="../../css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="../../Js/themes/default/easyui.css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="../../Js/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="../../common/user/addUser.css" />
	
<style>
#responsedepartmentTools input, #state, #shunxu {
	font-size: 12px;
	border: 0px;
	line-height: 20px;
	height: 20px;
	padding: 0px;
	border: 1px solid #A4BED4;
}

#responsedepartmentTools div {
	margin: 5px;
}
	.timeline-header{
	    margin: 0;
    color: #00c0ee;
    border-bottom: 1px solid #f4f4f4;
    padding: 10px;
    font-size: 16px;
    line-height: 1.1;
}
li{
list-style:none;
}
.timeline-body{
padding:10px;}
.repair {
	margin-left: -80px;
	margin-top: 10px;
	margin-bottom: 10px;
}
 
.repair-reason {
	margin: 5px 5px;
	display: inline-block;
	line-height: 30px;
}
</style>
<script type="text/javascript" src="../../Js/jquery.min.js"></script>
<!--[if lt IE 9]>
   <script type="text/javascript" src='../../Js/json2.js'></script>
   <![endif]-->
<script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../Js/vilidate.js"></script>
<script type="text/javascript" src="../../Js/pinyin.js"></script>
<script type="text/javascript" src="../../common/suppliesProposer/index.js"></script>
<div class="responsedepartmentIndex">
	<div align="left" id="responsedepartmentTools">
		<div style="margin:2px 0px;">
			提&nbsp;&nbsp;&nbsp;&nbsp;交&nbsp;&nbsp;&nbsp;&nbsp;人:<input
				id="submitperson" name="dept" style="width:200px"></input>
			状&nbsp;&nbsp;&nbsp;&nbsp;态： <SELECT id='state' class="easyui-combobox" style="width: 200px;">
				<option value="-1">请选择</option>
				<option value="0">未发放</option>
				<option value="1">已发放</option>
				
			</SELECT> 
				排序方式： <SELECT id='shunxu' class="easyui-combobox" style="width: 200px;">
				<option value="-1">请选择</option>
				<option value="0">状态</option>
				<option value="1">时间</option>
			</SELECT>
		</div>
		<div style="margin:2px 0px;">
			申请单部门：<input id="shenqingdepartment" name="dept"
				style="width: 200px;"></input> 
		     </div>
		</div>
		<div>
			<a id="searchResponse" iconCls="icon-search" href="javascript:void(0)" class="easyui-linkbutton">查询</a> 
			<a id="complete"href="javascript:void(0)" iconCls="icon-ok" class="easyui-linkbutton">发放</a>
		</div>
	</div>
	<div class="supplieslist">
		<div id='supplieslist'></div>
	</div>
	</div>
	
	

	 