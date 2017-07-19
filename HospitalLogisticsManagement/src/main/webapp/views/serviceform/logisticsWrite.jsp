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
	</head>
    <body class="easyui-layout" style="overflow-y: hidden" scroll="no">
    <div>
        <table border="1" class="PopUpTableBorder" style="width:100%;">
                <thead>
                    <tr align="center">
                        <td class="td">抽血人</td>
                        <td class="td">抽血床位</td>
                        <td class="td">抽血时间</td>
                        <td class="td">抽血科室</td>
                        <td class="td">抽血数量</td>
                        <td>
                            <input type="button" value="添加" class="ButtonStyle_Blue" id="add">
                        </td>
                    </tr>
                    <thead>
                        <tbody id="tb">
                            <tr class="lst">
                                <td align="center">
                                    <input type="text" style="width: 90%;" class="bloodUser">
                                </td>
                                <td align="center">
                                    <input type="text" style="width: 90%;" class="bloodPos">
                                </td>
                                <td align="center">
                                    <input type="text" style="width: 90%;" class="bloodDate easyui-datetimebox" />
                                </td>
                                <td align="center">
                                    <input type="text" style="width: 90%;" class="bloodDep easyui-combobox">
                                </td>
                                <td align="center">
                                    <input type="number" style="width: 90%;" min="1" class="bloodNum">
                                </td>
                                <td align="center">
                                    <input type="button" value="删除" class="ButtonStyle_Blue del">
                                </td>
                            </tr>
                            <tr id="pick">
                                <td align="center" style="width:550px;">取货人姓名:</td>
                                <td colspan="5" align="center">
                                    <input id="pickUser" type="text" name="" value="" placeholder="" style="width: 90%;">
                                </td>
                            </tr>
                            <tr>
                                <td align="center" style="width:550px;">取货人联系方式:</td>
                                <td colspan="5" align="center">
                                    <input id="pickPhone" type="text" name="" value="" placeholder="" style="width: 90%;">
                                </td>
                            </tr>
                        </tbody>
            </table>
            <div class="butList" style="margin: 20px auto;width:300px">
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save-info">
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close-info">
            </div>
        </div>
        <script >
        	$(function() {
	        var deps = JSON.parse(sessionStorage.getItem('deps'));
			var host = window.location.host;
			var windowPath = "http://"+host + "/";
			    $('#close-info').click(function(){
    	$('#infoLogistics').dialog('close');
    });
    $('#save-info').click(function() {
        var arr = [];
        var allList = $('.lst');
        for (var i = 0; i < allList.length; i++) {
            var blood = {};
            blood.bloodUser = $($('.bloodUser')[i]).val();
            blood.bloodPos = $($('.bloodPos')[i]).val();
            blood.bloodDate = $($('.bloodDate')[i]).datetimebox('getValue');
            blood.bloodDep = $($('.bloodDep')[i]).val();
            blood.bloodNum = $($('.bloodNum')[i]).val();
            arr.push(blood);
        }
        var object = {};
        object.proid = $('#serviceFormlist').datagrid('getSelected').id;
        object.pickUser = $('#pickUser').val();
        object.pickPhone = $('#pickPhone').val();
        object.arr = arr;
        var url = windowPath + 'proposer_saveLogistics.action';
        $.post(url,{object:JSON.stringify(object)},function(data){
        	var code = JSON.parse(data).code_;
            if (code == '0') {
            	$.messager.alert('系统提示', '物流信息已提交!', 'info',function(){
            		  location.reload();
            	});
            } else {
               $.messager.alert('系统提示', '物流信息提交失败，请重试!', 'error');
            }
        })
    });
     function addtr() {
        var str = '<tr class="lst">' + '<td align="center">' +
            '<input type="text" style="width: 90%;" class="bloodUser">' +
            '</td>' + '<td align="center">' +
            '<input type="text"  style="width: 90%;" class="bloodPos">' +
            '</td>' + '<td align="center">' +
            '<input type="datetime"  style="width: 90%;" class="bloodDate">' +
            '</td>' + '<td align="center">' +
            '<input type="text"  style="width: 90%;" class="bloodDep">' +
            '</td>' + '<td align="center">' +
            '<input type="number"  style="width: 90%;" min="1" class="bloodNum">' +
            '</td>' + '<td align="center">' +
            '<input type="button" value="删除" class="ButtonStyle_Blue del">' +
            '</td>' + '</tr>';
    $('#pick').before(str);
    var lastBloodDate = $('#tb').find('.bloodDate:last');
    var lastBloodDep = $('#tb').find('.bloodDep:last');
    /*$('.bloodDate').datetimebox();*/
    lastBloodDate.datetimebox();
    lastBloodDep.combobox({
   	 data: deps,
     valueField: 'id',
     textField: 'text',
     filter: filterCombo,
     onLoadSuccess:function(){
    	 var data = $(this).combobox('getData');
         if (data.length > 0) {
             $(this).combobox('select', data[0].text);
         } 
    }
	});
	};
	var flag =  0;
	if(flag == 0){
		$('.bloodDep').combobox({
		   url: windowPath + 'dep_getdepartment.action',
	       valueField: 'id',
	       textField: 'text',
	       width:150,
           filter: filterCombo,
           onLoadSuccess:function(){
          	 var data = $(this).combobox('getData');
               if (data.length > 0) {
                   $(this).combobox('select', data[0].text);
               } 
          }
		});
		flag = 1;
	};
    $('#add').click(function() {
        addtr();
    });
    $('#tb').on('click', '.del', function() {
        $(this).parent().parent().remove();
    });
        });
       
	
        </script>
    </body>

</html>
