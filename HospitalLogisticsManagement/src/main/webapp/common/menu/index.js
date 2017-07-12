$(function() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    $("#menutree").treegrid({
        title: '菜单管理',
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
        //toolbar:'#toolbar',
        url: windowPath + 'menu_findall.action',
        columns: [
            [
                //{field:'ck',checkbox:true},
                { field: 'id', title: 'id', width: 200, align: 'left', hidden: true },
                { field: 'name', title: '菜单名称', width: '100%', align: 'left', editor: 'text' },
            ]
        ],
        onContextMenu: function(e, row) {
            e.preventDefault();
            $(this).treegrid('select', row.ID_);
            $('#mm').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }
    });
});

function add() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    $('body').append('<div id="addmenuDialog"></div>');
    $('#addmenuDialog').dialog({
        title: '添加菜单',
        width: 350,
        height: 250,
        closed: false,
        cache: false,
        href: windowPath + 'menu_addmenufrom.action',
        modal: true,
        onClose: function() {
            $('#addmenuDialog').remove();
        }
    });
}

function edit() {
    var host = window.location.host;
    var windowPath = "http://" + host + "/";
    var rows = $("#menutree").treegrid('getSelected');
    if (rows == null) {
        $.messager.alert('系统提示', '请选择你要修改的菜单!', 'warning');
        return;
    }
    $('body').append('<div id="menuDialog"></div>');
    $('#menuDialog').dialog({
        title: '修改菜单',
        width: 350,
        height: 250,
        closed: false,
        cache: false,
        href: windowPath + 'menu_menufrom.action',
        modal: true,
        onClose: function() {
            $('#menuDialog').remove();
        }
    });
};
