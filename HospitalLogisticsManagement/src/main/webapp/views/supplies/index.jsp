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
<script type="text/javascript" src="../../common/supplies/index.js"></script>
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
<div class="supplieslist1">
	<div class="supplieslist">
		<div align="left" id="suppliesTools">
			<div style="margin:10px 0px">
				部门： <input id="department" name="dept" style="width: 200px;"></input>
			</div>
			<a href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-add"
				onclick="$('#insertsuppliesDialog').dialog('open')">增加</a> 
			<a id="editsupplies" href="javascript:void(0)" iconCls="icon-edit" class="easyui-linkbutton">编辑</a>
			<a id="detelesupplies" href="javascript:void(0)" iconCls="icon-remove"
				class="easyui-linkbutton" iconCls="icon-del" >删除</a> 
		</div>
		<div id='supplieslist'></div>
		<div id="insertsuppliesDialog" class="easyui-dialog" title="添加耗材"
			data-options="iconCls:'icon-save'" closed="true"
			style="width:500px;height:300px;padding:10px;">
			<table class="PopUpTableBorder" border="0" cellspacing="0"
				cellpadding="0">
				<tbody>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 耗材名称:</td>
						<td width="390px"><input type="text" name="" class="suppliesname"
							value="" placeholder="" style="width: 96%;"></td>
					</tr>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 耗材价值:</td>
						<td width="390px"><input type="text" name="" class="suppliesvalue"
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
	                    <td  width="400px" colspan="2" align="center">
	                       注释: 所有加<font color="#ff0000">*</font>为必填项
	                    </td>
	                </tr>
				</tbody>
			</table>
			<div class="butList" style="margin: 20px auto;width:300px">
				<input type="button" name="" value="保存" class="ButtonStyle_Blue"
					id="save_close" /> 
				<input type="button" name="" value="关闭"
					class="ButtonStyle_Blue" id="save_continue" /> 
			</div>
		</div>

		<div id="editsuppliesDialog" class="easyui-dialog" title="编辑角色"
			data-options="iconCls:'icon-save'" closed="true"
			style="width:500px;height:300px;padding:10px;">
			<table class="PopUpTableBorder" border="0" cellspacing="0"
				cellpadding="0">
				<tbody>
					<tr data-id="">
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 耗材名称:</td>
						<td width="390px"><input type="text" name=""
							class="suppliesname1" value="" placeholder="" style="width: 96%;">
						</td>
					</tr>
					<tr data-id="">
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 耗材价值:</td>
						<td width="390px"><input type="text" name=""
							class="suppliesvalue1" value="" placeholder="" style="width: 96%;">
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
				<input type="button" name="" value="保存" class="ButtonStyle_Blue"
					id="keep_close"> <input type="button" name="" value="关闭"
					class="ButtonStyle_Blue" id="keep_continue"> 
			</div>
		</div>
	</div>
</div>