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
        		<input type="text" name="" id="supDep">
            <div id="suppliesDatagrid"></div>
            <div class="butList" style="width:200px;">
                <input type="button" name="" value="保存" class="ButtonStyle_Blue" id="save-s">
                <input type="button" name="" value="关闭" class="ButtonStyle_Blue" id="close-s">
            </div>
        </div>
        <script >
        	$(function() {
	        var deps = JSON.parse(sessionStorage.getItem('deps'));
			var host = window.location.host;
			 var id=0;
			  var supId=0;
			var windowPath = "http://"+host + "/";
			 $('#supDep').combobox({
    			data:deps,
    			valueField: 'id',
		        textField: 'text',
		        width:140,
		        filter: filterCombo,
		        onLoadSuccess:function(){
		        	 var data = $(this).combobox('getData');
		             if (data.length > 0) {
		                 $(this).combobox('select', data[0].text);
		             } 
		        },
		        onSelect:function(data){
		        	id = data.id;
		   	     $('#suppliesDatagrid').datagrid('options').url = windowPath+'supplies_findAll.action?depid='+id + "&supId="+ supId;
		   	    	$('#suppliesDatagrid').datagrid('reload');
		   	    }
    		});
     $('#suppliesDatagrid').datagrid({
        iconCls: 'icon-wrench',
        checkbox: true,
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        rownumbers: true,
        animate: true,
        collapsible: false,
        collapsed: false,
        striped: true, //隔行变色
        singleSelect: false,
        autoSave: true,
        pageNum: 1, //每次重新查询设置为第一页，这里重要，测试一下
        pageSize: 15,
        pageList: [15, 30, 50], //可以设置每页记录条数的列表 
        pagination: true, // 分页控件
        url: windowPath + 'supplies_findAll.action?depid='+id,
        columns: [
            [
                { field: 'ck', checkbox: true },
                { field: 'id', title: '耗材ID', width: 60, hidden: true },
                { field: 'name', title: '耗材名称', width: 100, align: 'center' }, {
                    field: 'suppliesNum',
                    title: '耗材数量',
                    width: 100,
                    align: 'center',
                    editor: {
                        type: 'numberbox',
                        options: {
                            required: true //校验必须
                        }
                    }

                }
            ]
        ],

        onClickRow: onClickRow,
        onAfterEdit: function(rowIndex, rowData, changes) {
            //保存编辑行数据
            var a = rowData.suppliesNum;
            var id = rowData.id;
            $('#suppliesDatagrid').datagrid('updateRow', {
                index: rowIndex,
                row: {
                    'id': id,
                    suppliesNum: a
                }
            });
        }
    });
    //记录当前处于编辑状态行索引
    var editIndex = undefined;

    function endEditing() {
        //没有编辑
        if (editIndex == undefined) {
            return true
        }
        //数据校验
        if ($('#suppliesDatagrid').datagrid('validateRow', editIndex)) {
            //校验通过技术编辑
            $('#suppliesDatagrid').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            //校验没有通过继续编辑
            return false;
        }
    }
    //行点击事件
    function onClickRow(index) {
        //记录的编辑行， 和当前点击行 不能同一样
        if (editIndex != index) {
            //结束之前的编辑
            if (endEditing()) {
                //让当前点击行进行编辑
                $('#suppliesDatagrid').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
                //记录编辑行
                editIndex = index;
            } else {
                //选中当前行
                $('#suppliesDatagrid').datagrid('selectRow', editIndex);
            }
        }
    };
     //保存耗材
    $('#save-s').click(function() {
        endEditing();

        var row = $("#serviceFormlist").datagrid('getSelected');
        //申请单ID
        var proposerId = row.id;
        
        //耗材ID
        var supplies = [];
        //耗材数量
        var rows = $('#suppliesDatagrid').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            supplies.push(rows[i].id + ":" + rows[i].suppliesNum);
            if (rows[i].suppliesNum == null) {
                $.messager.alert('系统提示', '请选择耗材数量', 'warning');
                return;
            }
        }
        $.post(windowPath + 'suppliesproposer_saveSupplies.action', { proposerId: proposerId, supplies: supplies }, function(data) {
            var code = JSON.parse(data).code_;
            if (code == '0') {
            	$.messager.alert('系统提示', '选择耗材成功', 'info',function(){
            		 location.reload();
            	});
            } else {
               $.messager.alert('系统提示', '选择耗材失败，请重试!', 'error');
            }
        });
    });
      $('#close-s').click(function() {
        $('#suppliesDialog').dialog('close');
    });

       		 });
        </script>
    </body>

</html>
