$(function(){
	var deps = JSON.parse(sessionStorage.getItem('deps'));
	var host = window.location.host;
	var windowPath = "http://"+host + "/";
	$('#supplieslist').datagrid({
		title:'耗材响应单管理',
		iconCls:'icon-wrench',
		checkbox: true,
		width: '100%',
		height:500,
		fitColumns:true,
		rownumbers: true,
		striped:true,//隔行变色
		animate:true,
		collapsible:false,
		collapsed:false,
		singleSelect:true,
		pageNum:1,//每次重新查询设置为第一页，这里重要，测试一下
		pageSize:15,
		pageList: [15,30,50],//可以设置每页记录条数的列表 
		pagination:true,// 分页控件
		toolbar:'#departmentTools',
		url:windowPath+'suppliesproposer_findByConditions.action',
		columns:[[
		    {field:'ck',checkbox:true},
		    {field:'ID_',title:'申请单中间表ID',width:60,hidden:true},
		    {field:'proName',title:'申请人名称',width:100,align:'center'},
		    {field:'proDepName',title:'申请部门名称',width:100,align:'center'},
		    {field:'suppliesName',title:'耗材名称',width:100,align:'center'},
		    {field:'suppliesNum',title:'耗材数量',width:100,align:'center'},
		    {field:'suppliesSendTime',title:'申请开始时间',width:100,align:'center',formatter:function(value){
		    	if(value==null){
		    		return "";
		    	}else{
		    		return new Date(value.time).toLocaleString();
		    	}
		    	}},
		    	{field:'suppliesProState',title:'状态',width:100,align:'center'}
		]]
	});
	 //设置分页控件 
    $('#supplieslist').datagrid('getPager').pagination({
    	beforePageText: '第',//页数文本框前显示的汉字 
	    afterPageText: '页  ， 共 {pages} 页', 
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $('#supplieslist').datagrid('options').url =windowPath+'suppliesproposer_findBySupplies.action?page=' + pageNumber + '&rows=' + pageSize;
            $('#supplieslist').datagrid('reload');
            $(this).pagination('loaded');
        }
    });
	$('#complete').click(function(){
		var id = $('#supplieslist').datagrid('getSelected').id;
		var url = windowPath+'suppliesproposer_updateStatus.action';
		$.post(url,{id:id},function(data){
			var code = JSON.parse(data).code_;
            if (code == '0') {
            	$.messager.alert('系统提示', '发放耗材成功!', 'info',function(){
            		 location.reload();
            	});
            } else {
            	$.messager.alert('系统提示', '发放耗材失败,请重试!', 'error');
              
            }
		})
	});
	$('#shenqingdepartment').combobox({
		  url: windowPath+'dep_getdepartment.action',
		  valueField:'id',
		  textField:'text',
		  filter: filterCombo
	});
	$('#submitperson').combobox({
		url: windowPath+'user_getusers.action', 
	    valueField:'id',
	    textField:'text',
	    filter: filterCombo
	});
	$('#zhixingperson').combobox({
		url: windowPath+'user_getuser.action', 
	    valueField:'id',
	    textField:'text',
	    filter: filterCombo
	});
	$('#searchResponse').click(function(){
		//提交人id
    	var tijiaorenId = $("#submitperson").combobox('getText');
    	//申请单部门 id   	
    	var dep = $("#shenqingdepartment").combobox('getText');
    	//排序方式 0状态 1 时间
    	var paixu = $("#shunxu option:selected").val();
    	
    	//状态 0未发放 
    	var status =$("#state option:selected").val();
    	
    	$('#supplieslist').datagrid({
    		url:windowPath+'suppliesproposer_findByConditions.action', 
			//pageNum:1,//每次重新查询设置为第一页，这里重要，测试一下
    		queryParams:{'tijiaorenId':tijiaorenId,'dep':dep,'paixu':paixu,'status':status,'flag':1}
    	});
    	$('#supplieslist').datagrid('getPager').pagination({
        	beforePageText: '第',//页数文本框前显示的汉字 
    	    afterPageText: '页  ， 共 {pages} 页', 
    	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            onSelectPage: function (pageNumber, pageSize) {
                $(this).pagination('loading');
	            $('#supplieslist').datagrid('options').url = windowPath+'suppliesproposer_findByConditions?page=' + pageNumber + '&rows=' + pageSize + '&tijiaorenId=' 
	            																						+ tijiaorenId + '&dep=' + dep + '&paixu='+
	            																						paixu + '&status=' + status  + '&flag=0';
	            $('#supplieslist').datagrid('reload');
	            $(this).pagination('loaded');
	        }
	    });
		
		
	})
});

