	$(function() {
  	 	var host = window.location.host;
	    var windowPath = "http://" + host + "/";
  		var test = {
  				init:function(){
  					this.getData();
  					this.changeBgColor();
  					this.addService();
  					this.call();
  				},
  				dealTemplate:function(child, parent, data){
  				  var _html = $(child).html();
  				  var _htmlfn = _.template(_html);
  				  var _html2 = _htmlfn({
  	                list: data
  				  	});
  	            $(parent).html(_html2);
  				},
  				appendTemplate:function(child, parent, data){
    				  var _html = $(child).html();
    				  var _htmlfn = _.template(_html);
    				  var _html2 = _htmlfn({
    	                list: data
    				  	});
    	            $(parent).append(_html2);
    				},
  				getData:function(){
  					var that = this;
  		          	$.post(windowPath + "proposer_findAll.action",function(data){
  					var json = JSON.parse(data);
  					var d = json.rows;
  					that.dealTemplate('#List', '#tbody', d);
  					});
  				},
  				changeBgColor:function(){
  					var that = this;
  					/*改变背景颜色*/
  				    $("#tbody").on("click",".row",function(){
//  				    	var allRow = $("#tbody").find(".selectRow:checked");
//  				    	if(allRow.length > 0 ){
//  				    		$("#seeDetail").removeAttr("disabled");
//  				    	}else{
//  				    		$("#seeDetail").attr("disabled","disabled");
//  				    	}
  				    	$(this).siblings().find("td").removeClass("xzzt");
  				    	$(this).siblings().find(".selectRow").removeAttr("checked");
  				    	var currentCheckBox = $(this).find(".selectRow");
  				    	if(currentCheckBox.prop("checked")){
  				    		currentCheckBox.removeAttr("checked");
  				    		$("#tbody").find($(this)).find("td").removeClass("xzzt");
  				    	}else{
  				    		currentCheckBox.prop("checked","true");
  				    		$("#tbody").find($(this)).find("td").addClass("xzzt");
  				    	}
  				    });
  				    /*全选*/
  				  $("#qx input").click(function(){
  			    	var $xzs = $(".selectRow");
  			    	if(!$(this).is(':checked')){
  				    	$xzs.removeAttr("checked");
  				    	$("#tbody").find("td").removeClass("xzzt");
  			    	}else{
  			    		$xzs.prop("checked","true");
  			    		$("#tbody").find("td").addClass("xzzt");
  			    	}
  			    });
  				},
  				getSelectRow:function(){
  					/*获取当前选中行*/
  					return $(".selectRow:checked").parent().parent();
  				},
  				center:function(id,width,height){
  					/*居中显示*/
  					$("#" + id).css("width",width + "px");
  					$("#" + id).css("height",height + "px");
  					$("#" + id).css("margin-left",width/2/-1 + "px");
  					$("#" + id).css("margin-top",height/2/-1 + "px");
  					$("#" + id).show();
  				},
  				addService:function(){
  					/*添加申请单*/
  					var that = this;
  					$('#add').click(function(){
//  						that.center('addServiceform','500','350');
  						$("#addServiceform").window('open').window('center');
  					});
  					$("#close_add").click(function(){
  						$('#addServiceform').hide();
  					});
  			    	$.post(windowPath + "dep_getdepartment.action",function(data){
  	  					var json = JSON.parse(data);
  	  					that.appendTemplate('#pro_dep', '#shenqingdepartment2', json);
  	  					that.appendTemplate('#pro_dep', '#responseDeptName', json);
  	  					});
  			    	$("#save_add").click(function(){
  			    		 var mark = $("#mark").val();
  			           var responseDeptid = $('#responseDeptName option:selected').attr('data-id');
  			           var sendDeptId = $("#shenqingdepartment2 option:selected").attr('data-id');
  			           var url2 = windowPath+'proposer_saveProposer.action';
  			           $.post(url2, {
  			                   mark: mark,
  			                   responseDeptid: responseDeptid,
  			                   sendDeptId: sendDeptId
  			               },
  			               function(data) {
  			                   var code = JSON.parse(data).code_;
  			                   if (code == '0') {
  			                   	alert("添加成功");
  			                   	location.reload();
  			                   } else {
  			                   	 alert( '添加失败,请重试');
  			                   }
  			               });
  			    	});
  				},
  				call:function(){
  					$("#call").click(function(){
  						$("#callLogistics").window('open').window('center');
  					});
  				},
  				seeDetail:function(){
  					$("#seeDetail").click(function(){
  					});
  				}
  		}
  		test.init();
  	});