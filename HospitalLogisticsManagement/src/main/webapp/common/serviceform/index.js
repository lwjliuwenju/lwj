$(function() {
    var deps = JSON.parse(sessionStorage.getItem('deps'));
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    var id=0;
    var supId=0;
    $('#serviceFormlist').datagrid({
        title: '服务申请单管理',
        iconCls: 'icon-wrench',
        checkbox: true,
        width: '100%',
        height: 500,
        fitColumns: true,
        rownumbers: true,
        striped: true, //隔行`变色
        animate: true,
        collapsible: false,
        collapsed: false,
        singleSelect: false,
        pageNum: 1, //每次重新查询设置为第一页，这里重要，测试一下
        pageSize: 15,
        pageList: [15, 30, 50], //可以设置每页记录条数的列表 
        pagination: true, // 分页控件
        toolbar: '#departmentTools',
        url: windowPath + 'proposer_findAll.action',
        columns: [
            [
                { field: 'ck', checkbox: true },
                { field: 'id', title: '申请单ID', width: 60, hidden: true },
                { field: 'depName', title: '科室名称', width: 100, align: 'center' }, {
                    field: 'proposerTime',
                    title: '申请时间',
                    width: 200,
                    align: 'center'
                },
                { field: 'projectName', title: '申请服务', width: 100, align: 'center' }, {
                    field: 'repairFlag',
                    title: '返修',
                    width: 100,
                    align: 'center'
                },
                { field: 'responseTimes', title: '响应次数', width: 100, align: 'center' }, {
                    field: 'outSourcIngFlag',
                    title: '外修',
                    width: 100,
                    align: 'center'
                }, {
                    field: 'responseTime',
                    title: '响应时间',
                    width: 200,
                    align: 'center'
                },
                { field: 'responseStaff', title: '响应人员', width: 100, align: 'center' },
                { field: 'useTime', title: '已用工时', width: 200, align: 'center' },
                { field: 'sendUserName', title: '申请人', width: 100, align: 'center' },
                { field: 'reponseDepName', title: '响应科室名称', width: 100, align: 'center' }, {
                    field: 'appraise',
                    title: '评价',
                    width: 100,
                    align: 'center'
                }, {
                    field: 'endTime',
                    title: '完成时间',
                    width: 200,
                    align: 'center'
                },
                {field: 'pickUser', title: '取货人', width: 100, align: 'center'},
                {field: 'goodsName', title: '物品名称', width: 100, align: 'center'},
                {field: 'pickdepName', title: '所属部门', width: 100, align: 'center'},
                {
                    field: 'state',
                    title: '状态',
                    width: 85,
                    align: 'center'
                }
            ]
        ],
        onRowContextMenu: function(e, rowIndex, rowData) {
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
    deps.unshift({ "text": "请选择", "id": 0 });
    $('#shenqingdepartment').combobox({
        data: deps,
        valueField: 'id',
        textField: 'text',
        filter: filterCombo,
        onLoadSuccess:function(){
       	 var data = $(this).combobox('getData');
            if (data.length > 0) {
                $(this).combobox('select', data[0].id);
            } 
       }
    });
    $('#shenqingdepartment2').combobox({
        data: deps,
        valueField: 'id',
        textField: 'text',
        filter: filterCombo,
        onLoadSuccess:function(){
       	 	var data = $(this).combobox('getData');
            if (data.length > 0) {
                $(this).combobox('select', data[0].text);
            } 
       }
    });
    $.post(windowPath + 'user_getusers.action', function(dataT) {
    	  var data = JSON.parse(dataT);
    	  data.unshift({ "text": "请选择", "id": 0 });
    	  $('#submitperson').combobox({
    	        data: data,
    	        valueField: 'id',
    	        textField: 'text',
    	        filter: filterCombo,
    	        onLoadSuccess:function(){
    	       	 var data = $(this).combobox('getData');
    	         if (data.length > 0) {
    	             $(this).combobox('select', data[0].text);
    	         } 
    	       }
    	    });
    	});
    $.post(windowPath + 'dep_getdepartment.action',function(dataT){
    	var data = JSON.parse(dataT);
  	  	data.unshift({ "text": "请选择", "id": -1 });
    	$('#jieshoubumen').combobox({
            data: data,
            valueField: 'id',
            textField: 'text',
            filter: filterCombo,
            onLoadSuccess:function(){
           	 var data = $(this).combobox('getData');
                if (data.length > 0) {
                    $(this).combobox('select', data[0].id);
                } 
           }
        });
    });
    $.post(windowPath + 'user_getuser.action',function(dataT){
    	var data = JSON.parse(dataT);
  	  	data.unshift({ "text": "请选择", "id": 0 });
    	$('#zhixingperson').combobox({
            data: data,
            valueField: 'id',
            textField: 'text',
            filter: filterCombo,
            onLoadSuccess:function(){
           	 var data = $("#zhixingperson").combobox('getData');
                if (data.length > 0) {
                    $(this).combobox('select', data[0].text);
                } 
           }
        });
    });
    
    //设置分页控件 
    $('#serviceFormlist').datagrid('getPager').pagination({
        beforePageText: '第', //页数文本框前显示的汉字 
        afterPageText: '页  ， 共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function(pageNumber, pageSize) {
            $(this).pagination('loading');
            $('#serviceFormlist').datagrid('options').url = windowPath + 'proposer_findAll.action?page=' + pageNumber + '&rows=' + pageSize;
            $('#serviceFormlist').datagrid('reload');
            $(this).pagination('loaded');
        }
    });
    $("#complete").click(function() {
        var rows = $("#serviceFormlist").datagrid("getSelected");
        if (rows == null) {
            $.messager.alert('系统提示', '请选择你要完成的订单!', 'warning');
            return;
        }
        if (rows.status < 4) {
            var r = $("#serviceFormlist").datagrid('getSelections');
            $.messager.confirm('系统提示', '是否完成?', function(r){
            	if (r){
            		 var row = $("#serviceFormlist").datagrid('getSelections');
            		 var url = windowPath + 'proposer_complete.action';
            		  var proId = [];
                      for (var i = 0; i < row.length; i++) {
                          proId.push(row[i].id);
                      }
                     $.post(url, { 'proId': proId }, function(data) {
                         var code = JSON.parse(data).code_;
                         if (code == '0') {
                             $.messager.alert('系统提示', '以成功!', 'info');
                             location.reload();
                         } else {
                             $.messager.alert('系统提示', '未完成!', 'error');
                         }
                     });
            	}else{
            		$.messager.alert('系统提示', '你已取消了完成操作!', 'info');
            		return;
            	}
            });
        } else {
            $.messager.alert('系统提示', '该响应单无法进行完成操作，请联系管理员', 'warning');
            return;
        }
    });
    $("#seeDetails").click(function() {
        var row = $("#serviceFormlist").datagrid('getSelected');
        if (row == null) {
        	 $.messager.alert('系统提示', '请选择你要查询的订单', 'warning');
            return;
        }
        $('body').append('<div id="detailsDialog"></div>');
        $('#detailsDialog').dialog({
            title: '查看详情',
            width: 600,
            height: 400,
            closed: false,
            cache: false,
            href: windowPath + 'proposer_detailform.action?id=' + row.id,
            modal: true,
            onClose: function() {
                $('#detailsDialog').remove();
            }
        });
    });
       
 
    $("#searchServiceform").click(function() {
    	var startTime = $("#startTime").datebox('getValue');
		var endTime = $("#endTime").datebox('getValue');
        var reponseUser = $("#zhixingperson").combobox('getText');
        if(reponseUser == "请选择")
        	reponseUser = '';
        var reponseDepId = $("#jieshoubumen").combobox('getValue');
        var sendUserName = $("#submitperson").combobox('getText');
        if(sendUserName == "请选择")
        	sendUserName = '';
        var state = $("#state").val();
        var sendDepName = $("#shenqingdepartment").combobox('getText');
        if(sendDepName == "请选择")
        	sendDepName = '';
        var shunxu =$("#shunxu").val();
        $('#serviceFormlist').datagrid({
            url: windowPath + 'proposer_findAll.action',
            //pageNum:1,//每次重新查询设置为第一页，这里重要，测试一下
            queryParams: {
                'reponseUser': reponseUser,
                'reponseDepId': reponseDepId,
                'sendUserName': sendUserName,
                'state': state,
                'sendDepName': sendDepName,
                'shunxu':shunxu,
                'startTime':startTime,
                'endTime':endTime
            }
        });
        $('#serviceFormlist').datagrid('getPager').pagination({
            beforePageText: '第', //页数文本框前显示的汉字 
            afterPageText: '页  ， 共 {pages} 页',
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            onSelectPage: function(pageNumber, pageSize) {
                $(this).pagination('loading');
                $('#serviceFormlist').datagrid('options').url = windowPath + 'proposer_findAll?page=' + pageNumber + '&rows=' + pageSize + '&reponseUser=' + reponseUser + '&sendUserName=' + sendUserName +
                    +'&state=' + state + '&sendDepName=' + sendDepName +'&shunxu='+shunxu + '&startTime='+startTime +'&endTime='+endTime;
                $('#serviceFormlist').datagrid('reload');
                $(this).pagination('loaded');
            }
        });
    });
    $("#insertServiceform").click(function() {
    	  $('body').append('<div id="addServiceform"></div>');
          $('#addServiceform').dialog({
              title: '添加申请单',
              width: 600,
              height: 400,
              closed: false,
              cache: false,
              href: windowPath + 'proposer_addServiceform.action',
              modal: true,
              onClose: function() {
                  $('#addServiceform').remove();
              }
          });
    });
    $("#evaluate").click(function() {
    	 var row = $("#serviceFormlist").datagrid('getSelected');
    	 if(row == null){
    		 $.messager.alert('系统提示', '请选择你要评价的申请单!!', 'warning');
    		 return;
    	 }
         if (row.status != 4) {
        	 $.messager.alert('系统提示', '申请单只有完成后，才能评价。', 'warning');
        	 return;
         }
         if (row.appraise == 1) {
         	$.messager.alert('系统提示', '该申请单已经被评价，不能重复评价', 'warning');
         	return;
         } 
    	$('body').append('<div id="evaluateform"></div>');
    	$('#evaluateform').dialog({
    		title: '评价申请单',
    		width: 600,
    		height: 400,
    		closed: false,
    		cache: false,
    		href: windowPath + 'proposer_evaluateform.action',
    		modal: true,
    		onClose: function() {
    			$('#evaluateform').remove();
    		}
    	});
    });
    $.post(windowPath+'dep_getdepartment.action',function(dataT){
    	var data = JSON.parse(dataT);
  	  	data.unshift({ "text": "请选择", "id": 0 });
    	$('#responseDeptName').combobox({
        	data: data, 
            valueField: 'id',
            textField: 'text',
            filter: filterCombo,
            onSelect: function(data) {
                var url2= windowPath+'project_findById.action?id='+data.id+'&fatherid=0';
            	$('#service').combobox('reload',url2);
            }
        });
    	 $('#aaa').combobox({
    	    	valueField: 'id',
    			textField: 'text',
    	    });
    });
    
    
    /**
     * 选择耗材管理
     */
    $('#supplies_cai').click(function() {
        var row = $("#serviceFormlist").datagrid('getSelected');
        if (row == null) {
        	$.messager.alert('系统提示', '请选择你要处理的耗材订单', 'warning');
            return;
        }
        if (row.status < 4) {
        	$('#suppliesDialog').window('open').window('center');
        }else{
        	$.messager.alert('系统提示', '只有未完成状态下，才能添加耗材！', 'warning');
        	return;
        }
        $('body').append('<div id="suppliesDialog"></div>');
    	$('#suppliesDialog').dialog({
    		title: '耗材',
    		width: 600,
    		height: 400,
    		closed: false,
    		cache: false,
    		href: windowPath + 'proposer_addHaocai.action',
    		modal: true,
    		onClose: function() {
    			$('#suppliesDialog').remove();
    		}
    	});
    })
    $('#supName').combobox({
    	url:windowPath+'supplies_findSup.action',
    	valueField: 'id',
        textField: 'text',
        width:140,
        filter: filterCombo,
        onLoadSuccess:function(data){
        	 var data = $(this).combobox('getData');
             if (data.length > 0) {
                 $(this).combobox('select', data[0].text);
             } 
        },
        onSelect:function(data){
        	supId =data.id;
   	     $('#suppliesDatagrid').datagrid('options').url = windowPath+'supplies_findAll.action?depid='+id+'&supId='+supId;
   	    	$('#suppliesDatagrid').datagrid('reload');
   	    }
    });
    /*物流通知*/
    $('#logisticscall').click(function(){
    	var row = $('#serviceFormlist').datagrid('getSelected');    	
    	if(row == null){
    		$.messager.alert('系统提示', '请选择你要操作的申请单!', 'warning');
    		return;
    	}
    	if(row.status < 4){
    		  $('body').append('<div id="callLogistics"></div>');
    	    	$('#callLogistics').dialog({
    	    		title: '耗材',
    	    		width: 600,
    	    		height: 400,
    	    		closed: false,
    	    		cache: false,
    	    		href: windowPath + 'proposer_logistics.action',
    	    		modal: true,
    	    		onClose: function() {
    	    			$('#callLogistics').remove();
    	    		}
    	    	});
    	}else{
    		$.messager.alert('系统提示', '该申请单无法完成该操作，请联系管理员!', 'warning');
    		return;
    	}
    });
   
    /*物流信息填写*/
    $('#logisticsinfo').click(function(){
    	var row = $('#serviceFormlist').datagrid('getSelected');
    	if(row == null){
    		$.messager.alert('系统提示', '请选择你要操作的申请单!', 'warning');
    		return;
    	}
    	if(row.status < 4 && row.pickdepName != ''){
    		 $('body').append('<div id="infoLogistics"></div>');
    	    	$('#infoLogistics').dialog({
    	    		title: '物流信息填写',
    	    		width: 600,
    	    		height: 450,
    	    		closed: false,
    	    		cache: false,
    	    		href: windowPath + 'proposer_logisticsWrite.action',
    	    		modal: true,
    	    		onClose: function() {
    	    			$('#infoLogistics').remove();
    	    		}
    	    	});
    	}else{
    		$.messager.alert('系统提示', '该申请单不能进行该操作，具体请联系管理员!', 'warning');
    		return;
    	}
    	if(row.pickUser!=null){
    	$("#pickUser").val(row.pickUser);
    	}
    });
    });
function add() {
	var host = window.location.host;
	var windowPath = "http://" + host + "/";
	var row = $("#serviceFormlist").datagrid('getSelected');
	if (row == null) {
		$.messager.alert('系统提示', '请选择你要选择服务的申请单', 'warning');
		return;
	}
	$('body').append('<div id="selectProject"></div>');
	$('#selectProject').dialog({
		title: '选择服务',
		width: 500,
		height: 400,
		closed: false,
		cache: false,
		href: windowPath + 'proposer_addService.action',
		modal: true,
		onClose: function() {
			$('#selectProject').remove();
		}
	});
};
function edit() {
	var host = window.location.host;
	var windowPath = "http://" + host + "/";
	var row = $("#serviceFormlist").datagrid('getSelected');
	if (row == null) {
		$.messager.alert('系统提示', '请选择你要选择服务的申请单', 'warning');
		return;
	}
	$('body').append('<div id="edit1Project"></div>');
	$('#edit1Project').dialog({
		title: '修改服务',
		width: 500,
		height: 400,
		closed: false,
		cache: false,
		href: windowPath + 'proposer_editService.action',
		modal: true,
		onClose: function() {
			$('#edit1Project').remove();
		}
	});
};
