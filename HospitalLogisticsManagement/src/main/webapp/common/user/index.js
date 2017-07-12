$(function() {
	var host = window.location.host;
	var windowPath = "http://"+host + "/";
    var index = {
        init: function() {
            this.events();
            this.dealAddUser();
            this.dealEditUser();
            this.dealImpowerRole();
            this.dealUserList();
            this.dealDeleteUser();
            this.dealRestPass();
            this.dealSearch();
        },
        events: function() {
            $('#shutdown').click(function() {
                $("#editUserDialog").dialog('close');
            });
            $('#close').click(function() {
                $("#insertUserDialog").dialog('close');
            });
            $('#cancelimpower').click(function() {
                $("#dd").dialog('close');
            });
        },
        dealSearch:function(){
        	$("#query").click(function(){
        		var jobNumber = $("#account").val();
        		var userName = $("#user_name").val();
        		var phone = $("#phone").val();
        		var params = {account:account,userNameuserName:userName,phone:phone};
        		$('#userlist').datagrid('options').url = windowPath
        		+ 'user_findall.action?jobNumber=' + jobNumber
        		+ '&userName=' + userName + '&phone=' + phone;
        		$('#userlist').datagrid('reload');
        	});
        },
        dealAddUser: function() {
            var that = this;
            //添加用户
            function addUser() {
                var username = $.trim($('.username').val());
                var password = $.trim($('.password').val());
                var phone = $.trim($('.phone').val());
                var sex = '';
                var email = $.trim($('.email').val());
                var jobNumber = $.trim($('.jobNumber').val());
                var flag = null;
                if ($('.boy').is(':checked')) {
                    sex = '男';
                    flag = true;
                };
                if ($('.girl').is(':checked')) {
                    sex = '女';
                    flag = false;
                };
                if(password.length == 0 || username.length == 0){
                	 $.messager.alert('添加用户','填写信息不完整,请重试!','info');
                	 return;
                }
                $.post(
                    windowPath + 'user_add.action', { password: password, userName: username, number: phone, sex: flag, email: email, jobNumber: jobNumber },
                    function(data) {
                        var code = JSON.parse(data).code_;
                        if (code == '0') {
                            $.messager.alert('添加用户','添加成功!','info',function(){
                            	location.reload();
                            });
                           
                        } else {
                        	 $.messager.alert('添加用户','添加失败,请重试!','info');
                        }
                    })
            };
            $('#save_close').click(function() {
                addUser();
                $("#insertUserDialog").dialog('close');
            });
            $('#save_continue').click(function() {
                addUser();
            });
        },
        dealRestPass: function(){
        	$('#restPass').click(function() {
                var node = $("#userlist").datagrid('getSelected');
                if (node == null) {
                	$.messager.alert('密码重置','请选择你要重置的用户!','error');
                } else {
                	$.messager.confirm('重置用户', '确定重置该用户?', function(r){
                    	if (r){
                    		var id = node.id;
                    		$.post(
                                    windowPath + 'user_update.action', { id: id,password: '123456' },
                                    function(data) {
                                        var code = JSON.parse(data).code_;
                                        if (code == '0') {
                                            $.messager.alert('密码重置','重置成功!','info',function(){
                                           	 location.reload();
                                            });
                                        } else {
                                        	  $.messager.alert('密码重置','重置失败!','error');
                                        }
                                    })
                    	}
                	})
                }
        	});
        },
        dealEditUser: function() {
            var that = this;
            //编辑用户
            function editUser() {
                var node = $('#userlist').datagrid('getSelected');
                var username = $('.username1').val();
                var phone = $('.phone1').val();
                var sex = '';
                var email = $('.email1').val();
                var jobNumber = $.trim($('.jobNumber1').val());
                var id = node.id;
                var flag = null;
                if ($('.boy').is(':checked')) {
                    sex = '男';
                    flag = true;
                };
                if ($('.girl').is(':checked')) {
                    sex = '女';
                    flag = false;
                };
                $.post(
                    windowPath + 'user_update.action', { id: id,userName: username, number: phone, sex: flag, email: email, jobNumber: jobNumber },
                    function(data) {
                        var code = JSON.parse(data).code_;
                        if (code == '0') {
                            $.messager.alert('更新用户','更新成功!','info');
                            location.reload();
                        } else {
                        	  $.messager.alert('更新用户','更新失败!','error');
                        }
                    })
            };

            $('#editUser').click(function() {
                var node = $("#userlist").datagrid('getSelected');
                if (node == null) {
                	$.messager.alert('修改用户','请选择你要修改的用户!','error');
                } else {
                    $('#editUserDialog').dialog('open');
                    var username = $('.username1').val(node.userName);
                    var phone = $('.phone1').val(node.number);
                    var sex = '';
                    var email = $('.email1').val(node.email);
                    var jobNumber = $('.jobNumber1').val(node.jobNumber);
                    var flag = null;
                    if (node.sex == true) {
                        $('.boy1').attr('checked', true);
                    } else {
                        $('.girl1').attr('checked', true);
                    }
                }
            });
            $('#keep_close').click(function() {
                editUser();
                $("#editUserDialog").dialog('close');
            });
            $('#keep_continue').click(function() {
                editUser();
            });
        
        },
        dealDeleteUser: function() {
            var that = this;
            /*删除用户*/
            $('#deteleUser').click(function() {
                var node = $('#userlist').datagrid('getSelections');
                var userId = [];
                for (var i = 0; i < node.length; i++) {
                	userId.push(node[i].id);
				}
                $.messager.confirm('删除用户', '确定删除该用户?', function(r){
                	if (r){
                		 $.post(windowPath + 'user_delete.action', { id: userId },function(data){
                			 var code = JSON.parse(data).code_;
                             if (code == '0') {
                                 $.messager.alert('删除用户','删除成功!','info',function(){
                                	 location.reload();
                                 });
                             } else {
                             	$.messager.alert('删除用户','删除失败!','error');
                             }
                		 });
                	}
                });
            });
        },
        dealImpowerRole: function() {
            var that = this;
            /*角色授权*/
            $('#impowerRole').click(function() {
                var row = $('#userlist').datagrid('getSelected');
                $('#dg').datagrid({onLoadSuccess : function(data){
                	var userid=row.id;
                    var url4=windowPath + 'role_getrole.action';
                    $.post(url4,{userid:userid},function(data2){
                    	$.each(JSON.parse(data2), function(idx, obj) {
                    		var r= $('#dg').datagrid('getRows');
                    		for (var i = 0; i < r.length; i++) {
                    	    	 var rId = r[i].ID_;
                    	    	 if(rId==obj.ID_){
                    	    		  var index = $("#dg").datagrid("getRowIndex",r[i]);
                    	    		  $("#dg").datagrid("selectRow",index);
                    	    	 }
    						}
                    	});
                    });
            		},
                    url: windowPath + 'role_findAll.action',
                    title: '角色授权',
                    width: 'auto',
                    height: 'auto',
                    fitColumns: true,
                    striped:true,//隔行变色
                    align:"left",
                    //toolbar: '#impowerTools',
                    columns: [
                        [
                            { field: 'ck', checkbox: true },
                            { field: 'ID_', title: '角色编号', width: 60, hidden: true },
                            { field: 'NAME_', title: '角色名', width: 100, align: 'center' }
                        ]
                    ]
                });
                if (row) {
                    $("#dd").dialog('open');
                }
                $('#selectall').click(function() {
                    $('#dg').datagrid('selectAll');
                });
                $('#cancelselect').click(function() {
                    var s_rows = $.map($('#dg').datagrid('getSelections'),
                        function(n) {
                            return $('#dg').datagrid('getRowIndex', n);
                        });
                    $('#dg').datagrid('selectAll');
                    $.each(s_rows, function(i, n) {
                        $('#dg').datagrid('unselectRow', n);
                    });
                });
                $('#saveimpower').click(function() {
                	 var row = $('#userlist').datagrid('getSelected');
                    var userId = row.id;
                    var roleIdList = [];
                    var arr = $('#dg').datagrid('getSelections');
                    for (var i = 0; i < arr.length; i++) {
                    	roleIdList.push(arr[i].ID_);
                    }
                    $.post(windowPath +'user_setRoles.action', { userId: userId, roleIds: roleIdList }, 
                        	function(data) {
                            var code = JSON.parse(data).code_;
                            if (code == '0') {
                                $.messager.alert('授权','授权成功!','info',function(){
                                	 location.reload();
                                });
                            } 
                        });
                });

            });
        },
        dealUserList: function() {
            var that = this;
            $('#userlist').datagrid({
                title: '用户管理',
                iconCls: 'icon-wrench',
                checkbox: true,
                width: '100%',
                height: 500,
                fitColumns: true,
                rownumbers: false,
                animate: true,
                collapsible: false,
                collapsed: false,
                striped:true,//隔行变色
                singleSelect:false,
                pageNum: 1, //每次重新查询设置为第一页，这里重要，测试一下
                pageList: [15, 30, 50], //可以设置每页记录条数的列表 
                pageSize:15,
                pagination: true, // 分页控件
                toolbar: '#userTools',
                url: windowPath + 'user_findAll.action',
                columns: [
                    [
                        { field: 'ck', checkbox: true },
                        { field: 'id', title: '用户编号', width: 60, hidden: true },
                        { field: 'userName', title: '用户名', width: 100 },
                        { field: 'jobNumber', title: '工号', width: 100 }, {
                            field: 'sex',
                            title: '性别',
                            width: 100,
                            formatter: function(value) {
                                if (value == true) {
                                    {
                                        return "男";
                                    }
                                } else if (value == false) {
                                    return "女";
                                }
                            }
                        },
                        { field: 'number', title: '手机号', width: 100 }
                    ]
                ]
            });
        },
        dealUserListPagetion: function() {
            var that = this;
            //设置分页控件 
            $('#userlist').datagrid('getPager').pagination({
                beforePageText: '第', //页数文本框前显示的汉字 
                afterPageText: '页  ， 共 {pages} 页',
                displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
                onSelectPage: function(pageNumber, pageSize) {
                    $(this).pagination('loading');
                    $('#userlist').datagrid('options').url = windowPath + 'user_findAll.action?page=' + pageNumber + '&rows=' + pageSize;
                    $('#userlist').datagrid('reload');
                    $(this).pagination('loaded');
                }
            });
        }
    }
    index.init();
});
