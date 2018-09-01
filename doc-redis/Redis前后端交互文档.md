## Redis前后端交互标准

**服务器端口说明**

 + 主缓存服务器采用端口：65532
+ 备份缓存服务器采用端口：6000

### 前端请求接口

 + 请求路径-url：/redis/data/cached
 + 缓存键参数名：key-缓存键的参数名称;
 + 缓存值参数名：value-缓存键对应的参数值;

***前端请求例子:***

```js
///JQuery ajax异步请求
$('#send').click(function(){
         $.ajax({
             type: "post",
             url: "/redis/data/cached",
             data: {
                 	command:$("#command").val(),
             		key   : $("#key").val(), 
             		value : $("#value").val()
             },
             dataType: "json",
             success: function(data){
                 ////处理返回的JSON数据
             }             
         });
});
```

### 后端返回数据格式

```json
////返回的数据格式
{"result":"value1 value2 value3 value4","success":true}
```

## 服务器错误信息说明

```txt
+ 1002 ：发送的命令不合法
+ OK : 服务器命令正确执行 
```

