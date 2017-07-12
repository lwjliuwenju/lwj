<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="../../css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="../../Js/themes/default/easyui.css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="../../Js/themes/icon.css" />
<script type="text/javascript" src="../../Js/jquery.min.js"></script>
  <!--[if lt IE 9]>
   <script type="text/javascript" src='../../Js/json2.js'></script>
   <![endif]-->
<script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../Js/vilidate.js"></script>
<script type="text/javascript" src="../../Js/pinyin.js"></script>
<script type="text/javascript" src="../../common/role/index.js"></script>
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

.PopUpTableBorder td {
	border-collapse: collapse;
	line-height: 30px;
	border-width: 1px;
	border-style: solid;
	border-color: #a4d5e6;
	-o-border-image: initial;
	border-image: initial;
}

.PopUpTableBorder .role, .confirm .PopUpTableBorder .username {
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
.topMenuList{
	margin-top:-5px;
	margin-bottom:1px;
}
</style>
<div id='rolelist1'>
<div align="left" id="roleTools" class="topMenuList">
	<a id="insertRole" iconCls="icon-add" href="javascript:void(0)"
		class="easyui-linkbutton"
		onclick="$('#insertRoleDialog').dialog('open')">增加</a> <a
		id="editRole" iconCls="icon-edit" href="javascript:void(0)"
		class="easyui-linkbutton">编辑</a> <a id="deteleRole"
		iconCls="icon-remove" href="javascript:void(0)"
		class="easyui-linkbutton">删除</a> <a id="impowerRole"
		iconCls="icon-user-config" href="javascript:void(0)"
		class="easyui-linkbutton">功能授权</a>
		<a id="role_user"
		iconCls="icon-user-config" href="javascript:void(0)"
		class="easyui-linkbutton">角色管理用户</a>
</div>
<div id='rolelist'></div>
<div id="insertRoleDialog" class="easyui-dialog" title="添加角色"
	data-options="iconCls:'icon-save'" closed="true"
	style="width:500px;height:300px;padding:10px;">
	<table class="PopUpTableBorder" border="0" cellspacing="0"
		cellpadding="0">
		<tbody>
			<tr>
				<td class="PopUpTableTitle" width="100px"><font color="#ff0000">*</font>
					角色名:</td>
				<td width="390px"><input type="text" name="" class="rolename easyui-validatebox"
					value="" placeholder="" style="width: 96%;" data-options="required:true,validType:'minLength'"></td>
			</tr>
			<tr>
				<td width="400px" colspan="2" align="center">注释: 所有加<font
					color="#ff0000">*</font>为必填项
				</td>
			</tr>
		</tbody>
	</table>
	<div class="butList" style="margin: 20px auto;width:300px">
		<input type="button" name="" value="保存" class="ButtonStyle_Blue"
			id="save_close"> 
			 <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close">
	</div>
</div>

<div id="editRoleDialog" class="easyui-dialog" title="编辑角色"
	data-options="iconCls:'icon-save'" closed="true"
	style="width:500px;height:300px;padding:10px;">
	<table class="PopUpTableBorder" border="0" cellspacing="0"
		cellpadding="0">
		<tbody>
			<tr data-id="">
				<td class="PopUpTableTitle" width="100px"><font color="#ff0000">*</font>
					角色名:</td>
				<td width="390px"><input type="text" name="" class="rolename1 easyui-validatebox"
					value="" placeholder="" style="width: 96%;" data-options="required:true,validType:'username'"></td>
			</tr>
			<tr>
				<td width="400px" colspan="2" align="center">注释: 所有加<font
					color="#ff0000">*</font>为必填项
				</td>
			</tr>
		</tbody>
	</table>
	<div class="butList" style="margin: 20px auto;width:300px">
		<input type="button" name="" value="保存" class="ButtonStyle_Blue"
			id="keep_close"> 
			<input
			type="button" name="" value="关闭" class="ButtonStyle_Blue"
			id="shutdown">

	</div>
</div>
<!--功能授权  -->
<div class="easyui-dialog" id="impower" class="easyui-dialog"
	title="功能授权" closed="true" style="width:500px;padding:10px;">
	<div>
		<input type="button" name="" value="保存" class="ButtonStyle_Blue"
			id="baocun"> <input type="button" name="" value="关闭"
			class="ButtonStyle_Blue" id="guanbi">
	</div>
	<div id="impowerRoledg"></div>
</div>
<!-- 角色管理用户 -->
<div class="easyui-dialog" id="role_userDia" class="easyui-dialog"
	title="角色管理用户" closed="true" style="width:500px;padding:10px;">
	<div class="butList">
		<input type="button" name="" value="保存" class="ButtonStyle_Blue"
			id="role_user_save"> <input type="button" name="" value="关闭"
			class="ButtonStyle_Blue" id="role_user_close">
	</div>
	<div id="role_user_datagrid"></div>
</div>
</div>
