package cn.missbe.redis.slave.dao;

import cn.missbe.redis.server.dao.FileDaoImpl;
import cn.missbe.redis.server.map.KeyValueNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-30 上午9:18
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class FileDaoImplTest {

    @Test
    public void readFromFile() {
        String fileName = "dump.json";
        Map<String, KeyValueNode> maps = FileDaoImpl.getInstance().read(fileName);
        Map<String, HashSet<KeyValueNode>> setMaps = FileDaoImpl.getInstance().read2Set(fileName);
        Map<String, ArrayList<KeyValueNode>> listMaps = FileDaoImpl.getInstance().read2list(fileName);

        int count = 0;
        System.out.println("Key--->>>");
        ///print  maps
        for (String key : maps.keySet()) {
            count++;
            System.out.print(maps.get(key) + "->>");
        }
        System.out.println("KEY-->>>" + count);

        count = 0;
        System.out.println("List--->>>");
        ///print  list maps
        for (String key : listMaps.keySet()) {
            for (KeyValueNode node : listMaps.get(key)) {
                System.out.print(node  + "->>");
                count++;
            }
            System.out.println();
        }
        System.out.println("List-->>>" + count);

        count = 0;
        System.out.println("Set--->>>");
        ///print  list maps
        for (String key : setMaps.keySet()) {
            for (KeyValueNode node : setMaps.get(key)) {
                System.out.print(node  + "->>");
                count++;
            }
            System.out.println();
        }
        System.out.println("Set-->>>" + count);


    }

}