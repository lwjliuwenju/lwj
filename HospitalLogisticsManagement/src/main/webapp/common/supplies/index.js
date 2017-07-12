$(function(){
	var host = window.location.host;
	var windowPath = "http://"+host + "/";
	var deps = JSON.parse(sessionStorage.getItem('deps'));
	$('#supplieslist').datagrid({
		title:'耗材管理',
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
		url:windowPath+'supplies_findAll.action?depid='+deps[0].id,
		columns:[[
		    {field:'ck',checkbox:true},
		    {field:'id',title:'角色编号',width:60,hidden:true},
		    {field:'name',title:'耗材名',width:100,align:'center'},
		    {field:'value',title:'价值',width:100,align:'center'}
		]]
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
	     $('#supplieslist').datagrid('options').url = windowPath+'supplies_findAll.action?depid='+data.id;
	    	$('#supplieslist').datagrid('reload');
	    }
	});
	$('#depname').combobox({
		data:deps,
	    valueField:'id',
	    textField:'text',
	    filter: filterCombo
	});
	 //设置分页控件 
    $('#supplieslist').datagrid('getPager').pagination({
    	beforePageText: '第',//页数文本框前显示的汉字 
	    afterPageText: '页  ， 共 {pages} 页', 
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $('#supplieslist').datagrid('options').url =windowPath+'supplies_findAll.action?page=' + pageNumber + '&rows=' + pageSize;
            $('#supplieslist').datagrid('reload');
            $(this).pagination('loaded');
        }
    });
    //添加耗材
    function addsupplies() {
        var suppliesname = $('.suppliesname').val();
        var suppliesvalue = $('.suppliesvalue').val();
        var depId = $('#depname').combobox('getValue');
        $.post(
        		windowPath+ 'supplies_add.action', {name: suppliesname,value:suppliesvalue,depId:depId },
            function(data) {
                var code = JSON.parse(data).code_;
                if (code == '0') {
                    $.messager.alert('系统提示', '添加成功', 'info',function(){
                    	location.reload();
                    });
                } else {
                    $.messager.alert('系统提示', '添加失败，请重试!', 'error');
                }
            })
    }
    //删除耗材
    $('#detelesupplies').click(function(){
    	var node =$('#supplieslist').datagrid('getSelections');
    	var userId = [];
        for (var i = 0; i < node.length; i++) {
        	userId.push(node[i].id);
		}
    	$.messager.confirm('删除耗材', '确定删除该耗材?', function(r){
        	if (r){
        		 $.post(windowPath + 'supplies_delete.action', { id: userId },function(data){
        			 var code = JSON.parse(data).code_;
                     if (code == '0') {
                         $.messager.alert('删除耗材','删除成功!','info',function(){
                        	 location.reload();
                         });
                     } else {
                     	$.messager.alert('删除耗材','删除成功!','error');
                     }
        		 });
        	}else{
        		$.messager.alert('删除耗材','删除失败!','error');
        	}
        });
    });
    function editsupplies() {
    	var node = $('#supplieslist').datagrid('getSelected');
    	var suppliesname = $('.suppliesname1').val();
    	var suppliesvalue = $('.suppliesvalue1').val();
        var id =node.id;
        $.post(
        		windowPath+ 'supplies_update.action', { id:id,name: suppliesname,value:suppliesvalue },
            function(data) {
                   var code = JSON.parse(data).code_;
                if (code == '0') {
                    $.messager.alert('系统提示', '更新成功', 'info',function(){
                    	location.reload();
                    });
                    
                } else {
                   $.messager.alert('系统提示', '更新失败，请重试!', 'error');
                }
            })
    }
    //编辑角色
    $('#editsupplies').click(function() {
		  var node=$("#supplieslist").datagrid('getSelected');
	      if(node==null){
	    	  $.messager.alert('系统提示', '请选择你要修改的耗材!', 'warning');
	      }else{
	    	  $('#editsuppliesDialog').dialog('open');
	    	  $('.suppliesname1').val(node.name);
	    	  $('.suppliesvalue1').val(node.value);
	    	  
	      }
    });
   $('#save_close').click(function(){
	   addsupplies();
	   $("#insertsuppliesDialog").dialog('close');
   });
   $('#save_continue').click(function(){
	   $("#insertsuppliesDialog").dialog('close');
   });
   $('#keep_close').click(function(){
	   editsupplies();
	   $("#editsuppliesDialog").dialog('close');
   });
   $('#keep_continue').click(function(){
	   $("#editsuppliesDialog").dialog('close');
   });
});
