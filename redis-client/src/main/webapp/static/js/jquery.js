/*
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-31 上午10:00
 *   @author lyg
 *   @version 1.0
 *   @Description
 */

function load() {
    $("#load").height("document.documentElement.scrollHeight");
    $("#load").width("document.documentElement.scrollWidth");
    var i = document.getElementById("load");
    i.style.display = "block";
}

function hideLoad() {
    var o = document.getElementById("load");
    o.style.display = "none";
}

function showView() {
    $("#bkView").height("document.documentElement.scrollHeight");
    $("#bkView").width("document.documentElement.scrollWidth");
    var view = document.getElementById("bkView");
    view.style.display = "block";
}

function hideView() {
    var viewOut = document.getElementById("bkView");
    viewOut.style.display = "none";
}


function getData() {
    var a = document.getElementById("wbk").value;
    var arr;
    var back = [];
    arr = a.split(" ");
    //验证命令是否正确
    if (arr.length < 1) {
        alert("文本框不能为空");
        return;
    }
    else {
        back[0] = arr[0].toLowerCase();

        if (back[0] == "get" || back[0] == "del") {
            if (arr.length != 2) {
                alert("必须输入command和key！");
                return;
            }
        }

        else if (back[0] == "set" || back[0] == "expire") {
            if (arr.length != 3) {
                alert("必须输入command，key和单个value！");
                return;
            }
        }

        else if (back[0] == "lset" || back[0] == "hset") {
            if (arr.length < 3) {
                alert("必须输入command，key，一个或多个value");
                return;
            }
        }
        else {
            alert("输入命令错误！");
            return;
        }
    }
    //参数名
    back[1] = arr[1];
    back[2] = "";
    //参数值
    var i;
    for (i = 2; i < arr.length - 1; i++) {
        back[2] = back[2] + arr[i] + " ";
    }
    back[2] = back[2] + arr[arr.length - 1];

    $.ajax({
        url: "/redis/data/cached" + "?time=" + new Date().getTime(),
        //当点击媒体类型时发起请求，媒体类型（文件类型）发送给后台
        data: {
            command: back[0],
            key: back[1],
            value: back[2]
        },
        type: "post",
        dataType: "json",
        beforeSend: function () {
            load();

        },
        success: function (data) {
            res = data;
            setTimeout("result()",1000);
        },
        error: function () {
            alert("Data Error!");
        }
    });
}
var res;
function  result() {
    // console.log(data);
    hideLoad();
    showView();
    // $.each(data, function (key, value) {
    //     item = "<p>" + value + "</p>";//ajson对象的什么数据
    //     $("#setP").append(item);
    // });
    $("#setP").html("<p>" + res.result + "</p>");
}

