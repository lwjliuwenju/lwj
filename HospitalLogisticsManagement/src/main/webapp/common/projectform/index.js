$(function() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    var id =-1;
    $('#projectList').treegrid({
        title: '服务管理',
      //checkbox: true,
        //iconCls: 'icon-tip',
        width: '100%',
        height: 'auto',
        idField: 'id',
        treeField: 'name',
        pageList: [15, 30, 50], //可以设置每页记录条数的列表 
        //		pagination:true,// 分页控件
        fitConlumns: true,
        rownumbers: true,
        animate: true,
        collapsible: false,
        collapsed: false,
        singleSelect: true,
        striped: true, //隔行变色
        url: windowPath + 'project_findByDepId.action?id='+id,
        columns: [
            [
                { field: 'ck', checkbox: true },
                { field: 'id', title: 'ID_',hidden: true },
                { field: 'name', title: '服务名称',align:"left",width:"25%"},
               { field: 'grade', title: '服务分值',align:"center",width:"25%" },
                { field: 'standardHour', title: '标准工时',align:"center",width:"25%"},
                {
                    field: 'startFlag',
                    title: '登记标记',
                    align:"center",
                    width:"25%",
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
        onContextMenu: function(e, row) {
            e.preventDefault();
            $(this).treegrid("select", row.id);
            $('#menu1').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
            e.preventDefault();
        }
    });
    var deps = JSON.parse(sessionStorage.getItem('deps'));
    deps.unshift({"id":-1,"text":"请选择"})
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
        	id =data.id
            $('#projectList').treegrid('options').url = windowPath + 'project_findByDepId.action?id=' + id;
            $('#projectList').treegrid('reload');
        }
    });
  
    $('#depname').combobox({
        data: deps,
        valueField: 'id',
        textField: 'text',
        filter: filterCombo,
    /*    onSelect: function(data) {
            var url2= windowPath+'project_findById.action?id='+data.id+'&fatherid=0';
        	$('#service').combobox('reload',url2);
        }*/
    });
/*    $('#service').combobox({
  	  valueField: 'id',
        textField: 'text',
        filter: filterCombo
  });*/
    //		保存
    $('#save1').click(function() {
        var depId = $('#depname').combobox('getValue');
        var depName = $('#depname').combobox('getText');
        var fatherId = 0;
        var projectname = $('#projectname').val();
        var standardtime = $('#standardtime').val();
        var url = windowPath + 'project_addproject.action';
        var grade = $('#projectNum').val();
        $.post(url, { projectname: projectname, depId: depId, depName: depName, standardtime: standardtime,grade:grade,fatherId:fatherId},
            function(data) {
                var code = JSON.parse(data).code_;
                if (code == '0') {
                    $.messager.alert('系统提示', '添加成功!', 'info',function(){
                    	$("#addProjectDialog tr td input").val('');
                    	$("#projectNum").numberbox("setValue",'');
                    	$("#depname").combobox("setValue",'');
                    	$("#addProjectDialog").dialog("close");
                    	$('#projectList').treegrid('options').url = windowPath + 'project_findByDepId.action?id=' + depId;
                    	$('#projectList').treegrid('reload');
                    });
                } else {
                    $.messager.alert('系统提示', '添加失败!', 'error');
                }
            });
    });
    //删除
    $('#delProject').click(function() {
        var node = $('#projectList').treegrid('getSelections');
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
        $('#addProjectDialog').panel('open').panel('refresh');
    });
    $('#close').click(function() {
        $('#addProjectDialog').dialog('close');
    });
    $('#shut').click(function() {
        $('#editProjectDialog').dialog('close');
    });
    //修改项目
    $('#editProject').click(function() {
        var row = $('#projectList').treegrid('getSelected');
        if (row == null) {
            $.messager.alert('系统提示', '请选择你要修改的项目!', 'warning');
            return;
        }
        var rid =row.Rid;
        var val = row.name;
        var grades =row.grade;
        var standertime =row.standardHour;
        $('#projectnum').numberbox('setValue', grades);
        $("#standardHour").val(standertime);
        $('#projectName').val(val);
        $('#Service').combobox({
        	url: windowPath+'project_findById.action?id='+rid+'&fatherid=0',
        	valueField: 'id',
    		textField: 'text',
    		filter: filterCombo
        });
        var fatherId= row.fatherId;
        if(fatherId==0){
        	$('#Service').combobox({ disabled: true} );
        }
        $('#editProjectDialog').dialog('open');
    });
    $("#keep").click(function() {
        var row = $('#projectList').treegrid('getSelected');
        var id = row.id;
        var fId =row.fatherId;
        var projectName = $('#projectName').val();
        var standardHour = $('#standardHour').val();
        var grade =  $('#projectnum').val();
        var fatherId =$('#Service').combobox('getValue');
        var url = windowPath + 'project_update.action';
        $.post(url, { id: id, projectName: projectName, standardHour: standardHour,grade:grade,fatherId:fatherId,fId:fId },
            function(data) {
                var code = JSON.parse(data).code_;
                if (code == '0') {
                    $.messager.alert('系统提示', '修改成功!', 'info',function(){
                  /*  	$("#editProjectDialog tr td input").val('');*/
                    	$("#editProjectDialog").dialog('close');
                        $('#projectList').treegrid('options').url = windowPath + 'project_findByDepId.action?id=' + row.Rid;
                        $('#projectList').treegrid('reload');
                    });
                } else {
                    $.messager.alert('系统提示', '修改失败!', 'error');
                }
            });
    })
});
function reg() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    var rows = $("#projectList").treegrid('getSelected');
    if(rows.startFlag == 1){
    	 $.messager.alert('系统提示', '该服务已经登记过了，无法登记!', 'warning');
    	 return;
    }
    var id = rows.id
    var url2 = windowPath + 'project_editdengji.action';
    $.post(url2, { id: id },
        function(data) {
            var code = JSON.parse(data).code_;
            if (code == '0') {
                $.messager.alert('系统提示', '登记成功!', 'info',function(){
                	location.reload();
                });
            } else {
                $.messager.alert('系统提示', '登记失败!', 'error');
            }
        });
}
function add() {
	var host = window.location.host;
    var windowPath = "http://" + host + "/";
    var rows = $("#projectList").treegrid('getSelected');
    if(rows.fatherId != 0){
    	   $.messager.alert('系统提示', '该服务无法添加下级服务!', 'warning');
    	   return;
    }
    $('body').append('<div id="serviceDialog"></div>');
    $('#serviceDialog').dialog({
        title: '添加下级服务',
        width: 600,
        height: 400,
        closed: false,
        cache: false,
        href: windowPath + 'project_addProject.action',
        modal: true,
        onClose: function() {
            $('#serviceDialog').remove();
        }
    });
}
