package cn.missbe.redis.server.dao;

import cn.missbe.redis.server.App;
import cn.missbe.redis.server.bean.RedisBean;
import cn.missbe.redis.server.map.IRedisMap;
import cn.missbe.redis.server.map.KeyValueNode;
import cn.missbe.redis.server.map.RedisMapImpl;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-31 下午5:28
 *   @author lyg
 *   @version 1.0
 *   @Description 文件持久化对象，负责持久化数据到文件和从文件中读取数据
 **/

public class FileDaoImpl  implements IRedisDataDao,IRedisDateRead{
    private volatile File        redisFile     = createFile(App.SAVE_FILENAME);
    private static   FileDaoImpl FILE_DAO_IMPL = new FileDaoImpl();
    private Map<String, KeyValueNode>       maps = new HashMap<>();
    private Map<String, HashSet<KeyValueNode>>  setMaps = new HashMap<>();
    private Map<String, ArrayList<KeyValueNode>> listMaps = new HashMap<>();

    private FileDaoImpl(){}

    /**
     * 获取类对象
     * @return 唯一对象
     */
    @Contract(pure = true)
    public static FileDaoImpl getInstance(){
        return FILE_DAO_IMPL;
    }

    private boolean isExist(){
        return redisFile != null && redisFile.exists();
    }

    @NotNull
    private File createFile(String fileName){
        String path = Objects.requireNonNull(FileDaoImpl.class.getClassLoader().getResource(App.REDIS_CONFIG_NAME)).getPath();
        path = path.substring(0,path.lastIndexOf("/")+1);
        path += App.PORT + "_" + fileName;
        return new File(path);
    }

    @Override
    public void clear() {
        if(isExist()){
            FileUtils.deleteQuietly(redisFile);
        }//end if
    }

    @Override
    public void save() {
        if(isExist()){
            redisFile = createFile(App.SAVE_FILENAME);
        }

        List<RedisBean> list = RedisMapImpl.RedisMapHolder.getInstance().allMaps2RedisBean();
        try {
            if(!redisFile.canWrite()){
                Thread.sleep(1000 * 3);
            }
            FileUtils.write(redisFile, JSON.toJSONString(list), Charset.forName("UTF-8"));
        } catch (IOException e) {
            PrintUtil.print("写入文件失败，文件IO异常." + e.getMessage(), SystemLog.Level.error);
        } catch (InterruptedException e) {
            PrintUtil.print("等待文件写入睡眠打断异常.." + e.getCause(), SystemLog.Level.error);
        }
        PrintUtil.print("持久化数据到文件结束.", SystemLog.Level.info);
    }

    @Override
    public void save(List<RedisBean> beans) {
        if(isExist()){
            redisFile = createFile(App.SAVE_FILENAME);
        }
        try {
            if(!redisFile.canWrite()){
                Thread.sleep(1000 * 3);
            }
            FileUtils.write(redisFile, JSON.toJSONString(beans), Charset.forName("UTF-8"));
        } catch (IOException e) {
            PrintUtil.print("写入文件失败，文件IO异常.");
        } catch (InterruptedException e) {
            PrintUtil.print("等待文件写入睡眠打断异常.." + e.getCause(), SystemLog.Level.error);
        }
        PrintUtil.print("持久化数据到文件结束.", SystemLog.Level.info);

    }

    @Override
    public boolean save(RedisBean bean) {
        List<RedisBean> redisBeans = new ArrayList<>();
        redisBeans.add(bean);
        save(redisBeans);
        return false;
    }

    @Override
    public byte[] read(IRedisMap redisMap) {
        ///将文件中的缓存内容返回
        byte[]  fileBytes = Objects.requireNonNull(readFromFile(App.SAVE_FILENAME)).getBytes(StandardCharsets.UTF_8);

        ///将map缓存中的键值数据返回
        List<RedisBean> allRedis =  ((RedisMapImpl)redisMap).allMaps2RedisBean();

        return new String(fileBytes).equals("") ? JSON.toJSONString(allRedis).getBytes(StandardCharsets.UTF_8) : fileBytes;
    }

