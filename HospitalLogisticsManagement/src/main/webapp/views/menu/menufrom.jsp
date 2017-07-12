<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link href="/Css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/js/themes/icon.css" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <script type="text/javascript" src="../../Js/jquery.min.js"></script>
  <script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src='/js/outlook2.js'>
    </script>
    <script type="text/javascript" src="/common/serviceform/addServiceform.js"></script>
    <body class="easyui-layout" style="overflow-y: hidden" scroll="no">
        <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0">
            <tbody>
            	 <tr>
                    <td class="PopUpTableTitle" width="250px">
                        <font color="#ff0000">*</font>
                      	菜单名称:
                    </td>
                    <td width="390px">
						<input  type="text" id="menuname"/>
                    </td>
                </tr>
                <tbody>
            	 <tr>
                    <td class="PopUpTableTitle" width="150px">
                        <font color="#ff0000">*</font>
                      	上级菜单:
                    </td>
                    <td width="390px">
							<input id="topmenu" name="dept" >
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
            <input type="button" name="" value="提交" class="ButtonStyle_Blue" id="submitform">
            <input type="button" name="" value="取消" class="ButtonStyle_Blue" id="cancelform">
        </div>
        <script >
                    $(function(){
                    		var host = window.location.host;
							var windowPath = "http://"+host + "/";
                    		var row = $("#menutree").treegrid('getSelected');
                    		$("#menuname").val(row.name);
                    		$('#topmenu').combobox({
                    		url:windowPath+'menu_getTopMenu.action',
    						valueField:'id',
    						textField:'text'
							});
							$("#submitform").click(function(){
								var row = $("#menutree").treegrid('getSelected');
								var id =$("#topmenu").combobox('getValue');
								var menuname = $("#menuname").val();
								var fatherid =row._parentId;
								if(id==null){
						   		alert("请选择菜单的上级菜单");
								}
								var mid =row.id;
								var url2 = windowPath+'menu_editmenu.action';
								var params = {'id':id,'menuname':menuname,'fatherid':fatherid,'mid':mid};
								$.post(url2,params,function(data){
								 	var code = JSON.parse(data).code_;
                        	     	if (code == '0') {
                           		 	location.reload();
                            	 	alert('修改成功');
                                 	} else {
                                 	alert('修改失败,请重试!');
                                	}
						});
							});
                    });
                   
        </script>
    </body>

</html>
