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
	url =windowPath+'data_findalllist.action';
	shunxunid = shuxunData[0].id;
	$('#fanxiulist').datagrid({
		title:'科室申请量',
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
		pageNum:1,//每次重新查询设置为第一页，这里重要，测试一下
		pageSize:15,
		pageList: [15,30,50],//可以设置每页记录条数的列表 
		pagination:true,// 分页控件
		toolbar:'#fanxiuTools',
		url:url,
		columns:[[
		    {field:'ck',checkbox:true},
		    {field:'projectName',title:'服务名称',width:200,align:'left'},
		    {field:'num',title:'申请次数',width:200,align:'left'},
		    {field:'bzgs',title:'标准工时',width:200,align:'left'},
		    {field:'fenzhi',title:'分值',width:200,align:'left'}
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
            	/*$('#department'	.combobox('select', data[0].depName);*/
            	id = 0;
     		} 
	    },
	    onSelect:function(data){
	    id = data.id;
	     $('#fanxiulist').datagrid('options').url = windowPath+'data_findalllist.action?id='+id;
	    	$('#fanxiulist').datagrid('reload');
	    }
	});
	$("#query").click(function(){
		var startTime = $("#startTime").datebox('getValue');
		var endTime = $("#endTime").datebox('getValue');
		var id =$("#department").combobox('getValue');
		$('#fanxiulist').datagrid('options').url = windowPath
		+ 'data_findalllist.action?id=' + id
		+ '&startTime=' + startTime + '&endTime=' + endTime;
		$('#fanxiulist').datagrid('reload');
	})
    $('#fanxiulist').datagrid('getPager').pagination({
    	beforePageText: '第',//页数文本框前显示的汉字 
	    afterPageText: '页  ， 共 {pages} 页', 
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $('#fanxiulist').datagrid('options').url =windowPath+'data_findalllist.action?page=' + pageNumber + '&rows=' + pageSize;
            $('#fanxiulist').datagrid('reload');
            $(this).pagination('loaded');
        }
    });
	
});

