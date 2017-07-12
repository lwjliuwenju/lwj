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
<script type="text/javascript" src="../../common/staff/index.js"></script>
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
<div class="departmentIndex">
		<div align="left" id="departmentTools">
				<div style="margin:10px 0px">
				部门： <input id="department" name="dept" style="width: 200px;"></input>
			</div>
		    <a id="insertkeyuan" iconCls="icon-add" href="javascript:void(0)" class="easyui-linkbutton">添加科员</a>
		</div>
		<div class="departmentlist">
			<div id='departmentlist'></div>
			</div>
		</div>
		</div>
		<div id="dd2" class="easyui-dialog" title="科室人员" closed="true"
		style="min-width:300px;min-height:300px;width:400px;height:400px">
		<table class="easyui-datagrid" id="dg2"></table>
		<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="del()" data-options="iconCls:'icon-remove'">删除</div>
	</div>
</div>
	<!--添加人员  -->
      <div class="easyui-dialog" title="添加人员" closed="true" style="width:500px;height:400px;" id="addUserDia">  
         <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0">
            <tbody>
                 <tr>
                    <td class="PopUpTableTitle" width="200">
                        <font color="#ff0000">*</font>
                      科员名称:
                    </td>
                    <td width="390px">
                         <input  id ="keyuanName" type="text">
                    </td>
                </tr>
                <tr>
						<td class="PopUpTableTitle" width="200"><font
							color="#ff0000"></font> 手机号:</td>
						<td width="390px"><input type="text" name="" class="phone" ></td>
					</tr>
                  <tr>
                    <td  width="400px" colspan="2" align="center">
                       注释: 所有加<font color="#ff0000">*</font>为必填项
                    </td>
                </tr>
            </tbody>
        </table>
	    <div class="butList">
	     <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save-addUser">
	        <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close-addUser">
	    </div>
    </div>
