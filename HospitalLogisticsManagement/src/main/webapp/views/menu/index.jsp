<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title></title>
      <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="../../Js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="../../Js/themes/icon.css" />
     <script type="text/javascript" src="../../Js/jquery.min.js"></script>
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
       <!--[if lt IE 9]>
   <script type="text/javascript" src='../../Js/json2.js'></script>
   <![endif]-->
  <script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="../../Js/vilidate.js"></script>
<script type="text/javascript" src="../../Js/pinyin.js"></script>
  <script type="text/javascript" src="../../common/menu/index.js"></script>
  <style type="text/css">
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
    .PopUpTableBorder td{
        padding: 0px 10px;
    }
    .butList {
        margin: 10px auto;
        width: 300px;
    }
  </style>
</head>

<body>
   <div class="menutree">
			<div id='menutree'></div>
			</div>
		<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="edit()" data-options="iconCls:'icon-edit'">修改</div>
		<div onclick="add()" data-options="iconCls:'icon-add'">增加</div>
	</div>
</body>

</html>
