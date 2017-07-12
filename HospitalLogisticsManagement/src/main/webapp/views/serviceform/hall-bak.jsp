<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>在线报修服务管理平台</title>
    <style>
        .nested{
            margin-left: 0px;
        }
        .cancelPadding{
            padding-left: 0px;
            padding-right: 0px;
            margin-bottom: 15px;
        }
        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button{
            -webkit-appearance: none !important;
            margin: 0;
        }
        input[type="number"]{-moz-appearance:textfield;}
    </style>
    <!-- dataTables -->
    <script type="text/javascript" src="../../Js/jquery.min.js"></script>
	<link rel="stylesheet" href="../../common-lwj/css/bootstrap-table.css">
	<link rel="stylesheet" href="../../common-lwj/css/examples.css">
	<link rel="stylesheet" href="../../common-lwj/bootstrap/css/bootstrap.css">
	<!--[if lte IE 7]>
	<link rel="stylesheet" href="../../common-lwj/css/ie.css">
	<![endif]-->
	<!--[if lt IE 9]>
	<script type="text/javascript" src="../../Js/html5.js"></script>
	<script type="text/javascript" src="../../Js/html5shiv.min.js"></script>
	<script type="text/javascript" src="../../Js/respond.min.js"></script>
	<script type="text/javascript" src='../../Js/json2.js'></script>
	<![endif]-->
	<script type="text/javascript" src="../../common-lwj/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="../../common-lwj/plugins/dataTables/bootstrap-table.js"></script>
	<script type="text/javascript" src="../../common-lwj/plugins/dataTables/ga.js"></script>
	<script type="text/javascript" src="../../common-lwj/plugins/dataTables/bootstrap-table-zh-CN.js"></script>
</head>
<body>
<div class="wrapper scroll-wrapper">
    <div class="content-wrapper nested">
            <div class="row">
                <div class="col-xs-12 cancelPadding">
                    <div class="box">
                        <div class="box-body">
                            <table id="table" 
                            	   data-url="/proposer_findAll.action"
                                   data-toggle="table"
                                   data-pagination="true"
                                   data-page-size="30"
                                   data-page-list="[30,50,70,All]"
                                   data-pagination-loop="false"
                                   data-side-pagination="server"
                                   data-response-handler="responseHandler"
                                   data-query-params="paginationParam"
                                   data-cache="false"
                                   data-toolbar="#toolbar"
                                   data-show-columns="true"
                                   data-show-refresh="true"
                                   data-page-number="1"
                                   data-content-type="application/x-www-form-urlencoded; charset=UTF-8"
                                  
                            >
                                <thead>
                                <tr>
                                   <!--  <th data-fidld="check" data-checkbox="true" data-align="center" data-valign="middle"></th> -->
                                    <th data-field="proposerTime" data-align="center" data-formatter="operateFormatter" data-valign="middle">申请时间</th>
                                    <th data-field="depName"  data-align="center" data-valign="middle">申请科室</th>
                                    <th data-field="sendUserName" data-width="30px" data-align="center" data-valign="middle">申请人</th>
                                    <th data-field="projectName" data-align="center" data-valign="middle">项目内容</th>
                                    <th data-field="repairFlag" data-width="30px"  data-align="center" data-valign="middle" data-formatter="fanxiu" >返修</th>
                                    <th data-field="responseTimes"  data-align="center" data-width="30px" data-valign="middle">响应次数</th>
                                    <th data-field="outSourcIngFlag" data-align="center" data-width="30px" data-valign="middle" data-formatter="waixiu" >外修</th>
                                    <th data-field="responseTime" data-align="center" data-formatter="operateFormatter" data-valign="middle">响应时间</th>
                                    <th data-field="reponseDepName" data-align="center" data-valign="middle">响应科室</th>
                                    <th data-field="responseStaff" data-width="20px" data-align="center" data-valign="middle">响应人员</th>
                                    <th data-field="useTime" data-align="center" data-valign="middle">已用工时</th>
                                    <th data-field="endTime" data-formatter="operateFormatter" data-align="center" data-valign="middle">完成时间</th>
                                    <th data-field="state" data-align="center" data-valign="middle" data-formatter="zhuangtai" >状态</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
    </div>
</div>
<script>
    $.extend($.fn.bootstrapTable.defaults,$.fn.bootstrapTable.locales['zh-CN']);

//   格式化服务器返回的数据
    function responseHandler(data) {
        return {
            'total': data.total,
            'rows': data.rows
        }
    };
    /* 申请响应时间 */
    function operateFormatter(value, row, index) {
    if(value == null)
    	return ' ';
    var time = value['time'];
     return [
            new Date(time).toLocaleString() 
        ].join(''); 
    };
    /* 是否外修 */
     function waixiu(value, row, index) {
           if(value == 0 || value == null){
           return " "
       }
         if(value == 1){
            return '<span style="color:red;">外</span>';
       }
    };
     /* 是否反修 */
     function fanxiu(value, row, index) {
           if(value == 0 || value == null){
           return " "
       }
         if(value == 1){
            return '<span style="color:red;">反</span>';
       }
    };
    /* 对应状态 */
     function zhuangtai(value, row, index) {
           if(value == 1){
           return '<span style="background-color:red;display:inline-block;width:100%;padding:0px;color:#fff;">异常</span>';
       }
         if(value == 2 ){
           return '<span style="background-color:green;display:inline-block;width:100%;padding:0px;color:#fff;">待响应</span>';
       }
         if(value == 3 ){
           return '<span style="background-color:yellow;display:inline-block;width:100%;padding:0px;color:#fff;">响应中</span>';
       }
         if(value == 4 ){
           return '<span style="background-color:#555;display:inline-block;width:100%;padding:0px;color:#fff;">已完成</span>';
       }
         if(value == 5 ){
           return '<span style="background-color:black;display:inline-block;width:100%;padding:0px;color:#fff;">已终止</span>';
       }
         if(value == 6 ){
           return '<span style="background-color:#000;display:inline-block;width:100%;padding:0px;color:#fff;">已评价</span>';
       }
    };
    /*  分页*/
      function paginationParam(params) {
        var currPage = Math.floor((params.offset + params.limit) / params.limit);
        return {
            'page':currPage,
            'rows':params.limit
        }
    };
    /*  1分钟刷新一次页面*/
    function aaa(){
    	$('#table').bootstrapTable('refresh',{
    	url:"/proposer_findAll.action",silent: true
    	})
    }
	setInterval(aaa, 5000); 
</script>
</body>
<html>