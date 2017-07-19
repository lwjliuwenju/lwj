$(function() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    var deps = JSON.parse(sessionStorage.getItem('deps'));
    var dep = "";
    if (deps.length < 2 && deps.length > 0)
        dep = "reponseDepId=" + deps[0].id;
    $('#responsedepartmentlist').datagrid({
        title: '响应科室申请单管理',
        iconCls: 'icon-wrench',
        checkbox: true,
        width: '100%',
        height: 'auto',
        fitColumns: true,
        rownumbers: true,
        striped: true, //隔行变色
        animate: true,
        collapsible: false,
        collapsed: false,
        singleSelect: true,
        pageNum: 1, //每次重新查询设置为第一页，这里重要，测试一下
        pageSize: 15,
        pageList: [15, 30, 50], //可以设置每页记录条数的列表 
        pagination: true, // 分页控件
        toolbar: '#departmentTools',
        url: windowPath + 'reponse_findAll.action?' + dep,
        columns: [
            [
                { field: 'ck', checkbox: true },
                { field: 'DEP_NAME_', title: '申请科室名称', width: 100, align: 'center' }, {
                    field: 'RESPONSE_START_TIME_',
                    title: '响应开始时间',
                    width: 100,
                    align: 'center',
                    formatter: function(value) {
                        if (value == null) {
                            return "";
                        } else {
                            return new Date(value.time).toLocaleString();
                        }
                    }
                }, {
                    field: 'RESPONSE_END_TIME_',
                    title: '响应结束时间',
                    width: 100,
                    align: 'center',
                    formatter: function(value) {
                        if (value == null) {
                            return "";
                        } else {
                            return new Date(value.time).toLocaleString();
                        }
                    }
                },
                { field: 'staffNames', title: '响应人员', width: 100, align: 'center' },
                { field: 'PROJECT_NAME_', title: '服务名称', width: 100, align: 'center' },
                { field: 'STANDARD_HOUR_', title: '服务标准工时', width: 100, align: 'center' },
                { field: 'TERMINATION_REASON_', title: '终止原因', width: 100, align: 'center' },
                { field: 'RESPONSE_TIMES_', title: '响应次数', width: 100, align: 'center' },
                { field: 'TERMINATION_REASON_USER_NAME_', title: '终止人姓名', width: 100, align: 'center' },
                {
                    field: 'STATUS_',
                    title: '状态',
                    width: 85,
                    align: 'center',
                    formatter: function(value) {
                        if (value == '1') {
                            return '<span style="color:red">异常</span>';
                        } else if (value == '2') {
                            return '<span style="color:green">待响应</span>';
                        } else if (value == '3') {
                            return '<span style="color:#00CED1;font-weight: bold;">响应中</span>';
                        } else if (value == '4') {
                            return '<span style="color:#ccc">已完成</span>';
                        } else if (value == '5') {
                            return '<span style="color:blue">已终止</span>';
                        } else if (value == '6') {
                        	return "已评价";
                        } else if (value == '7') {
                            return '<span style="color:	#00FFFF;font-weight: bold;">挂起</span>';
                        }
                    }
                }
            ]
        ],
        onLoadSuccess:function(data){
            $.post(windowPath + 'dep_getdepartment.action',function(dataT){
                var data = JSON.parse(dataT);
                data.unshift({ "text": "请选择", "id": 0 });
                $('#shenqingdepartment').combobox({
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
            $('#jieshoubumen').combobox({
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
            $.post(windowPath + 'user_getusers.action',function(dataT){
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
            $.post(windowPath + 'user_getuser.action',function(dataT){
                var data = JSON.parse(dataT);
                data.unshift({ "text": "请选择", "id": 0 });
                $('#zhixingperson').combobox({
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
            //设置分页控件
            $('#responsedepartmentlist').datagrid('getPager').pagination({
                beforePageText: '第', //页数文本框前显示的汉字
                afterPageText: '页  ， 共 {pages} 页',
                displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
                onSelectPage: function(pageNumber, pageSize) {
                    $(this).pagination('loading');
                    $('#responsedepartmentlist').datagrid('options').url = windowPath + 'reponse_findAll.action?page=' + pageNumber + '&rows=' + pageSize;
                    $('#responsedepartmentlist').datagrid('reload');
                    $(this).pagination('loaded');
                }
            });
            /*生成申请单*/
            $("#insertServiceform").click(function() {
                $('body').append('<div id="addServiceform"></div>');
                $('#addServiceform').dialog({
                    title: '生成申请单',
                    width: 600,
                    height: 400,
                    closed: false,
                    cache: false,
                    href: windowPath + 'reponse_generatefrom.action',
                    modal: true,
                    onClose: function() {
                        $('#addServiceform').remove();
                    }
                });
            });
            $("#fanxiu").click(function() {
                var rows = $("#responsedepartmentlist").datagrid('getSelected');
                var proId = rows.ID_;
                var state = rows.STATUS_;
                if (state >= 4) {
                    $.messager.confirm('系统提示', '是否返修?', function(r){
                        if (r){
                            var url = windowPath + 'reponse_fanxiu.action'
                            $.post(url, { 'proId': proId }, function(data) {
                                var code = JSON.parse(data).code_;
                                if (code == '0') {
                                    $.messager.alert('系统提示', '返修成功!', 'info');
                                    location.reload();
                                } else {
                                    $.messager.alert('系统提示', '返修失败!', 'error');
                                }
                            });
                        }else{
                            $.messager.alert('系统提示', '你已取消了返修!', 'info');
                        }
                    });
                }else{
                    $.messager.alert('系统提示', '该申请单没有完成!', 'warning');
                    return;
                }
            });
            $("#gq").click(function() {
                var rows = $("#responsedepartmentlist").datagrid('getSelected');
                var proId = rows.ID_;
                $.messager.confirm('系统提示', '是否挂起?', function(r){
                    if (r){
                        var url = windowPath + 'reponse_gq.action'
                        $.post(url, { 'proId': proId }, function(data) {
                            var code = JSON.parse(data).code_;
                            if (code == '0') {
                                $.messager.alert('系统提示', '挂起成功!', 'info');
                                location.reload();
                            } else {
                                $.messager.alert('系统提示', '挂起失败!', 'error');
                            }
                        });
                    }
                });
            });
            $("#qxgq").click(function() {
                var rows = $("#responsedepartmentlist").datagrid('getSelected');
                if(rows.GQBJ_ != 1){
                    $.messager.alert('系统提示', '无法取消挂起!', 'error');
                    return;
                }
                var proId = rows.ID_;
                $.messager.confirm('系统提示', '是否取消挂起?', function(r){
                    if (r){
                        var url = windowPath + 'reponse_qxgq.action'
                        $.post(url, { 'proId': proId }, function(data) {
                            var code = JSON.parse(data).code_;
                            if (code == '0') {
                                $.messager.alert('系统提示', '取消挂起成功!', 'info');
                                location.reload();
                            } else {
                                $.messager.alert('系统提示', '取消挂起失败!', 'error');
                            }
                        });
                    }
                });
            });
            function pqry() {
                var row = $('#responsedepartmentlist').datagrid('getSelected');
                if(row == null){
                    $.messager.alert('系统提示', '请选择你要操作的申请单!', 'error');
                    return;
                }
                if (row.STATUS_ >= 4) {
                    $.messager.alert('系统提示', '申请单只有在未完成状态才能派人!', 'error');
                    return;
                }

                var userid = sessionStorage.getItem("userid");
                $('#dg').datagrid({
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
                    ],
                    onLoadSuccess:function(row){//当表格成功加载时执行
                        var rowData = row.rows;
                        var rowT = $('#responsedepartmentlist').datagrid('getSelected');
                        var staffIds = rowT.staffIds.split(",");
                        $.each(rowData,function(idx,val){//遍历JSON
                            if($.inArray("" + val.id, staffIds) > 0){
                                $("#dg").datagrid("selectRow", idx);//如果数据行为已选中则选中改行
                            }
                        });
                    }
                });
                if (row) {
                    $("#dd").dialog('open');
                }
            }
            $("#sendrenyuan").click(function (){
                var rows = $("#responsedepartmentlist").datagrid("getSelected");
                if(rows.staffNames!=""){
                    $.messager.alert('系统提示', '申请单已经派人，不能再派人!', 'error');
                    return;
                }
                pqry()
            });
            $("#reassignment").click(function (){
                var rows = $("#responsedepartmentlist").datagrid("getSelected");
                if(rows.staffNames==""){
                    $.messager.alert('系统提示', '申请单没有派人，不能改派!', 'error');
                    return;
                }
                pqry()
            });
            $("#reassignmentstaff").click(function() {
                var rows = $("#responsedepartmentlist").datagrid("getSelected");
                if (rows == null) {
                    $.messager.alert('系统提示', '请选择你要改派人员的响应单!', 'warning');
                    return;
                }
                var row = $("#dg2").datagrid("getSelections");
                var userId = [];
                for (var i = 0; i < row.length; i++) {
                    userId.push(row[i].id);
                }
                var proId = rows.ID_;
                var url2 = windowPath + 'reponse_saverenyuan.action';
                var params = { 'userId': userId, 'proId': proId };
                $.post(url2, params,
                    function(data) {
                        var code = JSON.parse(data).code_;
                        if (code == '0') {
                            $.messager.alert('系统提示', '改派成功!', 'info',function(){
                                location.reload();
                            });

                        } else {
                            $.messager.alert('系统提示', '改派失败!', 'error');
                        }
                    });
            });
            $("#cancelstaff").click(function() {
                $("#dd2").dialog('close');
            });
            $("#saverenyuan").click(function() {
                var rows = $("#responsedepartmentlist").datagrid("getSelected");
                var wxValue = $('.wx:radio:checked').val();
                if (rows == null) {
                    $.messager.alert('系统提示', '请选择你要派遣人员的响应单!', 'warning');
                    return;
                }
                //不外修
                if (wxValue == 0) {
                    var row = $("#dg").datagrid("getSelections");
                    var userId = [];
                    for (var i = 0; i < row.length; i++) {
                        userId.push(row[i].id);
                    }
                    var proId = rows.ID_;
                    var flag = $('input:radio:checked').val();
                    var url2 = windowPath + 'reponse_saverenyuan.action';
                    var params = { 'userId': userId, 'proId': proId, 'flag': flag };
                    $.post(url2, params,
                        function(data) {
                            var code = JSON.parse(data).code_;
                            if (code == '0') {
                                $.messager.alert('系统提示', '派遣成功!', 'info',function(){
                                    location.reload();
                                });


                            } else {
                                $.messager.alert('系统提示', '派遣失败,请重试!', 'error');
                            }
                        })

                } else if (wxValue == 1) { //外修
                    var rows = $("#responsedepartmentlist").datagrid("getSelected");
                    var proId = rows.ID_;
                    var flag = $('input:radio:checked').val();
                    var repairReason = $('#repairReason').val();
                    var url2 = windowPath + 'reponse_saverenyuan.action';
                    var params = { 'proId': proId, 'flag': flag, 'repairReason': repairReason };
                    $.post(url2, params,
                        function(data) {
                            var code = JSON.parse(data).code_;
                            if (code == '0') {
                                $.messager.alert('系统提示', '外修成功!', 'info');

                                location.reload();

                            } else {
                                $.messager.alert('系统提示', '外修失败,请重试!', 'error');
                            }
                        })
                }


            });
            $("#cancelrenyuan").click(function() {
                $("#dd").dialog('close');
            });
            $("#shenhe").click(function() {
                var rows = $("#responsedepartmentlist").datagrid("getSelected");
                if (rows == null) {
                    $.messager.alert('系统提示', '请选择你要完成的订单!', 'warning');
                    return;
                }
                if (rows.STATUS_ ==4||rows.STATUS_==6) {
                    $('body').append('<div id="shenheDialog"></div>');
                    $('#shenheDialog').dialog({
                        title: '审核页面',
                        width: 600,
                        height: 400,
                        closed: false,
                        cache: false,
                        href: windowPath + 'reponse_shenheform.action',
                        modal: true,
                        onClose: function() {
                            $('#shenheDialog').remove();
                        }
                    });
                } else {
                    $.messager.alert('系统提示', '只有完成或评价才能进行审核操作!', 'warning');
                    return;
                }

            });
            $("#breakform").click(function() {
                var rows = $("#responsedepartmentlist").datagrid("getSelected");
                if (rows == null) {
                    $.messager.alert('系统提示', '请选择你要修改的响应单!', 'warning');
                    return;
                }
                if (rows.STATUS_ < 4 ) {
                    $('body').append('<div id="breakformDialog"></div>');
                    $('#breakformDialog').dialog({
                        title: '终止响应单',
                        width: 500,
                        height: 300,
                        closed: false,
                        cache: false,
                        href: windowPath + 'reponse_breakform.action',
                        modal: true,
                        onClose: function() {
                            $('#breakformDialog').remove();
                        }
                    });
                }else{
                    $.messager.alert('系统提示', '该申请单禁止终止!!', 'warning');
                    return;
                }
            });
            $("#seeDetail").click(function() {
                var row = $("#responsedepartmentlist").datagrid('getSelected');
                if (row == null) {
                    $.messager.alert('系统提示', '请选择你要查询的响应单!', 'warning');
                    return;
                }
                $('body').append('<div id="detailsDialog"></div>');
                $('#detailsDialog').dialog({
                    title: '查看详情',
                    width: 600,
                    height: 400,
                    closed: false,
                    cache: false,
                    href: windowPath + 'proposer_detailform.action?id=' + row.ID_,
                    modal: true,
                    onClose: function() {
                        $('#detailsDialog').remove();
                    }
                });
            });
            $("#searchResponse").click(function() {
                var responseUserName = $("#zhixingperson").combobox('getText');
                if(responseUserName == "请选择")
                    responseUserName = undefined;
                var userName	 = $("#submitperson").combobox('getText');
                if(userName == "请选择")
                    userName = undefined;
                var shunxu = $("#shunxu").val();
                var state = $("#state").val();
                var proposerDepId = $("#shenqingdepartment").combobox('getValue');
                if(proposerDepId == "0")
                    proposerDepId = undefined;
                var reponseDepName = $("#jieshoubumen").combobox('getText');
                var startTime = $("#startTime").datebox('getValue');
                var endTime = $("#endTime").datebox('getValue');
                var dept;
                if (deps.length < 2)
                    dept = deps[0].id;
                $('#responsedepartmentlist').datagrid({
                    url: windowPath + 'reponse_findAll.action',
                    //pageNum:1,//每次重新查询设置为第一页，这里重要，测试一下
                    queryParams: {
                        'responseUserName': responseUserName,
                        'userName': userName,
                        'shunxu': shunxu,
                        'state': state,
                        'proposerDepId': proposerDepId,
                        'reponseDepName': reponseDepName,
                        'startTime':startTime,
                        'endTime':endTime
                    }
                });
                $('#responsedepartmentlist').datagrid('getPager').pagination({
                    beforePageText: '第', //页数文本框前显示的汉字
                    afterPageText: '页  ， 共 {pages} 页',
                    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
                    onSelectPage: function(pageNumber, pageSize) {
                        $(this).pagination('loading');
                        $('#responsedepartmentlist').datagrid('options').url = windowPath + 'reponse_findAll?page=' + pageNumber + '&rows=' + pageSize + '&responseUserName=' + responseUserName + '&userName=' + userName + '&shunxu=' +
                            shunxu + '&state=' + state + '&proposerDepId=' +proposerDepId  + '&reponseDepName=' + reponseDepName + '&startTime='+startTime +'&endTime='+endTime;
                        $('#responsedepartmentlist').datagrid('reload');
                        $(this).pagination('loaded');
                    }
                });
            });
        }
    });
});
