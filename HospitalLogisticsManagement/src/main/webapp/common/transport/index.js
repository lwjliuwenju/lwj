$(function(){
	var host = window.location.host;
	var windowPath = "http://"+host + "/";
	var deps = JSON.parse(sessionStorage.getItem('deps'));
	var id =deps.id
	$('#transportlist').datagrid({
		title:'运输队管理',
		iconCls:'icon-wrench',
		checkbox: true,
		width: '100%',
		height: 500,
		fitColumns:true,
		rownumbers: true,
		striped:true,//隔行变色
		animate:true,
		collapsible:false,
		collapsed:false,
		singleSelect:false,
		pageNum:1,//每次重新查询设置为第一页，这里重要，测试一下
		pageSize:15,
		pageList: [15,30,50],//可以设置每页记录条数的列表 
		pagination:true,// 分页控件
		url:windowPath+'goods_findAll.action?id='+id,
		toolbar:'#transportTools',
		columns:[[
		    {field:'ck',checkbox:true},
		    {field:'goodsName',title:'物品名称',width:100,align:'center'},
		    {field:'pickUser',title:'取货人',width:100,align:'center'},
		    {field:'pickPhone',title:'取货人电话',width:100,align:'center'},
		    {field:'transportName',title:'物流科室名称',width:100,align:'center'}
		]]
	});
	  $('#department').combobox({
	        data: deps,
	        valueField: 'id',
	        textField: 'text',
	        filter: filterCombo,
	        onLoadSuccess:function(){
             	 var data = $(this).combobox('getData');
                  if (data.length > 0) {
                      $(this).combobox('select', data[0].id);
                  } 
             },
	        onSelect: function(data) {
	            $('#transportlist').datagrid('options').url = windowPath + 'goods_findAll.action?id=' + data.id;
	            $('#transportlist').datagrid('reload');
	        }
	    });
	var deptid = $('#department').combobox('getValue');
    $('#userList').datagrid({
        url: windowPath + 'dep_getdepartmentrenyuan.action?deptid=' + deptid,
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        striped: true, //隔行变色
        columns: [
            [
                { field: 'ck', checkbox: true },
                { field: 'ID_', title: '科员ID', width: 60, hidden: true },
                { field: 'USER_NAME_', title: '科员姓名', width: 100, align: 'center' },
                { field: 'DEP_NAME_', title: '所属科室', width: 100, align: 'center' },
                { field: 'NUMBER_', title: '工作次数', width: 100, align: 'center' }, {
                    field: 'STATUS',
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
            ]
        ]
    });
    $('#paiqianUser').click(function(){
        var userid = sessionStorage.getItem("userid");
    	$('#userListDia').dialog('open');
    	$('#userList').datagrid({
            url: windowPath + 'staff_getdepartment.action?userid=' + userid,
            //title: '派遣人员',
            width: 'auto',
            height: 'auto',
            fitColumns: true,
            striped: true, //隔行变色
            toolbar: '#renyuanTools',
            columns: [
                [
                    { field: 'ck', checkbox: true },
                    { field: 'id', title: '科员ID', width: 60, hidden: true },
                    { field: 'name', title: '科员姓名', width: 100, align: 'center' },
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
                ]
            ]
        });
    });
    $('#close').click(function(){
    	$('#userListDia').dialog('close');
    });
    $('#seeDetail').click(function(){
    	var row = $('#transportlist').datagrid('getSelected');
        $('body').append('<div id="detailsDialogs"></div>');
        $('#detailsDialogs').dialog({
            title: '查看详情',
            width: 600,
            height: 400,
            closed: false,
            cache: false,
            href: windowPath + 'goods_details.action?id=' + row.id,
            modal: true,
            onClose: function() {
                $('#detailsDialogs').remove();
            }
        });
    });
    $('#save').click(function(){
    	var row = $('#transportlist').datagrid('getSelected');
    	var proId = row.id;
    	var userId = [];
    	var rows = $('#userList').datagrid('getSelections');
    	for(var i = 0;i<rows.length;i++){
    		userId.push(rows[i].id);
    	}
    	 if (userId.length >= 1) {
    		 $.post(windowPath + 'goods_saveUser.action',{proId:proId,userId:userId},function(data){
        		 var code = JSON.parse(data).code_;
                 if (code == '0') {
                     $.messager.alert('系统提示', '派遣成功!', 'info',function(){
                     	location.reload();
                     });
                 } else {
                     $.messager.alert('系统提示', '派遣失败,请重试!', 'warning');
                 }
        	});
         } else {
             $.messager.alert('系统提示', '请选择你要派遣的人员!', 'warning');
         }
    });
});