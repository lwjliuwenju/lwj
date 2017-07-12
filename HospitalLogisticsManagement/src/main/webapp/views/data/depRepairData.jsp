<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
  <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="../../Js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="../../Js/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="../../common/user/addUser.css" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <script type="text/javascript" src="../../Js/jquery.min.js"></script>
  <script type="text/javascript" src='../../Js/json2.js'></script>
  <script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../common/data/depairData.js"></script>
<script type="text/javascript" src="../../Js/pinyin.js"></script>
		<div class="departmentlist">
			<div id='fanxiulist'></div>
			<div align="left" id="fanxiuTools">
			<div style="margin:10px 0px">
				部门： <input id="department" name="dept" style="width: 200px;"></input>
				开始时间: <input id="startTime" name="startTime" class="easyui-datebox"></input>
				结束时间: <input id="endTime" name="endTime" class="easyui-datebox"></input>
				<a href="#" type="button" id="query" class="easyui-linkbutton" iconCls="icon-search">查询&nbsp;</a>
			</div>
			</div>
