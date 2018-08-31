## Redis文档

**服务器端口说明**

 + 主缓存服务器采用端口：65532
+ 备份缓存服务器采用端口：6000

## Redis前后端交互标准

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

	+ 1002 ：命令格式不正确；

### Redis缓存的功能

### 命令选项

+ set key vlue：键值对都为字符串
+ expire key  1000：设置key的过期时间（单位：秒），ever表示永不过期
+ lset key value1 value2 value3：键为字符串，值为list集合
+ hset key value：键为字符串，值为set集合
+ get key：返回key对应的值

### 通信实现

***服务器监听客户端请求，有连接请求新开一个线程处理客户端命令，服务器返回处理结果***

### 缓存淘汰

+ 惰性淘汰：键值对对象过期后在再次获取时进行移除；
+ 周期更新：周期性定时检查缓存对象是否过期，将过期对象进行移除；

### 持久化策略

+ save 60 6：采取每60秒内，缓存里有6个对象发生更改，持久化一次；

+ 持久化到数据库；

+ 持久化到文件；

  