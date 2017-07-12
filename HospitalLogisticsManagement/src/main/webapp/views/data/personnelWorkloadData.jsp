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
<script type="text/javascript" src="../../common/data/personnelWorkloadData.js"></script>
<script type="text/javascript" src="../../Js/pinyin.js"></script>
<style>
.PopUpTableBorder {
	border-collapse: collapse;
	border-width: 1px;
	border-style: solid;
	border-color: #a4d5e6;
	-o-border-image: initial;
	border-image: initial;
	background-color: #eef9ff;
	margin: 3px;
}

.PopUpTableBorder .PopUpTableTitle {
	background-color: #eef9ff;
	text-align: right;
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

.PopUpTableBorder .tishi {
	padding-left: 25px;
	font-size: 12px;
	line-height: 22px;
	vertical-align: middle;
	background: url(../imgages/onShow.gif) no-repeat #fff2e9;
}

.PopUpTableBorder .onfocus {
	background: url(../imgages/onBlue.png) no-repeat #fff2e9;
}

.PopUpTableBorder .onerror {
	background: url(../imgages/onError.gif) no-repeat #fff2e9;
}

.PopUpTableBorder .onCurrect {
	background: url(../imgages/onCorrect.gif) no-repeat #fff2e9;
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
		<div align="left" id="dataTools">
			部门： <input id="department" name="dept" style="width: 200px;"></input>
			开始时间: <input id="startTime" name="startTime" class="easyui-datebox"></input>
			结束时间: <input id="endTime" name="endTime" class="easyui-datebox"></input>
			<a href="#" type="button" id="query" class="easyui-linkbutton" iconCls="icon-search">Query&nbsp;</a>
			<a id="downloaddata" href="javascript:void(0)" iconCls="icon-print" class="easyui-linkbutton">下载数据</a>
		</div>
		<div id='workLoadlist'></div>
		</div>
