<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="../../css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="../../Js/themes/default/easyui.css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="../../Js/themes/icon.css" />
<script type="text/javascript" src="../../Js/jquery.min.js"></script>
  <script type="text/javascript" src='../../Js/json2.js'></script>
<script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../Js/vilidate.js"></script>
<script type="text/javascript" src="../../Js/pinyin.js"></script>
<script type="text/javascript" src="../../common/transport/index.js"></script>
<style>
.PopUpTableBorder {
	border-collapse: collapse;
	border-width: 1px;
	border-style: solid;
	border-color: #a4d5e6;
	-o-border-image: initial;
	border-image: initial;
	background-color: #eef9ff;
	 margin: 5px auto;
}

.PopUpTableBorder .PopUpTableTitle {
	background-color: #eef9ff;
	text-align: right;
}
	li{
	list-style:none;
	}
	h1,h2,h3,h4,h5{
	font-weight:normal;
	
	}
	.timeline-header{
	margin: 0;
    color: #00c0ee;
    border-bottom: 1px solid #f4f4f4;
    padding: 10px;
    font-size: 16px;
    line-height: 1.1;
	}
	.timeline-body{
	padding:10px;
	}
.PopUpTableBorder td {
	border-collapse: collapse;
	line-height: 30px;
	border-width: 1px;
	border-style: solid;
	border-color: #a4d5e6;
	-o-border-image: initial;
	border-image: initial;
}

.PopUpTableBorder .supplies, .confirm .PopUpTableBorder .username {
	line-height: 20px;
	height: 20px;
	border-width: 1px;
	border-style: solid;
	border-color: #629fcf;
	-o-border-image: initial;
	border-image: initial;
	margin: 4px;
}
.ButtonStyle_Blue {
	height: 22px;
	line-height: 22px;
	color: #000000;
	cursor: pointer;
	vertical-align: middle;
	margin-right: 4px;
	padding-left: 10px;
	padding-right: 10px;
	border-width: 1px;
	border-style: solid;
	border-color: #80b5dd;
	-o-border-image: initial;
	border-image: initial;
	background: url(../imgages/button_bg_blue.gif) repeat-x;
}

.butList {
	margin: 10px auto;
	width: 300px;
}
</style>

	<div class="transportlist">
		<div align="left" id="transportTools">
			<div style="margin:10px 0px">
				部门： <input id="department" name="dept" style="width: 200px;"></input>
			</div>
			<div>
			<a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-add" id="paiqianUser">派遣人员</a> 
			<a id="seeDetail" href="javascript:void(0)" iconCls="icon-more" class="easyui-linkbutton">查看详情</a>
			</div>
		</div>
		<div id='transportlist'></div>
	</div>
	<!--派遣人员页面  -->
	<div class="easyui-dialog" style="width:500px;height:350px;" closed="true" id="userListDia">
		<div id="userList"></div>
		<div class="butList" style="margin: 20px auto;width:300px">
				<input type="button" name="" value="保存" class="ButtonStyle_Blue"id="save" /> 
				<input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close" /> 
			</div>
	</div>
