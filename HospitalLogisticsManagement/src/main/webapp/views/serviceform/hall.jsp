<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>在线报修服务管理平台</title>
    <style>
        *{
            margin: 0;
            padding: 0;
        }
        body{
        	text-align:center;
        }
        table th{
        font-weight:normal;
        }
        #table{
        border:1px solid #c1c1c1;
        margin:50px auto;
        border-collapse:collapse; 
        }
        #table td,#table th{
        border:1px solid #c1c1c1;
        text-align:center;
        padding:10px;
        }
      #tbo .state{
        	padding:0;
        	height:100%;
        	width:100px;;
        }
       #tbo  .outrepair,#tbo .repair{
        color:red;
        }
        #tbo tr:nth-child(odd){
        	background-color:#eee;
        }
    </style>
    <!-- dataTables -->
    <script type="text/javascript" src="../../Js/jquery.min.js"></script>
    <script type="text/javascript" src="../../Js/json2.js"></script>
    <script type="text/javascript" src="../../Js/underscore.js"></script>
</head>
<body>
<div id="outTable">
	<table id="table" cellspacing="0" cellpadding="0">
		<thead>
		<tr>
			<th class="protime">申请时间</th>
			<th class="prodep">申请科室</th>
			<th class="prouser">申请人</th>
			<th class="project">项目内容</th>
			<th class="repair">返修</th>
			<th class="restime">响应次数</th>
			<th class="outrepair">外修</th>
			<th class="respontime">响应时间</th>
			<th class="resdep">响应科室</th>
			<th class="resuser">响应人员</th>
			<th class="usedtime">已用工时</th>
			<th class="endtime">完成时间</th>
			<th class="state">状态</th>
		</tr>
		</thead>
		<tbody id="tbo">
		</tbody>
	</table>
</div>

 <script type="text/html" id="List">
                        ##for(var i = 0;i <list.length;i++){ ##
                         ##var item=list[i];## 
						<tr>
                        <td class="protime">{{item.proposerTime}}</td>
						<td class="prodep">{{item.depName}}</td>
						<td class="prouser">{{item.sendUserName}}</td>
						<td class="project">{{item.projectName}}</td>
						<td class="repair">{{item.repairFlag}}</td>
						<td class="restime">{{item.responseTimes}}</td>
						<td class="outrepair">{{item.outSourcIngFlag}}</td>
						<td class="respontime">{{item.responseTime}}</td>
						<td class="resdep">{{item.reponseDepName}}</td>
						<td class="resuser">{{item.responseStaff}}</td>
						<td class="usedtime">{{item.useTime}}</td>
						<td class="endtime">{{item.endTime}}</td>
						<td class="state" name="state">{{item.state}}</td>
						</tr>
                            ##}##
                    </script>
<script type="text/javascript">
$(function(){
	var host = window.location.host;
    var windowPath = "http://" + host + "/";
		function dealTemplate(child, parent, data) {
            var _html = $(child).html();
            var _htmlfn = _.template(_html);
            var _html2 = _htmlfn({
                list: data
            });
            $(parent).html(_html2);
        }
        function getData(){
        	var that = this;
          	$.post(windowPath + "proposer_findAll.action",{page:1,rows:10},function(data){
			var json = JSON.parse(data);
			var d = json.rows;
			dealTemplate('#List', '#tbo', d);
			});
        }
        getData();
        setInterval(getData, 5000);
});

</script>
</body>
<html>