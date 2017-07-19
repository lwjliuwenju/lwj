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
                            响应科室名称:
                        </td>
                        <td width="500px">
                            <input id="responsedepname" name="dept"></input>
                        </td>
                    </tr>
                    <tr>
                        <td class="PopUpTableTitle" width="200px">
                            <font color="#ff0000">*</font>
                            响应科室项目:
                        </td>
                        <td width="500px">
                            <input id="responseDeptitem" ></input>
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
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save_pro"></input>
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close_pro"></input>
            </div>
        </div>
        <script >
        	$(function() {
	        var deps = JSON.parse(sessionStorage.getItem('deps'));
			var host = window.location.host;
			var windowPath = "http://"+host + "/";
			$('#responsedepname').combobox({
		url: windowPath+'dep_getdepartment.action', 
		width:300,
		valueField: 'id',
		textField: 'text',
		editable:true,
		onSelect: function(data){
			var url=windowPath+'project_itemtree.action?id='+data.id;
			$('#responseDeptitem').combotree('reload',url);
		}  
	});
	$('#responseDeptitem').combotree({
	width:300
	});
	$('#save_pro').click(function(){
		var proId =$("#serviceFormlist").datagrid("getSelected").id;
		var responseDeptid = $("#responsedepname").combobox('getValue');
		var responseDeptitem = $("#responseDeptitem").combobox('getText');
		var ProposerId = $("#responseDeptitem").combotree('getValue');
		var url2 = windowPath+'proposer_saveproject.action';
		$.post(url2, {
			responseDeptitem: responseDeptitem,
			ProposerId:ProposerId,
			responseDeptid:responseDeptid,
			proId:proId
		},
		function(data) {
			var code = JSON.parse(data).code_;
			if (code == '0') {
				$.messager.alert('系统提示', '选择服务成功!', 'info',function(){
					location.reload();
				});
			} else {
				$.messager.alert('系统提示','选择服务失败,请重试!','error');
			}
		});
	});
		$('#close_pro').click(function(){
			$('#selectProject').dialog("close");
		});
        });
       
	
        </script>
    </body>

</html>
