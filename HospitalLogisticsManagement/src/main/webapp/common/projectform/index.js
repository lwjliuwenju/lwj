$(function() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    $('#projectList').datagrid({
        title: '服务管理',
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
        url: windowPath + 'project_findByDepId.action?id=-1',
        columns: [
            [
                { field: 'ck', checkbox: true },
                /* {field:'CREATE_STAMP_',title:'创建时间',width:100,align:'center',formatter:function(value){
		    	return new Date(value.time).toLocaleString();
		    }},*/

                { field: 'projectId', title: 'ID_', width: 80, hidden: true },
                { field: 'projectName', title: '服务名称', width: 80 },
                { field: 'grade', title: '服务分值', width: 80 },
                { field: 'standardHour', title: '标准工时', width: 80 }, {
                    field: 'startFlag',
                    title: '登记标记',
                    width: 80,
                    formatter: function(value) {
                        if (value == null || value == 0) {
                            return "未登记"
                        } else {
                            return "已登记"
                        }
                    }
                }
            ]
        ],
        onRowContextMenu: function(e, rowIndex, rowData) {
            e.preventDefault();
            $(this).datagrid("clearSelections");
            $(this).datagrid("selectRow", rowIndex);
            $('#menu1').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
            e.preventDefault();
        }
    });
    var deps = JSON.parse(sessionStorage.getItem('deps'));
    console.log(deps);
    $('#department').combobox({
        data: deps,
        valueField: 'id',
        textField: 'text',
        filter: filterCombo,
        onLoadSuccess: function() {
            var data = $('#department').combobox('getData');
            if (data.length > 0) {
                $('#department').combobox('select', data[0].text);
            }
        },
        onSelect: function(data) {
            $('#projectList').datagrid('options').url = windowPath + 'project_findByDepId.action?id=' + data.id;
            $('#projectList').datagrid('reload');
        }
    });
    $('#depname').combobox({
        data: deps,
        valueField: 'id',
        textField: 'text',
        filter: filterCombo	
    });
    //		保存
    $('#save1').click(function() {
        var depId = $('#depname').combobox('getValue');
        var depName = $('#depname').combobox('getText');
        var projectname = $('#projectname').val();
        var standardtime = $('#standardtime').val();
        var url = windowPath + 'project_addproject.action';
        var grade = $('#projectNum').val();
        $.post(url, { projectname: projectname, depId: depId, depName: depName, standardtime: standardtime,grade:grade },
            function(data) {
                var code = JSON.parse(data).code_;
                if (code == '0') {
                    $.messager.alert('系统提示', '添加成功!', 'info');
                    $("#dd").dialog('close');
                    location.reload();

                } else {
                    $.messager.alert('系统提示', '添加失败!', 'error');
                }
            });
    });
    //删除
    $('#delProject').click(function() {
        var node = $('#projectList').datagrid('getSelections');
   
        $.messager.confirm('系统提示', '确定删除该服务?', function(r){
        	if (r){
        		 var url = windowPath + 'project_delproject.action';
        		 var id = [];
        		 for(var i = 0; i<node.length;i++){
        			 id.push(node[i].id);
        		 }
                 $.post(url, { 'id': id }, function(data) {
                     var code = JSON.parse(data).code_;
                     if (code == '0') {
                         $.messager.alert('系统提示', '删除成功!', 'info');
                         location.reload();
                     } else {
                         $.messager.alert('系统提示', '删除失败!', 'error');
                         return;
                     }
                 });
        	}else{
        		$.messager.alert('系统提示', '你已取消了删除服务!', 'info');
        		return;
        	}
        });
        
    });
    //添加项目
    $('#addProject').click(function() {
        $('#addProjectDialog').dialog('open');
    });
    $('#close').click(function() {
        $('#addProjectDialog').dialog('close');
    });
    $('#shut').click(function() {
        $('#editProjectDialog').dialog('close');
    });
    //修改项目
    $('#editProject').click(function() {
        var row = $('#projectList').datagrid('getSelected');
        var val = row.projectName;
        var grades =row.grade;
        var standertime =row.standardHour;
        $('#projectnum').numberbox('setValue', grades);
        $("#standardHour").val(standertime);
        $('#projectName').val(val);
        if (row == null) {
            $.messager.alert('系统提示', '请选择你要修改的项目!', 'warning');
            return;
        }
        $('#editProjectDialog').dialog('open');
    });
    $("#keep").click(function() {
        var row = $('#projectList').datagrid('getSelected');
        var id = row.id;
        var projectName = $('#projectName').val();
        var standardHour = $('#standardHour').val();
        var grade =  $('#projectnum').val();
        var url = windowPath + 'project_update.action';
        $.post(url, { id: id, projectName: projectName, standardHour: standardHour,grade:grade },
            function(data) {
                var code = JSON.parse(data).code_;
                if (code == '0') {
                    $.messager.alert('系统提示', '修改成功!', 'info');
                    location.reload();

                } else {
                    $.messager.alert('系统提示', '修改失败!', 'error');
                }
            });
    })


});

function reg() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    var rows = $("#projectList").datagrid('getSelected');
    var id = rows.id
    var url2 = windowPath + 'project_editdengji.action';
    $.post(url2, { id: id },
        function(data) {
            var code = JSON.parse(data).code_;
            if (code == '0') {
                $.messager.alert('系统提示', '登记成功!', 'info');
                location.reload();

            } else {
                $.messager.alert('系统提示', '登记失败!', 'error');
            }
        });
}