    /**
     * 从持久化文件中读取缓存内容，返回内容字符串
     * @return 返回文件字节串,读取失败时则返回空串
     */
    @NotNull
    private String readFromFile(String fileName){
        ////文件不存在则创建
        if(!isExist()){
            redisFile = createFile(fileName);
        }

        if(isExist() && redisFile.canRead()){
            try(
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(redisFile)))
            ) {
                String line;
                StringBuilder builder = new StringBuilder();
                while ( (line = reader.readLine()) != null){
                    builder.append(line);
                }
                return builder.toString();
            } catch (FileNotFoundException e) {
                PrintUtil.print("读取文件时，文件查找失败，读取流创建失败." + e.getCause(), SystemLog.Level.error);
            } catch (IOException e) {
                PrintUtil.print("读取文件时，文件IO异常，读取文件数据失败." + e.getCause(), SystemLog.Level.error);
            }
        }
        return "";
    }

    /**
     * 工具方法，负责从持久化文件中读取数据，并且用Map对象保持该数据，在需要时返回该数据，为空时重新读取该数据
     * @param fileName 类路径下的文件名称
     */
    private void loadMaps(String fileName){
        String content  = FileDaoImpl.getInstance().readFromFile(fileName);

        if(content.equalsIgnoreCase("")){
            return;
        }

        JSONArray jsonArray = JSONArray.parseArray(content);
        int size = jsonArray.size();
        for(int i = 0; i < size; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ////缓存对象key
            String key = jsonObject.getString("key");
            String preffix =key.substring(0,key.indexOf(":")+1);
            key = key.substring(key.indexOf(":")+1);
            switch (preffix){
                case "main:":
                    KeyValueNode node = new KeyValueNode();
                    node.setTimeOut(jsonObject.getLong("timeout"));
                    node.setValue(jsonObject.getString("value"));
                    maps.put(key,node);
                    break;
                case "list:":
                    ArrayList<KeyValueNode> list = new ArrayList<>();
                    getCollectionKeyValueNode(jsonObject.getString("value"),jsonObject.getLong("timeout"), list);
                    listMaps.put(key, list);
                    break;
                case "set:":
                    HashSet<KeyValueNode> set = new HashSet<>();
                    getCollectionKeyValueNode(jsonObject.getString("value"),jsonObject.getLong("timeout"), set);
                    setMaps.put(key, set);
                    break;
            }///end switch
        }///end for
    }

    /**
     * 工具方法，负责将持久化文件中数据转化为KeyValueNode对象加入集合中
     * @param values 缓存对象值
     * @param timeout 缓存对象过期时间
     * @param collection 指定要加入的集合
     */
    private void getCollectionKeyValueNode(@NotNull String values, long timeout, Collection<KeyValueNode> collection) {

        String[] arr = values.split(" ");
        for(String value : arr){
            KeyValueNode node = new KeyValueNode();
            node.setValue(value + " ");////用空格分开键对应的值
            node.setTimeOut(timeout);
            collection.add(node);
        }
    }

    @Override
    public Map<String, ArrayList<KeyValueNode>> read2list(String fileName) {
       if(listMaps.isEmpty()){
           loadMaps(fileName);
       }
        return listMaps;
    }

    @Override
    public Map<String, KeyValueNode> read(String fileName) {
        if(maps == null || maps.isEmpty()){
            loadMaps(fileName);
        }
        return maps;
    }

    @Override
    public Map<String, HashSet<KeyValueNode>> read2Set(String fileName) {
        if(setMaps == null || setMaps.isEmpty()){
            loadMaps(fileName);
        }
        return setMaps;
    }

    @Override
    public boolean update(RedisBean bean) {
        return false;
    }

    @Override
    public boolean delete(RedisBean bean) {
        return false;
    }
}
