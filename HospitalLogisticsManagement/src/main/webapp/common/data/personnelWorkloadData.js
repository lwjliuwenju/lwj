$(function(){
	var host = window.location.host;
	var windowPath = "http://"+host + "/";
	var username = sessionStorage.getItem("username");
	var deps = eval('(' + sessionStorage.getItem('deps') + ')');
	var url = "";
	var id = -99;
	var shunxuid = 0;
	var shuxunData = [ {
		"id" : "0",
		"text" : "--请选择--"
	}, {
		"id" : "1",
		"text" : "本日"
	}, {
		"id" : "2",
		"text" : "本周"
	}, {
		"id" : "3",
		"text" : "本月"
	} ];
	if (username == "admin") {
		url = windowPath + 'data_personnelWorkloadData.action?depId=-99';
	} else {
		var id = deps[0].id
		url = windowPath + 'data_personnelWorkloadData.action?depId=' + id;
	}
	$('#workLoadlist').datagrid({
		title:'人员工作量统计',
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
		url:url,
		columns:[[
		    {field:'ryName',title:'姓名',width:100,align:'center'},
		    {field:'depName',title:'部门',width:100,align:'center'},
		    {field:'workNumber',title:'工作次数',width:100,align:'center'},
		    {field:'grade',title:'总分值',width:100,align:'center',formatter:function(value){
		    	  if(value!=null){
		    		  return value
		    	  }else{
		    		  return 0;
		    	  }
		    }},
		    {field:'goodNumber',title:'好评',width:100,align:'center'},
		    {field:'goodLv',title:'好评率',width:100,align:'center',formatter:function(value){
		    	return value + "%";
		    }},
		    {field:'centerNumber',title:'中评',width:100,align:'center'},
		    {field:'centerLv',title:'中评率',width:100,align:'center',formatter:function(value){
		    	return value + "%";
		    }},
		    {field:'chaNumber',title:'差评',width:100,align:'center'},
		    {field:'chaLv',title:'差评率',width:100,align:'center',formatter:function(value){
		    	return value + "%";
		    }},
		    {field:'PJNumber',title:'综合',width:100,align:'center'},
		    {field:'fxNumber',title:'返修',width:100,align:'center'},
		    {field:'wwcNumber',title:'未完成',width:100,align:'center'},
		    {field:'SHexamineL',title:'通过率',width:100,align:'center',formatter:function(value){
		    	return value + "%";
		    }}
		]]
	});
	$('#department').combobox(
			{
				data : deps,
				valueField : 'id',
				textField : 'text',
			    filter: filterCombo,
				onLoadSuccess : function() {
					var data = $('#department').combobox('getData');
					if (data.length > 0) {
						/* $('#department').combobox('select', data[0].depName); */
						id = -99;
					}
				}
			});
	$("#query").click(function(){
		var startTime = $("#startTime").datebox('getValue');
		var endTime = $("#endTime").datebox('getValue');
		var depid =$('#department').combobox('getValue');
		if(depid==""){
			id=0;
		}else{
			id=depid;
		}
		$('#workLoadlist').datagrid('options').url = windowPath
		+ 'data_personnelWorkloadData.action?id=' + id
		+ '&startTime=' + startTime + '&endTime=' + endTime;
		$('#workLoadlist').datagrid('reload');
	})
	 //设置分页控件 
	$("#downloaddata").click(function(){
		var startTime = $("#startTime").datebox('getValue');
		var endTime = $("#endTime").datebox('getValue');
		var depid =$('#department').combobox('getValue');
		if(depid==""){
			id=0;
		}else{
			id=depid;
		}
	     var url =  windowPath + 'data_personnelWorkloadData.action?down=true&id='+id
	     + '&startTime=' + startTime + '&endTime=' + endTime;
	     window.open(url);
	});
    $('#workLoadlist').datagrid('getPager').pagination({
    	beforePageText: '第',//页数文本框前显示的汉字 
	    afterPageText: '页  ， 共 {pages} 页', 
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $('#workLoadlist').datagrid('options').url =windowPath+'data_personnelWorkloadData.action?page=' + pageNumber + '&rows=' + pageSize;
            $('#workLoadlist').datagrid('reload');
            $(this).pagination('loaded');
        }
    });
});
