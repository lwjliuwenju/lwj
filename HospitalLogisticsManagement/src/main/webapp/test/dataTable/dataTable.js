var host;
var windowPath;
var $table;
var url;
var $tds;
var dataAuto;
var dataTotal;
var qjData;

$.fn.comSelect = function (linkUrl,key,val) {
    var $select = $('#' + $(this).selector);
    $select.children('option').remove();
    if(typeof linkUrl == 'string'){
        $.post(linkUrl, function (data) {
            data = JSON.parse(data);
            var content = "<option value='-1'></option>";
            for(var i = 0; i < data.length ; i++){
                content += "<option value='" + data[i][key] + "'>" + data[i][val] + "</option>";
            }
            $select.append(content);
        });
    }else{
        var content = "<option value='-1'></option>";
        for(var i = 0; i < linkUrl.length ; i++){
            content += "<option value='" + linkUrl[i][key] + "'>" + linkUrl[i][val] + "</option>";
        }
        $select.append(content);
    }

}

function updatefy(){
    $("#page").Page({
        totalPages: dataTotal / 15,//分页总数
        liNums: 7,//分页的数字按钮数(建议取奇数)
        activeClass: 'activP', //active 类样式定义
        callBack : function(page){
            updateDate(page);
        }
    });
}

//加载表格数据
function loaddata(data) {
    data = jgData(JSON.parse(data));
    qjData = data;
    $("#data-table tr td").parent().remove();
    dataTotal = data.total;
    var rows = data.rows;
    var tr = '';
    for (var int = 0; int < rows.length; int++) {
        tr += "<tr class='row'><td><input class='xz' col-id='" + int + "' type='checkbox' /></td>";
        jQuery.each($tds, function (i, td) {
            var tdCol = undefined;
            var col = td.getAttribute("data-col");
            var colhidden = td.getAttribute("data-hidden");
            var hiddenstyle = "";
            if (colhidden == "true") {
                hiddenstyle = "style='display:none;'";
            }
            var val = rows[int][col];
            var title = val;
            if (typeof(val) != "undefined") {
                if(title.toString().substring(0,1) == "<"){
                    var reg = />(.+)</;
                    var items = reg.exec(title);
                    title = items[0];
                    title = title.substring(1,title.length-1);
                }

                tdCol = "<td " + hiddenstyle + " title=" + title + ">" + val + "</td>";
            } else {
                tdCol = "<td " + hiddenstyle + " ></td>";
            }
            tr += tdCol;
        });
        tr += "</tr>";
        $table.append(tr);
        tr = '';
    }
}
//弹窗方法
function tc(url, width, height, title) {
    $("#tc-title").text(title);
    $("#tc").css("width", width);
    $("#tc").css("height", height);
    $("#tc").css("margin-left", width / 2 / -1);
    $("#tc").css("margin-top", height / 2 / -1);
    $("#tc-content").prop("src", url);
    $("#tc").css("display", "block");
}



$(function() {
    host = window.location.host;
    windowPath = "http://" + host + "/";
    $table = $("#data-table");
    url = windowPath + $table.attr("data-url");
    $tds = $("#data-table tr th:gt(0)");

    dataAuto = $table.attr("data-auto");
    $.post(url, function (data) {
        if(dataAuto == 'true'){
            loaddata(data);
            updatefy();
        }
    });

    //加载下拉框
    var $select = $('.selectInput');
    for(var i = 0; i < $select.length ; i++){
        var id = '#' + $select[i].id;
        $(id).selectseach("../test/img/");
    }

    //加载日期选择器
    $('.data-rqxz').dcalendarpicker();
    $table.on("click", ".row", function () {
        var $row = $(this).find(".xz");
        if ($row.prop("checked")) {
            $row.removeAttr("checked");
            $table.find($(this)).find("td").removeClass("xzzt");
        } else {
            $row.prop("checked", "true");
            $table.find($(this)).find("td").addClass("xzzt");
            $row.trigger("create");
        }
        initButton();
    });

    $("#qx input").click(function () {
        var $xzs = $(".xz");
        if (!$(this).is(':checked')) {
            $xzs.removeAttr("checked");
            $table.find("td").removeClass("xzzt");
        } else {
            $xzs.prop("checked", "true");
            $table.find("td").addClass("xzzt");
            $xzs.trigger("create");
        }
        initButton();
    });
    $("#tc-close").click(function () {
        $("#tc").css("display", "none");
    });

    //function jgData(data){
    //    return data;
    //}

    //弹窗使用方式
    //tc("../login.jsp",600,200,"测试");

    //iframe  使用父iframe
    //parent.document.getElementById("myH1").innerHTML

});
/**
 * 需要处理数据写这个方法
 * @param data
 * @returns {*}
 */
function jgData(data){
    return data;
}

//按钮初始化
function initButton() {}
//获取选中列
function getSelectCol() {
    var $cols = $table.find(".xz:checked");
    var arr = new Array();
    for(var i = 0; i < $cols.length ; i++){
        arr[i] = qjData[$cols[i].attr['col-id']];
    }
    return $cols;
}