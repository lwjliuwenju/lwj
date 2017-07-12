	$(function() {
	        var username = sessionStorage.getItem("username");
	        var proposerName = $("#proposerName").val(username);
			var host = window.location.host;
			var windowPath = "http://"+host + "/";
            $('#responseDeptName').combobox({
            	url: windowPath+'dep_getdepartment.action', 
                valueField: 'id',
                textField: 'text'
            });
          $("#save_close").click(function() {
                var mark = $("#mark").val();
                var responseDeptid = $("#responseDeptName").combobox('getValue');
                var url2 = windowPath+'proposer_saveProposer.action';
                $.post(url2, {
                        mark: mark,
                        responseDeptid: responseDeptid
                    },
                    function(data) {
                        var code = JSON.parse(data).code_;
                        if (code == '0') {
                            location.reload();
                            alert('申请成功');
                        } else {
                            alert('申请失败,请重试!');
                        }
                    });
            }); 
        });