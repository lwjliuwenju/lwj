<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="../../css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="../../Js/themes/default/easyui.css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="../../Js/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="../../common/user/addUser.css" />
<script type="text/javascript" src="../../Js/jquery.min.js"></script>
 <!--[if lt IE 9]>
   <script type="text/javascript" src='../../Js/json2.js'></script>
   <![endif]-->
<script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../Js/vilidate.js"></script>
<script type="text/javascript" src="../../Js/pinyin.js"></script>
<script type="text/javascript" src="../../common/user/index.js"></script>
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
<div class="userlist1">

	<div class="userlist">
		<div align="left" id="userTools">
	<div style="margin:4px 0px 0px 0px">
	工号:<input id="account" style="width:200px;margin:0px;">
	姓名:<input id="user_name" style="width:200px;margin:0px;">
	手机号:<input id="phone" style="width:200px;margin:0px;">
	<a id="query" iconCls="icon-search" href="javascript:void(0)"
				class="easyui-linkbutton"
				>查询</a>
	</div>
		
			<a id="insertUser" iconCls="icon-add" href="javascript:void(0)"
				class="easyui-linkbutton"
				onclick="$('#insertUserDialog').dialog('open')">增加</a> <a
				id="editUser" iconCls="icon-edit" href="javascript:void(0)"
				class="easyui-linkbutton">编辑</a> <a id="deteleUser"
				iconCls="icon-remove" href="javascript:void(0)"
				class="easyui-linkbutton">删除</a> <a id="impowerRole"
				iconCls="icon-user-config" href="javascript:void(0)"
				class="easyui-linkbutton">授权角色</a> <a id="restPass"
				iconCls="icon-edit" href="javascript:void(0)"
				class="easyui-linkbutton">密码重置</a>
		</div>
		<div id='userlist'></div>
		<div id="insertUserDialog" class="easyui-dialog" title="添加用户"
			data-options="iconCls:'icon-save'" closed="true"
			style="width:500px;height:350px;padding:10px;">
			<table class="PopUpTableBorder" border="0" cellspacing="0"
				cellpadding="0">
				<tbody>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 工号:</td>
						<td width="390px"><input type="text" name="" class="jobNumber easyui-validatebox"
							value="" placeholder="" style="width: 96%;" data-options="required:true,validType:'length[1,6]'"></td>
					</tr>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 用户名:</td>
						<td width="390px"><input type="text" name="" class="username easyui-validatebox"
							value="" placeholder="" style="width: 96%;" data-options="required:true,validType:'minLength'" ></td>
					</tr>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 密码:</td>
						<td width="390px"><input type="text" name="" class="password easyui-validatebox"
							value="" placeholder="" style="width: 96%;" data-options="required:true,validType:'length[1,6]'"></td>
					</tr>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000"></font> 手机号:</td>
						<td width="390px"><input type="text" name="" class="phone easyui-validatebox"
							value="" placeholder="" style="width: 96%;"data-options="required:true,validType:'mobile'" ></td>
					</tr>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 性别:</td>
						<td width="390px"><label>男 <input type="radio"
								name="sex" checked="checked" class="boy" value="男">
						</label> <label>女 <input type="radio" name="sex" class="girl" value="女" >
						</label></td>
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
					id="save_close">  <input
					type="button" name="" value="关闭" class="ButtonStyle_Blue"
					id="close">

			</div>
		</div>

		<div id="editUserDialog" class="easyui-dialog" title="编辑用户"
			data-options="iconCls:'icon-save'" closed="true"
			style="width:500px;height:350px;padding:10px;">
			<table class="PopUpTableBorder" border="0" cellspacing="0"
				cellpadding="0">
				<tbody>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 工号:</td>
						<td width="390px"><input type="text" name="" class="jobNumber1 easyui-validatebox"
							value="" placeholder="" style="width: 96%;" data-options="required:true,validType:'length[1,6]'"></td>
					</tr>
					<tr data-id="">
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 用户名:</td>
						<td width="390px"><input type="text" name=""
							class="username1" value="" placeholder="" style="width: 96%;">
						</td>
					</tr>
					
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000"></font> 手机号:</td>
						<td width="390px"><input type="text" name="" class="phone1"
							value="" placeholder="" style="width: 96%;"></td>
					</tr>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 性别:</td>
						<td width="390px"><label>男 <input type="radio"
								name="sex" class="boy1" value="男"
								placeholder="">
						</label> <label>女 <input type="radio" name="sex" class="girl1"
								value="女" placeholder="">
						</label></td>
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
					id="keep_close">  <input
					type="button" name="" value="关闭" class="ButtonStyle_Blue"
					id="shutdown">

			</div>
		</div>
	</div>
	<div id="dd" class="easyui-dialog" title="角色授权管理" closed="true"
		style="min-width:300px;min-height:300px;width:400px;height:400px">
		<table class="easyui-datagrid" id="dg"></table>
		<div align="center" id="impowerTools" style="margin-top:10px;">
			<a id="saveimpower" href="javascript:void(0)"
				class="easyui-linkbutton ButtonStyle_Blue">保存</a> <a id="cancelimpower"
				href="javascript:void(0)" class="easyui-linkbutton ButtonStyle_Blue">关闭</a> <a
				id="selectall" href="javascript:void(0)" class="easyui-linkbutton ButtonStyle_Blue">全选</a>
			<a id="cancelselect" href="javascript:void(0)"
				class="easyui-linkbutton ButtonStyle_Blue">反选</a>
		</div>
	</div>
</div>