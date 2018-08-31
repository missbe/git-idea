package cn.missbe.util;

import java.io.*;

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
public class SerializeUtil {

    public static byte[] serializeObject(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't Serialize Null Object");
        }
        byte[] result = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            result = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            cn.missbe.util.PrintUtil.print("Serialize Object Error:" + e, SystemLog.Level.error);
        } finally {
            close(os);
            close(bos);
        }
        return result;
    }

    public static Object deserializeObject(byte[] in) {
        return deserializeObject(in, Object.class);
    }


    public static <T> T deserializeObject(byte[] in, Class<T>... classType) {
        Object values = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                values = is.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
            cn.missbe.util.PrintUtil.print("Deserialize Object Error:" + e, SystemLog.Level.error);
        } finally {
            close(is);
            close(bis);
        }
        return (T) values;
    }

    private static void close(Closeable stream) {
        if (stream != null)
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                PrintUtil.print("Close Stream Error:" + e, SystemLog.Level.error);
            }
    }

}
