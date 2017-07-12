$(function() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    $('#departmentlist').datagrid({
        title: '科室管理',
        iconCls: 'icon-wrench',
        checkbox: true,
        width: '100%',
        height: 500,
        fitColumns: true,	
        rownumbers: true,
        animate: true,
        collapsible: false,
        collapsed: false,
        singleSelect: false,
        striped: true, //隔行变色
        pageNum: 1, //每次重新查询设置为第一页，这里重要，测试一下
        pageSize: 15,
        pageList: [15, 30, 50], //可以设置每页记录条数的列表 
        pagination: true, // 分页控件
        toolbar: '#departmentTools',
        url: windowPath + 'dep_findAll.action',
        columns: [
            [
                { field: 'ck', checkbox: true },
                { field: 'id', title: '科室ID', width: 100, align: 'center', hidden: 'true' },
                { field: 'depName', title: '科室名称', width: 100, align: 'center' },
                { field: 'depPhone', title: '科室电话', width: 100, align: 'center' },
                { field: 'userName', title: '科室科长', width: 100, align: 'center' },
            ]
        ],
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
    //设置分页控件 
    $('#departmentlist').datagrid('getPager').pagination({
        beforePageText: '第', //页数文本框前显示的汉字 
        afterPageText: '页  ， 共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function(pageNumber, pageSize) {
            $(this).pagination('loading');
            $('#departmentlist').datagrid('options').url = windowPath + 'dep_findAlllist.action?page=' + pageNumber + '&rows=' + pageSize;
            $('#departmentlist').datagrid('reload');
            $(this).pagination('loaded');
        }
    });
    $('#close').click(function() {
        $("#insertDepartmentDialog").dialog('close');
    });
    $("#insertDepartment").click(function() {
    	$('#insertDepartmentdia').dialog('open');
    });
    $("#close_dep").click(function() {
    	$('#insertDepartmentdia').dialog('close');
    });
    $("#save_dep").click(function(){
        var mark =$("#mark").val();
        var depName =$("#depName").val();
        var depPhone = $("#depPhone").val();
        var isAutoSend = document.getElementsByName('isAuto');
         var selectvalue=null;   //  selectvalue为radio中选中的值
        for (var i = 0; i < isAutoSend.length; i++) {
            if (isAutoSend[i].checked == true) {
                 selectvalue=isAutoSend[i].value;
            }
        }
        $.post(windowPath + "dep_addDep.action",{"mark":mark,"depName":depName,"depPhone":depPhone,isAutoSend:"selectvalue","selectvalue":selectvalue},  
        function(data){
         var code = JSON.parse(data).code_;
                    if (code == '0') {
                        $.messager.alert('系统提示', '新建成功!', 'info',function(){
                        	location.reload();
                        	});
                    } else {
                    	 $.messager.alert('系统提示', '新建失败，请重试!', 'error');
                    }
        });
    });
    $("#setkezhang").click(function() {
        var row = $('#departmentlist').datagrid('getSelected');
        if (row == null) {
            $.messager.alert('系统提示', '请选择你要设置的科室!', 'warning');
        }
        $('#dg').datagrid({
            url: windowPath + 'user_findAll.action',
            width: 'auto',
            height: 'auto',
            fitColumns: true,
            singleSelect: false,
            toolbar: '#kezhangTools',
            striped: true, //隔行变色
            columns: [
                [
                    { field: 'ck', checkbox: true },
                    { field: 'id', title: '用户ID', width: 60, hidden: true },
                    { field: 'userName', title: '用户名', width: 100, align: 'center' }
                ]
            ],
            onLoadSuccess:function(row){//当表格成功加载时执行                 
                var rowData = row.rows; 
                var rowT = $('#departmentlist').datagrid('getSelected');
                $.each(rowData,function(idx,val){//遍历JSON 
                	for (var int = 0; int < rowT.userIds.length; int++) {
                		 if(val.id == rowT.userIds[int]){  
                             $("#dg").datagrid("selectRow", idx);//如果数据行为已选中则选中改行  
                             continue;
                         }
					}
                });                
            }
        });
        if (row) {
            $("#dd").dialog('open');
        }
       
    });
    $('#savekezhang').click(function() {
        var rows = $("#dg").datagrid('getSelections');
        var row = $('#departmentlist').datagrid('getSelected');
        var id = row.id;
        var userid = [];
        for (var int = 0; int < rows.length; int++) {
			userid.push(rows[int].id)
		}
        var url = windowPath + 'dep_editkezhang.action';
        var param = { userid: userid, id: id };
        $.post(url, param, function(data) {
            var code = JSON.parse(data).code_;
            if (code == '0') {
                $.messager.alert('系统提示', '设置成功!', 'info',function(){
                    location.reload();
                    $("#dd").dialog('close');
                });
            } else {
                $.messager.alert('系统提示', '设置失败,请重试!', 'error');
            }
        });
    });
    $('#cancelkezhang').click(function() {
        $("#dd").dialog('close');
    });
});

function del() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    var row = $('#departmentlist').datagrid('getSelected');
    var id =row.id;
    var url = windowPath + 'dep_deletekeyuan.action';
    var params = { 'id': id };
    $.post(url, params, function(data) {
        var code = JSON.parse(data).code_;
        if (code == '0') {
            $.messager.alert('系统提示', '删除成功!', 'info',function(){
            	location.reload();
            	$("#dd").dialog('close');
            });
        } else {
            $.messager.alert('系统提示', '删除成功!', 'error');
        }
    });
}
