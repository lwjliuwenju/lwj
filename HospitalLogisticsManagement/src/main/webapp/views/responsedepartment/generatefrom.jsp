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
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            申请人:
                        </td>
                        <td width="390px">
                            <input id="proposerName" type="text" disabled="disabled" class="width"></input>
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            申请部门：
                        </td>
                        <td width="390px">
                            <input id="shenqingdepartment2" ></input>
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            响应科室名称:
                        </td>
                        <td width="500px">
                            <input id="responseDeptName" name="dept" ></input>
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            申请内容:
                        </td>
                        <td width="390px">
                            <textarea id="mark" class="width"></textarea>
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
             <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save_add"></input>
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close_add"></input>
        </div>
        </div>
        <script >
        	$(function(){
        	  		var host = window.location.host;
				    var windowPath = "http://" + host + "/";
				    var username = sessionStorage.getItem('username');
				    $("#proposerName").val(username);
				    var deps = JSON.parse(sessionStorage.getItem('deps'));
				    	  deps.unshift({ "text": "请选择", "id": 0 });
        		    $.post(windowPath+'dep_getdepartment.action',function(dataT){
			    	var data = JSON.parse(dataT);
			  	  	data.unshift({ "text": "请选择", "id": 0 });
			    	$('#shenqingdepartment2').combobox({
			    		width:300,
			        	data: data, 
			            valueField: 'id',
			            textField: 'text',
			            filter: filterCombo
			        });
    			});
    			 $('#responseDeptName').combobox({
			        data: deps,
			        width:300,
			        valueField: 'id',
			        textField: 'text',
			        filter: filterCombo,
			        onLoadSuccess:function(){
			       	 	var data = $(this).combobox('getData');
			            if (data.length > 0) {
			                //$(this).combobox('select', data[0].text);
			                $(this).combobox('select', data[0].id);
			            } 
			       }
			    });
			     $("#save_add").click(function() {
			        var mark = $("#mark").val();
			        var responseDeptid = $("#responseDeptName").combobox('getValue');
			        var sendDeptId = $("#shenqingdepartment2").combobox('getValue');
			        var url2 = windowPath+'proposer_saveProposer.action';
			        $.post(url2, {
			                mark: mark,
			                responseDeptid: responseDeptid,
			                sendDeptId: sendDeptId
			            },
			            function(data) {
			                var code = JSON.parse(data).code_;
			                if (code == '0') {
			                	 $.messager.alert('系统提示', '生成成功', 'info',function(){
			                		 location.reload();
			                	 });
			                } else {
			                	 $.messager.alert('系统提示', '生成失败', 'error');
			                }
			            });
    				});
			    $('#close_add').click(function(){
			    	$('#addServiceform').dialog('close');
			    });
        	})
        </script>
    </body>

</html>
