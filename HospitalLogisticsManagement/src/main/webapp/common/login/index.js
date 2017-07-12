 $(function() {
    var host = window.location.host;
	var windowPath = "http://"+host + "/";
    var login =  {
        init: function() {
            this.userLogin();
        },
        userLogin: function() {
            $('.login').click(function(e) {
                e.preventDefault();
                var username = $('.number > input').val();
                var password = $('.password > input').val();
                if (username == "" || password == "") { //判断两个均不为空（其他判断规则在其输入时已经判断） 
                    alert("用户名密码均不能为空！")
                    return false;
                } else {
                    $.ajax({
                        url: windowPath+"login_login.action",
                        data: {
                            username: username,
                            password: password
                        },
                        type: 'post',
                        success: function(res) {
                         
                            var a = JSON.parse(res)
                            var deps = a.data.deps;
                            var userid = a.data.user.id
                            var code = JSON.parse(res).code_;
                            var _menus = a.data.menus;
                            if (code == '0') {
                                sessionStorage.setItem('leftMenu', JSON.stringify(_menus));
                                sessionStorage.setItem('username', username);
                                sessionStorage.setItem("userid", userid);
                                sessionStorage.setItem('deps', JSON.stringify(deps));
                                location.href = 'index.jsp';
                            }else{
                            	alert(a.message_);
                            }
                        }
                    })
                }
            });
            $(document).keyup(function(event) {
                if (event.keyCode == 13) {
                    $(".login").trigger("click");
                }
            });
        }
    }
    login.init();
})