<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="../../css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="../../Js/themes/default/easyui.css" />
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

.timeline-header {
	margin: 0;
	color: #00c0ee;
	border-bottom: 1px solid #f4f4f4;
	padding: 10px;
	font-size: 16px;
	line-height: 1.1;
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

li {
	list-style: none;
}

.timeline-body {
	padding: 10px;
}

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
<script type="text/javascript"
	src="../../common/responsedepartment/index.js"></script>
<div class="responsedepartmentIndex">
	<div align="left" id="responsedepartmentTools">
		<div style="margin:2px 0;">
			申&nbsp;&nbsp;&nbsp;&nbsp;请&nbsp;&nbsp;&nbsp;&nbsp;人:<input
				id="submitperson" name="dept" style="width:200px"></input>
			状&nbsp;&nbsp;&nbsp;&nbsp;态&nbsp： <SELECT id='state'
				class="easyui-combobox" style="width: 200px;">
				<option value="0">请选择</option>
				<option value="1">异常</option>
				<option value="2">待响应</option>
				<option value="3">响应中</option>
				<option value="4">已完成</option>
				<option value="5">已终止</option>
				<option value="6">已评价</option>
			</SELECT> 排序方式： <SELECT id='shunxu' class="easyui-combobox"
				style="width: 200px;">
				<option value="0">请选择</option>
				<option value="1">状态</option>
				<option value="2">时间</option>
			</SELECT>
		</div>
		<div style="margin:2px 0;">
			申请单部门：<input id="shenqingdepartment" name="dept"
				style="width: 200px; margin:0;"></input> 响应人员：<input
				id="zhixingperson" name="dept" style="width: 200px; margin:0;"></input>
			响应部门：<input id="jieshoubumen" name="dept"
				style="width: 200px; margin:0;"></input>
		</div>
		<div style="margin:2px 0;">
			开&nbsp始&nbsp时&nbsp间: <input id="startTime" name="startTime"
				class="easyui-datebox" style="width: 200px; margin:0;"></input>
			结&nbsp束&nbsp时&nbsp间: <input id="endTime" name="endTime"
				class="easyui-datebox" style="width: 200px; margin:0;"></input>
		</div>
		<div>
			<a id="searchResponse" href="javascript:void(0)"
				iconCls="icon-search" class="easyui-linkbutton">查询</a> <a
				id="seeDetail" href="javascript:void(0)" iconCls="icon-more"
				class="easyui-linkbutton">查看详情</a> <a id="breakform"
				href="javascript:void(0)" iconCls="icon-cancel"
				class="easyui-linkbutton">终止</a> <a id="sendrenyuan"
				href="javascript:void(0)" iconCls="icon-man"
				class="easyui-linkbutton">派遣人员</a> <a id="fanxiu"
				href="javascript:void(0)" iconCls="icon-redo"
				class="easyui-linkbutton">返修</a> <a id="reassignment"
				href="javascript:void(0)" iconCls="icon-man"
				class="easyui-linkbutton">改派</a> <a id="shenhe"
				href="javascript:void(0)" iconCls="icon-man"
				class="easyui-linkbutton">审核</a> <a id="insertServiceform"
				href="javascript:void(0)" iconCls="icon-add"
				class="easyui-linkbutton">生成申请单</a> <a id="gq"
				href="javascript:void(0)" iconCls="icon-redo"
				class="easyui-linkbutton">挂起</a> <a id="qxgq"
				href="javascript:void(0)" iconCls="icon-undo"
				class="easyui-linkbutton">取消挂起</a>
		</div>
	</div>
	<div class="responsedepartmentlist">
		<div id='responsedepartmentlist'></div>
	</div>
	<!-- 生成申请单 -->
	<div class="easyui-dialog" title="生成申请单" id="addServiceform"
		closed="true" style="width:500px;height:350px">
		<table class="PopUpTableBorder" border="0" cellspacing="0"
			cellpadding="0">
			<tbody>
				<tr>
					<td class="PopUpTableTitle" width="200px"><font
						color="#ff0000">*</font> 申请人:</td>
					<td width="390px"><input id="proposerName" type="text"
						disabled="disabled" value="${ username}"></input></td>
				</tr>
				<tr>
					<td class="PopUpTableTitle" width="200px"><font
						color="#ff0000">*</font> 申请部门：</td>
					<td width="390px"><input id="shenqingdepartment2"
						style="width:130px;margin:0px;"></input></td>
				</tr>
				<tr>
					<td class="PopUpTableTitle" width="200px"><font
						color="#ff0000">*</font> 响应科室名称:</td>
					<td width="500px"><input id="responseDeptName" name="dept"
						style="width:130px;"></input></td>
				</tr>
				<tr>
					<td class="PopUpTableTitle" width="200px"><font
						color="#ff0000">*</font> 申请内容:</td>
					<td width="390px"><textarea id="mark"></textarea></td>
				</tr>
				<tr>
					<td width="400px" colspan="2" align="center">注释: 所有加<font
						color="#ff0000">*</font>为必填项
					</td>
				</tr>
			</tbody>
		</table>
		<div class="butList">
			<input type="button" name="" value="保存" class="ButtonStyle_Blue"
				id="save_add"></input> <input type="button" name="" value="关闭"
				class="ButtonStyle_Blue" id="close_add"></input>
		</div>
	</div>
	<div id="dd" class="easyui-dialog" title="派遣人员" closed="true"
		style="min-width:300px;min-height:300px;width:400px;height:400px">
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