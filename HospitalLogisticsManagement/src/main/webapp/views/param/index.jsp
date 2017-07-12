<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<script type="text/javascript" src="../../common/param/index.js"></script>
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
<div id="paramlist"></div>
<div class="easyui-dialog" title="参数管理" closed="true" id="paramDiv" style="width:500px;height:300px;">
<table class="PopUpTableBorder" border="0" cellspacing="0"
	cellpadding="0">
	<tbody>
		<tr>
			<td class="PopUpTableTitle" width="100px"><font color="#ff0000">*</font>
				参数值:</td>
			<td width="390px">
			<input type="text" name="" id="editparam"
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
     <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save">
     <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close">     
</div>
</div>
