import cn.missbe.redis.slave.App;
import cn.missbe.redis.slave.dao.IRedisDataDao;
import cn.missbe.util.DateUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestClass {
    @Test
    public void testObject(){
        Object o;
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        o = list;
        System.out.println(o);
        System.out.println(o instanceof  List);
    }

    @Test
    public void testpath(){
        String path = TestClass.class.getResource(App.REDIS_CONFIG_NAME).getPath();

        System.out.println(path.substring(0,path.lastIndexOf("/")+1));
        System.out.println(IRedisDataDao.class.getClassLoader().getResource(App.REDIS_CONFIG_NAME).getPath());
    }
    @Test
    public void testInetAddress() throws IOException {
//        String ip = "127.0.0.1";
//        InetAddress inetAddress = InetAddress.getByAddress(ip.getBytes());
        InetAddress local = InetAddress.getByAddress(new byte[]{127,0,0,1});
        System.out.println("isreach:" + local.isReachable(2000));
        System.out.println(local.getHostAddress());

        System.out.println(local.getCanonicalHostName());
    }

    @Test
    public  void testDate() throws ParseException {
        String date = DateUtil.formateDateyyyyMMddHHmmss(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyyMMddHHMMSS").parse(date));
        System.out.println(calendar.getTimeInMillis() + System.currentTimeMillis());
        long now =  System.currentTimeMillis();
//        Date date = new Date(now);
//        String str = DateUtil.formateDateyyyyMMddHHmmss(date);
//        System.out.println(str);
//        System.out.println(DateUtil.parseDate(str).getTime());
        System.out.println(now);

    }
}
