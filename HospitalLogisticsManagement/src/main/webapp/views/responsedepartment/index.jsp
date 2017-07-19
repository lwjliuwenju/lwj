<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
 <!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title></title>
    <link rel="stylesheet" type="text/css" href="../../Js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="../../Js/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="../../css/serviceform.css">
   	<style type="text/css">
   	    .butList {
        margin: 10px auto;
        width: 300px;
    }
    </style>
</head>

<body>
    <div class="responsedepartmentIndex">
	<div id="responsedepartmentTools">
		<div style="margin:2px 0;">
		<label class="desc">申请人:</label><input id="submitperson" name="dept" style="width:200px"></input>
			<label class="desc">状态:</label>
			 <SELECT id='state'
				class="easyui-combobox" style="width: 200px;">
				<option value="0">请选择</option>
				<option value="1">异常</option>
				<option value="2">待响应</option>
				<option value="3">响应中</option>
				<option value="4">已完成</option>
				<option value="5">已终止</option>
				<option value="6">已评价</option>
			</SELECT>
			<label class="desc">排序方式:</label> <SELECT id='shunxu' class="easyui-combobox"
				style="width: 200px;">
				<option value="0">请选择</option>
				<option value="1">状态</option>
				<option value="2">时间</option>
			</SELECT>
		</div>
		<div style="margin:2px 0;">
				<label class="desc">申请部门:</label><input id="shenqingdepartment" name="dept"style="width: 200px; margin:0;"></input> 
				<label class="desc" style="margin-right:2px;">响应人员:</label><input id="zhixingperson" name="dept" style="width: 200px; margin:0;"></input>
				<label class="desc" style="margin-right:4px;">响应部门:</label><input id="jieshoubumen" name="dept"style="width: 200px; margin:0;"></input>
		</div>
		<div style="margin:2px 0;">
			<label class="desc">开始时间:</label><input id="startTime" name="startTime"class="easyui-datebox" style="width: 200px; margin:0;"></input>
			<label class="desc">结束时间:</label> <input id="endTime" name="endTime"class="easyui-datebox" style="width: 200px; margin:0;"></input>
		</div>
			<ul class="buttonList clear">
        	<li>
        		<input type="button" class="search button1" name="" id="searchResponse" value="查询" >
        	</li>
        	<li>
        		<input type="button" class="seeDetail button1" id="seeDetail" name="" value="查看详情" >
        	</li>
        	<li>
        		<input type="button" class="cancel button1" name="" value="终止" id="breakform">
        	</li>
        	<li>
        		<input type="button" name="" class="man button1" value="派遣人员" id="sendrenyuan">
        	</li>
        	<li>
        		<input type="button" name="" class="redo button1" value="返修" id="fanxiu">
        	</li>
        	<li>
        		<input type="button" name="" class="man button1" value="改派"  id="reassignment">
        	</li>
        	<li>
        		<input type="button" name="" class="man button1" value="审核"  id="shenhe">
        	</li>
        	<li>
        		<input type="button" name="" class="add button1" value="生成申请单"  id="insertServiceform">
        	</li>
        	<li>
        		<input type="button" name="" class="redo button1" value="挂起" id="gq">
        	</li>
        	<li>
        		<input type="button" name="" class="undo button1" value="取消挂起" id="qxgq">
        	</li>
        </ul>
	</div>
	<div class="responsedepartmentlist">
		<div id='responsedepartmentlist'></div>
	</div>
	<div id="dd" class="easyui-dialog" title="派遣人员" closed="true"
		style="min-width:300px;min-height:300px;width:600px;height:400px">
		<div id="dgdiv">
			<table class="easyui-datagrid" id="dg"></table>
		</div>
		<div align="center" id="renyuanTools"></div>
		<div align="center">
			<div class="repair">
				<span>是否外修:</span> 是:<input type="radio" name="a" value="1"
					onclick="getRadio()" class="wx"> 否:<input type="radio"
					name="a" value="0" checked="checked" onclick="getRadio()"
					class="wx">
			</div>

			<div class="reason" style="display:none;">
				<span class="repair-reason">外修原因:</span>
				<textarea id="repairReason"></textarea>
			</div>
		</div>
		<div style="width:150px;margin:0 auto">
			<a id="saverenyuan" href="javascript:void(0)"
				class="easyui-linkbutton ButtonStyle_Blue">保存</a> <a
				id="cancelrenyuan" href="javascript:void(0)"
				class="easyui-linkbutton ButtonStyle_Blue">关闭</a>
		</div>
	</div>
		<script type="text/javascript" src='../../Js/json2.js'></script>
        <script type="text/javascript" src="../../Js/jquery.min.js"></script>
        <script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="../../Js/vilidate.js"></script>
        <script type="text/javascript" src="../../Js/pinyin.js"></script>
        <script type="text/javascript"src="../../common/responsedepartment/index.js"></script>
        <script type="text/javascript">
		//判断是否外修
		function getRadio(evt) {
			var evt = evt || window.event;
			var e = evt.srcElement || evt.target;
			//外修
			if (e.value == 1) {
				$('.reason').css({
					'display' : "block"
				});
				$('#dgdiv').hide();
			} else {
				$('.reason').css({
					'display' : "none"
				});
				$('#dgdiv').show();
			}
		}
	</script>
</body>
</html>


      
        
  

	