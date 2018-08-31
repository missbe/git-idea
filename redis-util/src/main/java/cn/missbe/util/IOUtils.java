package cn.missbe.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-31 下午2:46
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class IOUtils {

    /**
     * 从输入读取字节数据，处理成字符串形式
     * @param reader  输入流
     * @param SERVER_OK  流中结束字符
     * @return 处理过后的字符串
     * @throws IOException 读取过程发生IO异常
     */
    public static String  parseStream(InputStream reader,String SERVER_OK) throws IOException {
        StringBuilder builder = new StringBuilder();
        int len ;
        byte[] res = new byte[128];
        while((len = reader.read(res)) != -1){///读取失败
            String tmp = new String(res,0, len, StandardCharsets.UTF_8);
            if(tmp.endsWith(SERVER_OK)){
                tmp = tmp.substring(0, tmp.lastIndexOf(SERVER_OK));
                builder.append(tmp);
                break; ///读取结束
            }////end if
            builder.append(tmp);
        }
        return builder.toString();
    }
}
