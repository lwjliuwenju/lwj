<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title></title>
<link href="../../css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="../../Js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../Js/themes/icon.css" />
<script type="text/javascript" src="../../Js/jquery.min.js"></script>
<!--[if lt IE 9]>
   <script type="text/javascript" src='../../Js/json2.js'></script>
   <![endif]-->
   <script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../Js/vilidate.js"></script>
<script type="text/javascript" src="../../Js/pinyin.js"></script>
<script type="text/javascript" src="../../common/projectform/index.js"></script>
<style type="text/css">
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
	border-radius: 5px;
}
</style>
</head>

<body>
	<div class="responsedepartmentIndex">
		<div align="left" id="responsedepartmentTools">
			<div style="margin:10px 0px">
				部门： <input id="department" name="dept" style="width: 200px;"></input>
			</div>
			<div>
				<a id="addProject" href="javascript:void(0)"iconCls="icon-add"
					class="easyui-linkbutton">添加服务</a>
					 <a id="editProject" href="javascript:void(0)" iconCls="icon-edit" class="easyui-linkbutton">修改服务</a> <a
					id="delProject" href="javascript:void(0)" iconCls="icon-remove"class="easyui-linkbutton">删除服务</a>
			</div>
		</div>
		<div class="projectList">
			<div id='projectList'></div>
		</div>
		<div id="menu1" class="easyui-menu" style="width:120px;">
		<div onclick="reg()" data-options="iconCls:'icon-add'">登记</div>
		</div>
		<div id="addProjectDialog" class="easyui-dialog" title="添加服务"
			data-options="iconCls:'icon-save'" closed="true"
			style="width:500px;height:300px;padding:10px;">
			<table class="PopUpTableBorder" border="0" cellspacing="0"
				cellpadding="0">
				<tbody>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 服务名:</td>
						<td width="390px"><input type="text" name="" id="projectname"
							value="" placeholder="" style="width: 96%;"></td>
					</tr>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 服务分值:</td>
						<td width="390px">
						<input type="text" class="easyui-numberbox"  data-options="min:0" id="projectNum"
							value="" placeholder="" style="width: 96%;"></td>
					</tr>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 部门:</td>
						<td width="390px">
						<input type="text" name="" id="depname"
							value="" placeholder="" style="width: 96%;"></td>
					</tr>
					<tr>
                    <td class="PopUpTableTitle" width="150px">
                        <font color="#ff0000">*</font>
                        标准工时:
                    </td>
                    <td width="390px">
                        <input id="standardtime" type="number">小时
                    </td>
                </tr>
                  <tr>
                    <td  width="400px" colspan="2" align="center">
                       注释: 所有加<font color="#ff0000">*</font>为必填项
                    </td>
                </tr>
				</tbody>
			</table>
			<div class="butList" style="margin: 20px auto;width:300px">
				<input type="button" name="" value="保存" class="ButtonStyle_Blue"id="save1"> 
				<input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close">
			</div>
		</div>


		<div id="editProjectDialog" class="easyui-dialog" title="修改服务"
			data-options="iconCls:'icon-save'" closed="true"
			style="width:500px;height:300px;padding:10px;">
			<table class="PopUpTableBorder" border="0" cellspacing="0"
				cellpadding="0">
				<tbody>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 服务名:</td>
						<td width="390px"><input type="text" name="" id="projectName"
							value="" placeholder="" style="width: 96%;"></td>
					</tr>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 服务分值:</td>
						<td width="390px">
						<input type="text" class="easyui-numberbox"  data-options="min:0" id="projectnum"
							value="" placeholder="" style="width: 96%;"></td>
					</tr>
					<tr>
                    <td class="PopUpTableTitle" width="150px">
                        <font color="#ff0000">*</font>
                        标准工时:
                    </td>
                    <td width="390px">
                        <input id="standardHour" type="number">小时
                    </td>
                </tr>
					  <tr>
                    <td  width="400px" colspan="2" align="center">
                       注释: 所有加<font color="#ff0000">*</font>为必填项
                    </td>
                </tr>
				</tbody>
			</table>
			<div class="butList" style="margin: 20px auto;width:300px">
				<input type="button" name="" value="保存" iconCls="icon-add" class="ButtonStyle_Blue"id="keep">
			    <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="shut">
			</div>
		</div>
</body>

</html>
