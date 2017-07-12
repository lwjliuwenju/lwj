$(function() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    var id;
    $('#paramlist').datagrid({
        title: '参数管理',
        iconCls: 'icon-wrench',
        checkbox: true,
        width: '100%',
        height: 500,
        fitColumns: true,
        striped: true, //隔行变色
        rownumbers: true,
        animate: true,
        collapsible: false,
        collapsed: false,
        singleSelect: false,
        url: windowPath + 'param_findAll.action',
        columns: [
            [
                { field: 'id', title: '参数编号', width: 60, hidden: true },
                { field: 'parName', title: '参数名称', width: 100, align: 'center' },
                { field: 'parMark', title: '参数标记', width: 100, align: 'center' },
                { field: 'parVal', title: '参数值', width: 100, align: 'center' }
            ]
        ],
        onDblClickRow: function(rowIndex, rowData) {
            id = rowData.id;
            $('#paramDiv').dialog('open');

        }
    });
    $('#close').click(function() {
        $('#paramDiv').dialog('close');
    });
    $('#save').click(function() {
        var parVal = $('#editparam').val();
        $.post(windowPath + 'param_update.action', { id: id, parVal: parVal }, function(data) {
            var code = JSON.parse(data).code_;
            if (code == '0') {
                $.messager.alert('系统提示', '添加成功!', 'info',function(){
                	 location.reload();
                });
               

            } else {
                $.messager.alert('系统提示', '添加失败!', 'error');
            }
        });
    });
  
});
