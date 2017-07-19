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
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 服务名:</td>
						<td width="390px"><input type="text" name="" id="aaa"
							value="" placeholder="" style="width: 96%;"></td>
					</tr>
					<tr>
						<td class="PopUpTableTitle" width="100px"><font
							color="#ff0000">*</font> 服务分值:</td>
						<td width="390px">
						<input type="text" class="easyui-numberbox"  data-options="min:0" id="bbb"
							value="" placeholder="" style="width: 96%;"></td>
					</tr>
					<tr>
                    <td class="PopUpTableTitle" width="150px">
                        <font color="#ff0000">*</font>
                        标准工时:
                    </td>
                    <td width="390px">
                        <input id="ccc" type="number">小时
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
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save_pro"></input>
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close_pro"></input>
            </div>
        </div>
        <script >
        	$(function() {
	             		var host = window.location.host;
						var windowPath = "http://"+host + "/";
						var row =$("#projectList").treegrid('getSelected');
					  $("#save_pro").click(function(){
					  	var projectId =row.id;
						var Rid =row.Rid;
						var projectName =$("#aaa").val();
						var grade =$("#bbb").numberbox("getValue"); 
						var standardHour =$("#ccc").val();
						var url = windowPath + 'project_addyiproject.action';
						var param ={projectId:projectId,projectName:projectName,grade:grade,standardHour:standardHour,Rid:Rid};
						$.post(url,param,function(data){
						  var code = JSON.parse(data).code_;
				                if (code == '0') {
				                	 $.messager.alert('系统提示', '添加成功', 'info',function(){
				                		 location.reload();
				                	 });
				                } else {
				                	 $.messager.alert('系统提示', '添加失败', 'error');
				                }
						});
					  });
					  $("#close_pro").click(function(){
					  	$('#serviceDialog').dialog('close');
					  });
        });
       
	
        </script>
    </body>

</html>
