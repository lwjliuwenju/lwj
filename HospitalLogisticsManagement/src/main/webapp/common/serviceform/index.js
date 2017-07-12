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
                    align: 'center',
                    formatter: function(value) {
                        if (value == 1) {
                            return '<span style="color:green">优</span>'; }
                        if (value == 2) {
                            return '<span style="color:red">良</span>'; }
                        if (value == 3) {
                            return '<span style="color:blue">中</span>'; }
                        if (value == 4) {
                            return '<span style="color:white">差</span>'; } else {
                            return "未评价"; }
                    }
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
    $("#evaluate").click(function() {
        var row = $("#serviceFormlist").datagrid('getSelected');
        if (row.status != 4) {
             $.messager.alert('系统提示', '申请单只有完成后，才能评价。', 'warning');
            return;
        }
        if (row.appraise == 1) {
        	$.messager.alert('系统提示', '该申请单已经被评价，不能重复评价', 'warning');
            
        } else {
            $('#evaluateform').window('open').window('center');
            
            var row = $("#serviceFormlist").datagrid('getSelected');
            var proposerName = $("#proposerName").val(row.depName);
            var e =row.endTime;
            var s;
            var startTime = row.responseTime;
            if(startTime != null)
            	s = startTime;
            else
            	s = e;
            $("#endtime").val(e);
            $("#xiangyingtime").val(s);
            $("#proposerDepName").val(row.depName);
        }
    });
        $("#save").click(function() {
            var url2 = windowPath+'proposer_evaluate.action';
            var row = $("#serviceFormlist").datagrid('getSelected');
            var appraise = $('.mt:radio:checked').attr('data-option');
	        var appraiseMark = $('#mark1').val();
	        var proposerId = row.id;
            $.post(url2, {'appraise':appraise,'appraiseMark':appraiseMark,'proposerId':proposerId},
                function(data) {
                    var code = JSON.parse(data).code_;
                    if (code == '0') {
                    	$.messager.alert('系统提示', '评价成功', 'info',function(){
                    		   location.reload();
                    	});
                    } else {
                    	$.messager.alert('系统提示', '评价失败', 'error');
                    }
                });
        });
        $("#close").click(function(){
           	$('#evaluateform').dialog('close');
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
    	$('#addServiceform').window('open').window('center');
    });
    $.post(windowPath+'dep_getdepartment.action',function(dataT){
    	var data = JSON.parse(dataT);
  	  	data.unshift({ "text": "请选择", "id": 0 });
    	$('#responseDeptName').combobox({
        	data: data, 
            valueField: 'id',
            textField: 'text',
            filter: filterCombo
        });
    });
    
    $("#save_add").click(function() {
        var mark = $("#mark").val();
        var responseDeptid = $("#responseDeptName").combobox('getValue');
        var sendDeptId = $("#shenqingdepartment2").combobox('getValue');
        var url2 = windowPath+'proposer_saveProposer.action';
        $.post(url2, {
                mark: mark,
                responseDeptid: responseDeptid,
                sendDeptId: sendDeptId
            },
            function(data) {
                var code = JSON.parse(data).code_;
                if (code == '0') {
                	 $.messager.alert('系统提示', '添加成功', 'info',function(){
                		 location.reload();
                	 });
                } else {
                	 $.messager.alert('系统提示', '添加失败', 'error');
                }
            });
    });
    $('#close_add').click(function(){
    	$('#addServiceform').dialog('close');
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
    })
    $('#close-s').click(function() {
        $('#suppliesDialog').dialog('close');
    });
    $('#suppliesDatagrid').datagrid({
        iconCls: 'icon-wrench',
        checkbox: true,
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        rownumbers: true,
        animate: true,
        collapsible: false,
        collapsed: false,
        striped: true, //隔行变色
        singleSelect: false,
        autoSave: true,
        pageNum: 1, //每次重新查询设置为第一页，这里重要，测试一下
        pageSize: 15,
        pageList: [15, 30, 50], //可以设置每页记录条数的列表 
        pagination: true, // 分页控件
        url: windowPath + 'supplies_findAll.action?depid='+id,
        columns: [
            [
                { field: 'ck', checkbox: true },
                { field: 'id', title: '耗材ID', width: 60, hidden: true },
                { field: 'name', title: '耗材名称', width: 100, align: 'center' }, {
                    field: 'suppliesNum',
                    title: '耗材数量',
                    width: 100,
                    align: 'center',
                    editor: {
                        type: 'numberbox',
                        options: {
                            required: true //校验必须
                        }
                    }

                }
            ]
        ],

        onClickRow: onClickRow,
        onAfterEdit: function(rowIndex, rowData, changes) {
            //保存编辑行数据
            var a = rowData.suppliesNum;
            var id = rowData.id;
            $('#suppliesDatagrid').datagrid('updateRow', {
                index: rowIndex,
                row: {
                    'id': id,
                    suppliesNum: a
                }
            });
        }
    });
    $('#supDep').combobox({
    	data:deps,
    	valueField: 'id',
        textField: 'text',
        width:140,
        filter: filterCombo,
        onLoadSuccess:function(){
        	 var data = $(this).combobox('getData');
             if (data.length > 0) {
                 $(this).combobox('select', data[0].text);
             } 
        },
        onSelect:function(data){
        	id = data.id;
   	     $('#suppliesDatagrid').datagrid('options').url = windowPath+'supplies_findAll.action?depid='+id + "&supId="+ supId;
   	    	$('#suppliesDatagrid').datagrid('reload');
   	    }
    });
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
    //记录当前处于编辑状态行索引
    var editIndex = undefined;

    function endEditing() {
        //没有编辑
        if (editIndex == undefined) {
            return true
        }
        //数据校验
        if ($('#suppliesDatagrid').datagrid('validateRow', editIndex)) {
            //校验通过技术编辑
            $('#suppliesDatagrid').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            //校验没有通过继续编辑
            return false;
        }
    }
    //行点击事件
    function onClickRow(index) {
        //记录的编辑行， 和当前点击行 不能同一样
        if (editIndex != index) {
            //结束之前的编辑
            if (endEditing()) {
                //让当前点击行进行编辑
                $('#suppliesDatagrid').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
                //记录编辑行
                editIndex = index;
            } else {
                //选中当前行
                $('#suppliesDatagrid').datagrid('selectRow', editIndex);
            }
        }
    };
    
    //保存耗材
    $('#save-s').click(function() {
        endEditing();

        var row = $("#serviceFormlist").datagrid('getSelected');
        //申请单ID
        var proposerId = row.id;
        
        //耗材ID
        var supplies = [];
        //耗材数量
        var rows = $('#suppliesDatagrid').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            supplies.push(rows[i].id + ":" + rows[i].suppliesNum);
            if (rows[i].suppliesNum == null) {
                $.messager.alert('系统提示', '请选择耗材数量', 'warning');
                return;
            }
        }
        $.post(windowPath + 'suppliesproposer_saveSupplies.action', { proposerId: proposerId, supplies: supplies }, function(data) {
            var code = JSON.parse(data).code_;
            if (code == '0') {
            	$.messager.alert('系统提示', '选择耗材成功', 'info',function(){
            		 location.reload();
            	});
            } else {
               $.messager.alert('系统提示', '选择耗材失败，请重试!', 'error');
            }
        });
    });
    /*物流通知*/
    $('#logisticscall').click(function(){
    	var row = $('#serviceFormlist').datagrid('getSelected');    	
    	if(row == null){
    		$.messager.alert('系统提示', '请选择你要操作的申请单!', 'warning');
    		return;
    	}
    	if(row.status < 4){
    		$('#callLogistics').window('open').window('center');
    	}else{
    		$.messager.alert('系统提示', '该申请单无法完成该操作，请联系管理员!', 'warning');
    		return;
    	}
    });
    $('#close-call').click(function(){
    	$('#callLogistics').dialog('close');
    });
    $('#wuliuteam').combobox({
    	 url:windowPath+'dep_findByTransport.action' ,
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
    $('#save-call').click(function(){
    	var depid = $('#wuliuteam').combobox('getValue');
    	var proId = $('#serviceFormlist').datagrid('getSelected').id;
    	var goodsName = $('#wupinName').val();
    	var url = windowPath + 'proposer_sendmessager.action';
    	$.post(url,{depid:depid,goodsName:goodsName,proId:proId},function(data){
    		 var code = JSON.parse(data).code_;
             if (code == '0') {
             	$.messager.alert('系统提示', '物流请求信息已发出!', 'info');
                 location.reload();
             } else {
                $.messager.alert('系统提示', '物流请求信息发送失败，请重试!', 'error');
             }
    	});
    });
    function addtr() {
        var str = '<tr class="lst">' + '<td align="center">' +
            '<input type="text" style="width: 90%;" class="bloodUser">' +
            '</td>' + '<td align="center">' +
            '<input type="text"  style="width: 90%;" class="bloodPos">' +
            '</td>' + '<td align="center">' +
            '<input type="datetime"  style="width: 90%;" class="bloodDate">' +
            '</td>' + '<td align="center">' +
            '<input type="text"  style="width: 90%;" class="bloodDep">' +
            '</td>' + '<td align="center">' +
            '<input type="number"  style="width: 90%;" min="1" class="bloodNum">' +
            '</td>' + '<td align="center">' +
            '<input type="button" value="删除" class="ButtonStyle_Blue del">' +
            '</td>' + '</tr>';
    $('#pick').before(str);
    var lastBloodDate = $('#tb').find('.bloodDate:last');
    var lastBloodDep = $('#tb').find('.bloodDep:last');
    /*$('.bloodDate').datetimebox();*/
    lastBloodDate.datetimebox();
    lastBloodDep.combobox({
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
	};
	var flag =  0;
	if(flag == 0){
		$('.bloodDep').combobox({
		   url: windowPath + 'dep_getdepartment.action',
	       valueField: 'id',
	       textField: 'text',
	       width:150,
           filter: filterCombo,
           onLoadSuccess:function(){
          	 var data = $(this).combobox('getData');
               if (data.length > 0) {
                   $(this).combobox('select', data[0].text);
               } 
          }
		});
		flag = 1;
	};
    $('#add').click(function() {
        addtr();
    });
    $('#tb').on('click', '.del', function() {
        $(this).parent().parent().remove();
    });
    /*物流信息填写*/
    $('#logisticsinfo').click(function(){
    	var row = $('#serviceFormlist').datagrid('getSelected');
    	if(row == null){
    		$.messager.alert('系统提示', '请选择你要操作的申请单!', 'warning');
    		return;
    	}
    	if(row.status < 4){
    		$('#infoLogistics').window('open').window('center');
    	}else{
    		$.messager.alert('系统提示', '该申请单不能进行该操作，具体请联系管理员!', 'warning');
    		return;
    	}
    	if(row.pickUser!=null){
    	$("#pickUser").val(row.pickUser);
    	}
    });
    $('#close-info').click(function(){
    	$('#infoLogistics').dialog('close');
    });
    $('#save-info').click(function() {
        var arr = [];
        var allList = $('.lst');
        for (var i = 0; i < allList.length; i++) {
            var blood = {};
            blood.bloodUser = $($('.bloodUser')[i]).val();
            blood.bloodPos = $($('.bloodPos')[i]).val();
            blood.bloodDate = $($('.bloodDate')[i]).datetimebox('getValue');
            blood.bloodDep = $($('.bloodDep')[i]).val();
            blood.bloodNum = $($('.bloodNum')[i]).val();
            arr.push(blood);
        }
        var object = {};
        object.proid = $('#serviceFormlist').datagrid('getSelected').id;
        object.pickUser = $('#pickUser').val();
        object.pickPhone = $('#pickPhone').val();
        object.arr = arr;
        var url = windowPath + 'proposer_saveLogistics.action';
        $.post(url,{object:JSON.stringify(object)},function(data){
        	var code = JSON.parse(data).code_;
            if (code == '0') {
            	$.messager.alert('系统提示', '物流信息已提交!', 'info',function(){
            		  location.reload();
            	});
              
            } else {
               $.messager.alert('系统提示', '物流信息提交失败，请重试!', 'error');
            }
        })
    });
    });
function add() {
	var host = window.location.host;
	var windowPath = "http://" + host + "/";
	var row = $("#serviceFormlist").datagrid('getSelected');
	if (row == null) {
		$.messager.alert('系统提示', '请选择你要选择服务的申请单', 'warning');
	}
	var proId = row.id;
	$('#selectProject').window('open').window('center');
	$('#responsedepname').combobox({
		url: windowPath+'dep_getdepartment.action', 
		valueField: 'id',
		textField: 'text',
		editable:true,
		onSelect: function(data){
			var url= windowPath+'project_findById.action?id='+data.id;
			$('#responseDeptitem').combobox('reload',url);
		} 
	});
	$('#responseDeptitem').combobox({
		//url:'combobox_data.json',
		valueField: 'id',
		textField: 'text',
		onSelect: function (data) {
			var itemid =data.id;
			if(itemid!=""){
				$("#responsenewDeptitem").attr("disabled","disabled");
			}				
		}
	});
	$('#save_pro').click(function(){
		var proId =$("#serviceFormlist").datagrid("getSelected").id;
		var responseDeptid = $("#responsedepname").combobox('getValue');
		var responseDeptitem = $("#responseDeptitem").combobox('getText');
		var ProposerId = $("#responseDeptitem").combobox('getValue');
		var url2 = windowPath+'proposer_saveproject.action';
		$.post(url2, {
			responseDeptitem: responseDeptitem,
			ProposerId:ProposerId,
			responseDeptid:responseDeptid,
			proId:proId
		},
		function(data) {
			var code = JSON.parse(data).code_;
			if (code == '0') {
				$.messager.alert('系统提示', '选择服务成功!', 'info',function(){
					location.reload();
				});
			} else {
				$.messager.alert('系统提示','选择服务失败,请重试!','error');
			}
		});
	});
	$('#close_pro').click(function(){
		$('#selectProject').window('open').window('center');
	});
};
function edit() {
	var host = window.location.host;
	var windowPath = "http://" + host + "/";
	var row = $("#serviceFormlist").datagrid('getSelected');
	if (row == null) {
		$.messager.alert('系统提示', '请选择你要选择服务的申请单', 'warning');
	}
	var proId = row.id;
	$('#edit1Project').window('open').window('center');
	$('#repdepname').combobox({
		url: windowPath+'dep_getdepartment.action', 
		valueField: 'id',
		textField: 'text',
		editable:true,
		onSelect: function(data){
			var url= windowPath+'project_findById.action?id='+data.id;
			$('#responsedeptitem').combobox('reload',url);
		} 
	});
	$('#responsedeptitem').combobox({
		//url:'combobox_data.json',
		valueField: 'id',
		textField: 'text',
		onSelect: function (data) {
			var itemid =data.id;
		}
	});
	$("#repdepname").combobox('setValue',row.reponseDepId);
	$("#repdepname").combobox('setText',row.responseDepName);
	$("#responsedeptitem").combobox('setValue',row.projectId);
	$("#responsedeptitem").combobox('setText',row.projectName);
	$("#save_edit").click(function() {
		var proId =$("#serviceFormlist").datagrid("getSelected").id;
		var responseDeptid = $("#repdepname").combobox('getValue');
		var responseDeptitem = $("#responsedeptitem").combobox('getText');
		var ProposerId = $("#responsedeptitem").combobox('getValue');
		var url2 = windowPath+'proposer_saveproject.action';
		$.post(url2, {
			responseDeptitem: responseDeptitem,
			ProposerId:ProposerId,
			responseDeptid:responseDeptid,
			proId:proId
		},
		function(data) {
			var code = JSON.parse(data).code_;
			if (code == '0') {
				$.messager.alert('系统提示', '修改服务成功!', 'info',function(){
					location.reload();
				});
			} else {
				$.messager.alert('系统提示', '修改服务失败!', 'info');
			}
		});
	});
	$('#close_edit').click(function(){
		$('#edit1Project').dialog('close');
	});
};
