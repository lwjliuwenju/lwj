$(function(){
	var host = window.location.host;
	var windowPath = "http://"+host + "/";
	var deps = JSON.parse(sessionStorage.getItem('deps'));
	var depid=deps[0].id;
	$('#departmentlist').datagrid({
		title:'人员管理',
		iconCls:'icon-wrench',
		checkbox: true,
		width: '100%',
		height: 500,
		fitColumns:true,
		rownumbers: true,
		animate:true,
		collapsible:false,
		collapsed:false,
		singleSelect:false,
		striped:true,//隔行变色
		pageNum:1,//每次重新查询设置为第一页，这里重要，测试一下
		pageSize:15,
		pageList: [15,30,50],//可以设置每页记录条数的列表 
		pagination:true,// 分页控件
		toolbar:'#departmentTools',
		url:windowPath+'staff_findAll.action?id='+deps[0].id,
		columns:[[
{ field: 'ck', checkbox: true },
{ field: 'id', title: '科员ID', width: 60, hidden: true },
{ field: 'name', title: '科员姓名', width: 100, align: 'center' },
{ field: 'phone', title: '手机号', width: 100, align: 'center' },
{ field: 'depName', title: '所属科室', width: 100, align: 'center' },
{ field: 'number', title: '响应次数', width: 100, align: 'center' }, {
    field: 'state',
    title: '科员当前状态',
    width: 100,
    align: 'center',
    formatter: function(value) {
        if (value == 0) {
            return '<span style="background-color:green;display:inline-block;width:100%;padding:0px;color:#fff;">空闲</span>';
        } else {
            return '<span style="background-color:red;display:inline-block;width:100%;padding:0px;color:#fff;">忙碌</span>';
        }
    }
}
		]],
		onRowContextMenu : function (e, rowIndex, rowData) {
			 e.preventDefault(); 
			 $(this).datagrid("clearSelections"); 
            $(this).datagrid("selectRow", rowIndex); 
            $('#mm').menu('show', {                        
                left: e.pageX,
                top: e.pageY
            });
            e.preventDefault(); 
		}
   });

	$('#department').combobox({
	    data:deps,
	    valueField:'id',
	    textField:'text',
	    filter: filterCombo,
	    onLoadSuccess:function(){
	    	  var data = $('#department').combobox('getData');
	    	  if (data.length > 0) {
              	$('#department').combobox('select', data[0].id);
       		} 
	    },
	    onSelect:function(data){
	     depid=data.id;
	     $('#departmentlist').datagrid('options').url = windowPath+'staff_findAll.action?id='+data.id;
	    	$('#departmentlist').datagrid('reload');
	    }
	});
	$('#depname').combobox({
		data:deps,
	    valueField:'id',
	    textField:'text',
	    filter: filterCombo
	});
	 //设置分页控件 
    $('#departmentlist').datagrid('getPager').pagination({
    	beforePageText: '第',//页数文本框前显示的汉字 
	    afterPageText: '页  ， 共 {pages} 页', 
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $('#departmentlist').datagrid('options').url =windowPath+'dep_findAlllist.action?page=' + pageNumber + '&rows=' + pageSize;
            $('#departmentlist').datagrid('reload');
            $(this).pagination('loaded');
        }
    });
    $('#close').click(function(){
 	   $("#insertDepartmentDialog").dialog('close');
    });
    $("#insertkeyuan").click(function(){
    	$('#addUserDia').dialog('open');
    });
    /*保存科室人员信息*/
    $('#save-addUser').click(function(){
            var keyuanName =$("#keyuanName").val();
            var phone =$(".phone").val();
            if($.trim(keyuanName) == ''){
            	$.messager.alert('系统提示', '科员姓名不能为空!', 'error');
            	return;
            }
            var depId=depid;
            $.post(windowPath + "staff_addkeyuan.action",
            {"keyuanName":keyuanName,"depId":depId,"phone":phone},  
            function(data){
             var code = JSON.parse(data).code_;
                        if (code == '0') {
                        	$.messager.alert('系统提示', '添加成功!', 'info',function(){
                        		location.reload();
                        	});
                        } else {
                        	$.messager.alert('系统提示', '添加失败，请重试!', 'error');
                        }
            });
    });
    /*关闭添加人员界面*/
    $('#close-addUser').click(function(){
    	$('#addUserDia').dialog('close');
    });
    $("#allotkeyuan").click(function(){
    	var row = $('#departmentlist').datagrid('getSelected');
    	if(row==null){
    		$.messager.alert('系统提示', '请选择你要修改的科室', 'warning');
    	}
    	$('#dg1').datagrid({
            url: windowPath + 'user_findAll.action',
            width: 'auto',
            height: 'auto',
            fitColumns: true,	
            singleSelect:false,
            toolbar: '#keyuanTools',
            striped:true,//隔行变色
            columns: [
                [
                    { field: 'ck', checkbox: true },
                    { field: 'id', title: '用户ID', width: 60, hidden: true },
                    { field: 'userName', title: '用户名', width: 100, align: 'center' },
                    { field: 'NUMBER_', title: '次数', width: 100, align: 'center',hidden:true },
                    { field: 'STATUS', title: '状态', width: 100, align: 'center',hidden:true}
                ]
            ]
        });
        if (row) {
            $("#dd1").dialog('open');
        }
    });
    $('#savekeyuan').click(function() {
    	var rows = $("#dg1").datagrid('getSelections');
    	var row =$('#departmentlist').datagrid('getSelected');
    	var userid = [];
    	var username =[];
    	var id =row.ID_;
    	for (var i = 0; i < rows.length; i++) {
        	userid.push(rows[i].id);
        	username.push(rows[i].userName);
    	}
    /*	alert(userid.length);
    	alert(username.length);
    	return;*/
            var url = windowPath + 'dep_editkeyuan.action';
        	var params = {
        			"id":id,
        			"userid":userid.join("@@"),
        			"username":username.join("@@")
        	};
            $.post(url,{params:JSON.stringify(params)},function(data){
                var code = JSON.parse(data).code_;
                if (code == '0') {
                	$.messager.alert('系统提示', '设置成功', 'info');
                	 $("#dd1").dialog('close');
                	location.reload();
                } else {
                    $.messager.alert('系统提示', '设置失败,请重试!', 'error');
                }
            });
    });
    $('#cancelkeyuan').click(function() {
    	  $("#dd1").dialog('close');
      });
});
function del() {
	var host = window.location.host;
	var windowPath = "http://"+host + "/";
	 $.messager.confirm('系统提示','是否删除?',function(r){
		 if(r){
			    var row = $("#departmentlist").datagrid('getSelected');
				var userId =row.id;
				var url = windowPath + 'staff_deletekeyuan.action';
			    var params ={'userId':userId};
			    $.post(url,params,function(data){
			    	  var code = JSON.parse(data).code_;
			            if (code == '0') {
			            	$("#dd").dialog('close');
			                $.messager.alert('系统提示', '删除成功', 'info',function(){
			                	  location.reload();
			                });
			            } else {
			                $.messager.alert('系统提示', '删除失败', 'error');
			            }
			    });
		 }else{
			 $.messager.alert('系统提示', '删除失败', 'error');
		 }
	 })

}

