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
         <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0">
                <tbody>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font>
                            完成时间:
                        </td>
                        <td width="390px">
                            <input id="endtime" type="text" disabled="disabled" class="width100">
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font>
                            响应时间:
                        </td>
                        <td width="390px">
                            <input id="xiangyingtime" type="text" disabled="disabled" class="width100">
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font>
                            申请部门:
                        </td>
                        <td width="390px">
                            <input id="proposerDepName" type="text" disabled="disabled" class="width100">
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font>
                            评价:
                        </td>
                        <td width="390px">
                            <label>优:
                                <input type="radio" name="a" class="mt" data-option="1" checked="checked">
                            </label>
                            <label>良:
                                <input type="radio" name="a" class="mt" data-option="2">
                            </label>
                            <label>中:
                                <input type="radio" name="a" class="mt" data-option="3">
                            </label>
                            <label>差:
                                <input type="radio" name="a" class="mt" data-option="4">
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="150px">
                            <font color="#ff0000">*</font>
                            评价内容:
                        </td>
                        <td width="390px">
                            <textarea id="mark1" class="width100"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td width="400px" colspan="2" align="center">
                            注释: 所有加
                            <font color="#ff0000">*</font>为必填项
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="butList">
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save">
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close">
            </div>
        </div>
        <script >
        	$(function(){
        	    var host = window.location.host;
   				 var windowPath = "http://" + host + "/";
        	  	 var row = $("#serviceFormlist").datagrid('getSelected');
             	var proposerName = $("#proposerName").val(row.depName);
           	  var e =row.endTime;
             var s;
             var startTime = row.responseTime;
             if(startTime != null)
             	s = startTime;
             else
             	s = e;
             $("#endtime").val(e);
             $("#xiangyingtime").val(s);
             $("#proposerDepName").val(row.depName);
              $("#save").click(function() {
            var url2 = windowPath+'proposer_evaluate.action';
            var row = $("#serviceFormlist").datagrid('getSelected');
            var appraise = $('.mt:radio:checked').attr('data-option');
	        var appraiseMark = $('#mark1').val();
	        var proposerId = row.id;
            $.post(url2, {'appraise':appraise,'appraiseMark':appraiseMark,'proposerId':proposerId},
                function(data) {
                    var code = JSON.parse(data).code_;
                    if (code == '0') {
                    	$.messager.alert('系统提示', '评价成功', 'info',function(){
                    		   location.reload();
                    	});
                    } else {
                    	$.messager.alert('系统提示', '评价失败', 'error');
                    }
                });
        });
        $("#close").click(function(){
           	$('#evaluateform').dialog('close');
        });
             });
        </script>
    </body>

</html>
