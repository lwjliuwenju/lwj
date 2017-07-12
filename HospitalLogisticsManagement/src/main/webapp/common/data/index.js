$(function(){
	var host = window.location.host;
	var windowPath = "http://"+host + "/";
	var deps = JSON.parse(sessionStorage.getItem('deps'));
	var username =sessionStorage.getItem("username");
	var url ="";
	var id ;
	var shunxuid;
	var shijian;
	var shuxunData = [
	     {
		"id":"0",
		"text":"--请选择--"
			},
			{
				"id":"1",
				"text":"本日"	
			},
			{"id":"2",
				"text":"本周"	},
				{"id":"3",
					"text":"本月"	}];
	if(username=="admin"){
		url =windowPath+'data_findAll.action';
	}else{
		var id =deps[0].id
		url =windowPath+'data_findAll.action?id='+id;
	}
	shunxunid = shuxunData[0].id;
	$('#applylist').datagrid({
		title:'科室响应量',
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
		striped:true,
		/*pageNum:1,//每次重新查询设置为第一页，这里重要，测试一下
		pageSize:15,
		pageList: [15,30,50],//可以设置每页记录条数的列表 
		pagination:true,// 分页控件
*/		toolbar:'#applyTools',
		url:url,
		columns:[[
		    {field:'ck',checkbox:true},
		    {field:'depName',title:'科室',width:200,align:'left'},
		    {field:'xiangyingcishu',title:'响应次数',width:200,sortable:true,align:'left'},
		    {field:'haoping',title:'好评次数',width:200,sortable:true,align:'left'},
		    {field:'haopinglv',title:'好评率',width:200,align:'left',sortable:true,formatter:function(value){
		    	return value+"%";
		    }},
		    {field:'zhongping',title:'中评次数',width:200,sortable:true,align:'left'},
		    {field:'zhongpinglv',title:'中评率',width:200,align:'left',sortable:true,formatter:function(value){
		    	return value+"%";
		    }},
		    {field:'chaping',title:'差评次数',width:200,sortable:true,align:'left'},
		    {field:'chapinglv',title:'差评率',width:200,align:'left',sortable:true,formatter:function(value){
		    	return value+"%";
		    }},
		    {field:'waixiu',title:'外修次数',width:200,sortable:true,align:'left'},
		    {field:'waixiulv',title:'外修率',width:200,align:'left',sortable:true,formatter:function(value){
		    	return value+"%";
		    }},
		    {field:'fanxiu',title:'返修次数',width:200,sortable:true,align:'left'},
		    {field:'fanxiulv',title:'返修率',width:200,align:'left',sortable:true,formatter:function(value){
		    	return value+"%";
		    }}
		]]
	});
	$('#dd').datebox({
	    required:false
	});
	$('#department').combobox({
	    data:deps,
	    valueField:'id',
	    textField:'text',
	    filter: filterCombo,
	    onLoadSuccess:function(){
	    	  var data = $('#department').combobox('getData');
	    	  if (data.length > 0) {
            	/*$('#department').combobox('select', data[0].depName);*/
            	id = 0;
     		} 
	    },
	    onSelect:function(data){
	    id = data.id;
	     $('#applylist').datagrid('options').url = windowPath+'data_findAll.action?id='+id;
	    	$('#applylist').datagrid('reload');
	    }
	});
	$("#query").click(function(){
		var starttime =$("#startTime").val();
		var endtime =$("#endTime").val();
        var shunxu =$("#shunxu").val();
		$('#applylist').datagrid({
    		url:windowPath+'data_findAll.action', 
    		queryParams:{'starttime':starttime,'endtime':endtime,'id':$('#department').combobox('getValue'),'shunxu':shunxu}
    	});
	});
	
});

