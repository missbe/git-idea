import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class TestClass {
    @Test
    public void testObject(){
        Object o = null;
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
    public void testInetAddress() throws IOException {
//        String ip = "127.0.0.1";
//        InetAddress inetAddress = InetAddress.getByAddress(ip.getBytes());
        InetAddress local = InetAddress.getByAddress(new byte[]{127,0,0,1});
        System.out.println("isreach:" + local.isReachable(2000));
        System.out.println(local.getHostAddress());

        System.out.println(local.getCanonicalHostName());
    }
}
