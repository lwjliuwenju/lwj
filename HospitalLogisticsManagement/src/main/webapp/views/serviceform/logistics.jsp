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
    <body class="easyui-layout" style="overflow-y: hidden" scroll="no">
    <div>
       <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0" style="width:100%;">
                <tbody>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font> 物流部门名称:</td>
                        <td width="390px">
                            <input type="text" name="" class="easyui-combobox" id="wuliuteam" value="" placeholder="" style="width: 96%;">
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font> 物品名称:</td>
                        <td width="390px">
                            <input type="text" name="" id="wupinName" value="" placeholder="" style="width: 96%;">
                        </td>
                    </tr>
                    <tr>
                        <td width="400px" colspan="2" align="center">注释: 所有加
                            <font color="#ff0000">*</font>为必填项
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="butList" style="margin: 20px auto;width:300px">
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save-call">
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close-call">
            </div>
        </div>
        <script >
        	$(function() {
	        var deps = JSON.parse(sessionStorage.getItem('deps'));
			var host = window.location.host;
			var windowPath = "http://"+host + "/";
			$('#save-call').click(function(){
    	var depid = $('#wuliuteam').combobox('getValue');
    	var proId = $('#serviceFormlist').datagrid('getSelected').id;
    	var goodsName = $('#wupinName').val();
    	var url = windowPath + 'proposer_sendmessager.action';
    	$.post(url,{depid:depid,goodsName:goodsName,proId:proId},function(data){
    		 var code = JSON.parse(data).code_;
             if (code == '0') {
             	$.messager.alert('系统提示', '物流请求信息已发出!', 'info',function(){
             	location.reload();
             	});
             } else {
                $.messager.alert('系统提示', '物流请求信息发送失败，请重试!', 'error');
             }
    		});
    	});
    	 $('#close-call').click(function(){
    	$('#callLogistics').dialog('close');
    		});
     $('#wuliuteam').combobox({
    	 url:windowPath+'dep_findByTransport.action' ,
         valueField: 'id',
         textField: 'text',
         filter: filterCombo,
         onLoadSuccess:function(){
         	 var data = $(this).combobox('getData');
              if (data.length > 0) {
                  $(this).combobox('select', data[0].id);
              } 
         }
    });
        });
       
	
        </script>
    </body>

</html>
