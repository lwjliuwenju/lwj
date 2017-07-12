<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title></title>
    <link href="/Css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/js/themes/icon.css" />
	 <script type="text/javascript" src="../../Js/jquery.min.js"></script>
  <script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src='/js/outlook2.js'>
    </script>
    <script type="text/javascript" src="/common/serviceform/evaluateform.js"></script>
    <style type="text/css" media="screen">
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
    
    .PopUpTableBorder .role,
    .confirm .PopUpTableBorder .username {
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
        border-radius: 5px;
    }
    .PopUpTableBorder td{
        padding: 0px 10px;
    }
    .butList {
        margin: 10px auto;
        width: 300px;
    }
    .mt{
    display:inline-block;
    margin-top:2px;
    }
    </style>

    <body class="easyui-layout" style="overflow-y: hidden" scroll="no">
        <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0">
            <tbody>
             <tr>
                    <td class="PopUpTableTitle" width="150px">
                        <font color="#ff0000">*</font>
                      	返修状态:
                    </td>
                    <td width="390px">
                       <label>返修:<input  class= "checkStatus" type="radio"  name="c"checked="checked" data-value="1"/></label>
                       <label>不返修:<input class= "checkStatus"  type="radio"  name="c"data-value="0"/></label>
                    </td>
                </tr>
                <tr>
                    <td class="PopUpTableTitle" width="150px">
                        <font color="#ff0000">*</font>
                      	返修原因:
                    </td>
                    <td width="390px">
                       <textarea id="content"></textarea>
                    </td>
                </tr>
  				      <tr>
                    <td  width="400px" colspan="2" align="center">
                       注释: 所有加<font color="#ff0000">*</font>为必填项
                    </td>
                </tr>            
            </tbody>
        </table>
        <div class="butList">
            <input type="button" name="" value="返修" class="ButtonStyle_Blue" id="fanxius">
            <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close">
        </div>
        <script >
        	$(function() {
        		var rows = $("#serviceFormlist").datagrid('getSelected');
    			var id = rows.ID_;
        		var host = window.location.host;
				var windowPath = "http://"+host + "/";
        		$("#fanxius").click(function(){
        		var content =$("#content").val();
    			var checkStatus = $('.checkStatus:radio:checked').attr('data-value');
    			var url2 = windowPath+'proposer_fanxiu.action';    
    			$.post(url2,{'id':id,'content':content,'checkStatus':checkStatus},function(data){
    				var code = JSON.parse(data).code_;
           			 if (code == '0') {
                	location.reload();
                	alert('返修成功');
           			 } else {
                	alert('返修失败,请重试!');
           				 }
    				});
        		});
        	  $("#close").click(function(){
        	     $("#fanxiuDialog").dialog('close');
        	  });
        });
        </script>
    </body>

</html>
