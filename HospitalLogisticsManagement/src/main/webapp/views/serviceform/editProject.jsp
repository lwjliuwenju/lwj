<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link href="/Css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/js/themes/icon.css" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	 <script type="text/javascript" src="../../Js/jquery.min.js"></script>
	 <script type="text/javascript" src="../../Js/json2.js"></script>
  <script type="text/javascript" src="../../Js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src='/js/outlook2.js'>
    </script>
   
    <body class="easyui-layout" style="overflow-y: hidden" >
    <div class="juzhong">
    		 <table class="PopUpTableBorder" border="0" cellspacing="0" cellpadding="0">
            <tbody>
                <tr>
                    <td class="PopUpTableTitle" width="200px">
                        <font color="#ff0000">*</font>
                        响应科室名称:
                    </td>
                    <td width="500px">
                        <input id="responseDeptName" name="dept"></input>
                    </td>
                </tr> 
                <tr>
                    <td class="PopUpTableTitle" width="200px">
                        <font color="#ff0000">*</font>
                        响应科室项目:
                    </td>
                    <td width="500px">
                     <input id="responseDeptitem" name="dept"></input>
                    </td>
                </tr>
                  <tr>
                    <td  width="400px" colspan="2" align="center">
                       注释: 所有加<font color="#ff0000">*</font>为必填项
                    </td>
                </tr>
            </tbody>
        </table>
        </div>
        <div class="butList">
            <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save_close"></input>
            <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="save_continue"></input>
        </div>
    </div>
       
        <script type="text/javascript">
                    $(function() {
			var host = window.location.host;
			var windowPath = "http://"+host + "/";
			var row = $("#serviceFormlist").datagrid('getSelected');
            $('#responseDeptName').combobox({
            	url: windowPath+'dep_getdepartment.action', 
                valueField: 'id',
                textField: 'text',
                editable:true,
                onSelect: function(data){
              	var url= windowPath+'project_findById.action?id='+data.id;
                $('#responseDeptitem').combobox('reload',url);
			    } 
            });
             $('#responseDeptitem').combobox({
                //url:'combobox_data.json',
                valueField: 'id',
                textField: 'text',
                onSelect: function (data) {
					var itemid =data.id;
				}
            });
            $("#responseDeptName").combobox('setValue',row.reponseDepId);
			$("#responseDeptName").combobox('setText',row.responseDepName);
			$("#responseDeptitem").combobox('setValue',row.projectId);
			$("#responseDeptitem").combobox('setText',row.projectName);
            $("#save_close").click(function() {
                var proId =$("#serviceFormlist").datagrid("getSelected").id;
                var responseDeptid = $("#responseDeptName").combobox('getValue');
              	var responseDeptitem = $("#responseDeptitem").combobox('getText');
              	var ProposerId = $("#responseDeptitem").combobox('getValue');
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
                            location.reload();
                            alert('修改服务成功');
                        } else {
                            alert('修改服务失败,请重试!');
                        }
                    });
            });
        });
		</script>
    </body>


