$(function() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    $('#rolelist').datagrid({
        title: '角色管理',
        iconCls: 'icon-wrench',
        checkbox: true,
        width: '100%',
        height: 500,
        fitColumns: true,
        rownumbers: true,
        striped: true, //隔行变色
        animate: true,
        collapsible: false,
        collapsed: false,
        singleSelect: false,
        pageNum: 1, //每次重新查询设置为第一页，这里重要，测试一下
        pageSize: 15,
        pageList: [15, 30, 50], //可以设置每页记录条数的列表 
        pagination: true, // 分页控件
        url: windowPath + 'role_findAll.action',
        columns: [
            [
                { field: 'ck', checkbox: true },
                { field: 'ID_', title: '角色编号', width: 60, hidden: true },
                { field: 'NAME_', title: '角色名', width: 100, align: 'center' }
            ]
        ]
    });
   
    //设置分页控件 
    $('#rolelist').datagrid('getPager').pagination({
        beforePageText: '第', //页数文本框前显示的汉字 
        afterPageText: '页  ， 共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function(pageNumber, pageSize) {
            $(this).pagination('loading');
            $('#rolelist').datagrid('options').url = windowPath + 'role_findAll.action?page=' + pageNumber + '&rows=' + pageSize;
            $('#rolelist').datagrid('reload');
            $(this).pagination('loaded');
        }
    });
    /*角色管理用户*/
  
    //角色管理用户
    $("#role_user").click(function(){
    	  var row = $("#rolelist").datagrid('getSelected');
          if (row == null) {
          	$.messager.alert('系统提示', '请选择你要授权的角色!', 'warning');
          	return;
          }else{
        	  var roleId =  row.ID_;
        	  var url = windowPath + 'role_getUser.action';
        	  $('#role_user_datagrid').datagrid({
          		onLoadSuccess : function(data){
          		$.post(url,{roleId:roleId},function(data2){
                  	var data = JSON.parse(data2);
                  	var rows= $('#role_user_datagrid').datagrid('getRows');
              		for(var j = 0; j < rows.length; j++){
              			for (var i = 0; i < data.length; i++){
                      		var selectId = data[i].ID_;
                      		var rowsId = rows[j].id;
      	        			if(rows[j].id == selectId){
      	            			var index = $("#role_user_datagrid").datagrid("getRowIndex",rows[j]);
      	            			$("#role_user_datagrid").datagrid("selectRow",index);
      	        				continue;
      	            		}
              			}
              		}
                  });
          		},
                  iconCls: 'icon-wrench',
                  checkbox: true,
                  width: '100%',
                  height: 400,
                  fitColumns: true,
                  rownumbers: true,
                  animate: true,
                  collapsible: false,
                  striped: true, //隔行变色
                  collapsed: false,
                  singleSelect: false,
                  pageNum: 1, //每次重新查询设置为第一页，这里重要，测试一下
                  pageList: [15, 30, 50], //可以设置每页记录条数的列表
                  pageSize: 30,
                  pagination: true, // 分页控件
                  url: windowPath + 'user_findAll.action',
                  columns: [
                      [
                          { field: 'ck', checkbox: true },
                          { field: 'userName', title: '用户名', width: 60,  align: 'center'},
                          { field: 'jobNumber', title: '工号', width: 100, align: 'center' }
                      ]
                  ]
              });
        	  $('#role_userDia').window('open').window('center');
          }
    });
    $("#role_user_close").click(function(){
    	 $('#role_userDia').window('close');
    });
    $("#role_user_save").click(function(){
    	 var row = $("#rolelist").datagrid('getSelected');
    	 var roleId = row.ID_;
    	 var userIdList = [];
    	 var rows =  $('#role_user_datagrid').datagrid('getSelections');
    	 for(var i = 0; i < rows.length;i++){
    		 userIdList.push(rows[i].id)
    	 }
    	 var url = windowPath + 'role_addUser.action';
    	 var params = {roleId:roleId,userIdList:userIdList}
    	 $.post(url,params,function(data){
    		 var code = JSON.parse(data).code_;
             if (code == '0') {
                 $.messager.alert('系统提示', '授权成功!', 'info',function(){
                 	location.reload();
                 });
             } else {
                 $.messager.alert('系统提示', '授权失败,请重试!', 'error');
             }
    	 });
    	 
    });
  //功能授权
    $("#impowerRole").click(function() {
        var row = $("#rolelist").datagrid('getSelected');
        if (row == null) {
        	$.messager.alert('系统提示', '请选择你要授权的角色!', 'warning');
        	return;
        } else {
        	var roleId = row.ID_;
            var url4=windowPath + 'menu_getmenu.action';
        	$('#impowerRoledg').datagrid({
        		onLoadSuccess : function(data){
        		$.post(url4,{roleId:roleId},function(data2){
                	var data = JSON.parse(data2);
                	var rows= $('#impowerRoledg').datagrid('getRows');
            		for(var j = 0; j < rows.length; j++){
            			for (var i = 0; i < data.length; i++){
                    		var selectId = data[i].ID_;
                    		var rowsId = rows[j].id;
    	        			if(rows[j].id == selectId){
    	            			var index = $("#impowerRoledg").datagrid("getRowIndex",rows[j]);
    	            			$("#impowerRoledg").datagrid("selectRow",index);
    	        				continue;
    	            		}
            			}
            		}
                });
        		},
                iconCls: 'icon-wrench',
                checkbox: true,
                width: '100%',
                height: 400,
                fitColumns: true,
                rownumbers: true,
                animate: true,
                collapsible: false,
                striped: true, //隔行变色
                collapsed: false,
                singleSelect: false,
                pageNum: 1, //每次重新查询设置为第一页，这里重要，测试一下
                pageList: [15, 30, 50], //可以设置每页记录条数的列表
                pageSize: 30,
                pagination: true, // 分页控件
                url: windowPath + 'menu_findAll.action',
                columns: [
                    [
                        { field: 'ck', checkbox: true },
                        { field: 'id', title: '角色编号', width: 60, hidden: true },
                        { field: 'name', title: '菜单树', width: 100, align: 'center' }
                    ]
                ]
            });
            $('#impower').window('open').window('center');
        }
       
    });
    // 关闭授权
    $('#guanbi').click(function() {
        $('#impower').dialog('close');
    });
    $('#baocun').click(function() {
        var row = $("#rolelist").datagrid('getSelected');
        var roleId = row.ID_;
        var idList = [];
        var rows = $('#impowerRoledg').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            idList.push(rows[i].id);
        }
        /**
         * 设置菜单
         * 2017年5月12日16:29:48
         */
            $.post(windowPath + 'role_setMenus.action', { roleId: roleId, menuIds: idList }, function(data) {
                var code = JSON.parse(data).code_;
                if (code == '0') {
                    $.messager.alert('系统提示', '授权成功!', 'info',function(){
                    	location.reload();
                    });
                } else {
                    $.messager.alert('系统提示', '授权失败,请重试!', 'warning');
                }
            });

    });
    //添加角色
    function addRole() {
        var rolename = $('.rolename').val();
        if($.trim(rolename).length == 0){
        	$.messager.alert('系统提示', '角色名不能为空!', 'error');
        	return;
        }
        $.post(
            windowPath + 'role_add.action', { name: rolename },
            function(data) {
                var code = JSON.parse(data).code_;
                if (code == '0') {
                    $.messager.alert('系统提示', '添加成功!', 'info',function(){
                    	location.reload();
                    });
                } else {
                    $.messager.alert('系统提示', '添加失败,请重试!', 'error');
                }
            })
    }
    //删除角色
    $('#deteleRole').click(function() {
        var node = $('#rolelist').datagrid('getSelections');
        var userId = [];
        $.messager.confirm('系统提示', '确定删除该角色？',function(r){
        	if(r){
        		for (var i = 0; i < node.length; i++) {
                     userId.push(node[i].ID_);
                }
        		 $.post(windowPath + 'role_delete.action', { id: userId },function(data){
        			 var code = JSON.parse(data).code_;
        			 if(code=='0'){
        				 $.messager.alert('系统提示', '删除成功!', 'info',function(){
        					 location.reload();
        				 });
        			 }else{
        				 $.messager.alert('系统提示', '删除失败!', 'error');
        			 }
        		 });
        	} else {
                $.messager.alert('系统提示', '删除失败!', 'error');
            }
        });
    });

    function editRole() {
        var node = $('#rolelist').datagrid('getSelected');
        var rolename = $('.rolename1').val();
        var id = node.ID_;
        $.post(
            windowPath + 'role_update.action', { id: id, name: rolename },
            function(data) {
                var code = JSON.parse(data).code_;
                if (code == '0') {
                    $.messager.alert('系统提示', '更新成功!', 'info',function(){
                    	location.reload();
                    });
                } else {
                    $.messager.alert('系统提示', '更新失败，请重试!', 'error');
                }
            })
    }
    //编辑角色
    $('#editRole').click(function() {
        var node = $("#rolelist").datagrid('getSelected');
        if (node == null) {
            alert("请选择你要修改的角色");
        } else {
            $('#editRoleDialog').dialog('open');
            var rolename = $('.rolename1').val(node.NAME_);
        }
    });
    $('#save_close').click(function() {
        addRole();
        $("#insertUserDialog").dialog('close');
    });
    $('#save_continue').click(function() {
        addRole();
    });
    $('#close').click(function() {
        $("#insertRoleDialog").dialog('close');
    });
    $('#keep_close').click(function() {
        editRole();
        $("#editRoleDialog").dialog('close');
    });
    $('#keep_continue').click(function() {
        editRole();
    });
    $('#shutdown').click(function() {
        $("#editRoleDialog").dialog('close');
    });
});
