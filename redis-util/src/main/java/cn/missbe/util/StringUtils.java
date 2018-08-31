
package cn.missbe.util;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class StringUtils{
    /**
     * 返回0-n之间的随机数
     * @param number 边界
     * @return
     */
    public static int random(int number){
        return new Random().nextInt(number);
    }

    public static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val.toLowerCase();
    }

    /**
     * 一次性判断多个传，只要有一个为空，ok
     * @param objects
     * @return
     */
    public static boolean isOneBlank(String... objects) {
        for (String str : objects) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 一次性判断多个串，只要一个串不为空 ok
     * @param objects
     * @return
     */
    public static boolean isNotAllBlank(String... objects) {
        for (String str : objects) {
            if (isNotBlank(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断指定字符串内容是否为空
     * @param str 指定字符串
     */
    public static boolean isBlank(String str) {
        return str==null || str.equals("");
    }
    /**
     * 判断指定字符串内容是否不为空
     * @param str 指定字符串
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断一个字符串在数组中存在几个
     * @param baseStr
     * @param strings
     * @return
     */
    public static int indexOf(String baseStr, String[] strings) {

        if (null == baseStr || baseStr.isEmpty() || null == strings)
            return 0;

        int i = 0;
        for (String string : strings) {
            boolean result = baseStr.equals(string);
            i = result ? ++i : i;
        }
        return i;
    }

    /**
     * 去除字符串中的空格
     **/
    public static String trimToEmpty(String str) {
        return (isBlank(str) ? "" : str.trim());
    }

    /**
     * 将 Strig 进行 BASE64 编码
     * @param str [要编码的字符串]
     * @param bf  [true|false,true:去掉结尾补充的'=',false:不做处理]
     * @return
     */
    public static String getBASE64(String str, boolean... bf) {
        if (isBlank(str))
            return null;
        String base64 = Base64.getEncoder().encodeToString(str.getBytes());
        // 去掉 '='
        if (bf.length > 0 && bf[0]) {
            base64 = base64.replaceAll("=", "");
        }
        return base64;
    }

    /**
     * 把Map转换成get请求参数类型,如 {"name"=20,"age"=30} 转换后变成 name=20&age=30
     * @param map
     * @return
     */
    public static String mapToGet(Map<?, ?> map) {
        String result = "";
        if (map == null || map.isEmpty()) {
            return result;
        }
        Set<?> keys = map.keySet();
        for (Object key : keys) {
            result += (key + "=" + map.get(key) + "&");
        }

        return isBlank(result) ? result : result.substring(0, result.length() - 1);
    }

    /**
     * 把一串参数字符串,转换成Map 如"?a=3&b=4" 转换为Map{a=3,b=4}
     * @param args
     * @return
     */
    public static Map<String, ?> getToMap(String args) {
        if (isBlank(args)) {
            return null;
        }
        args = args.trim();
        // 如果是?开头,把?去掉
        if (args.startsWith("?")) {
            args = args.substring(1, args.length());
        }
        String[] argsArray = args.split("&");

        Map<String, Object> result = new HashMap<>();
        for (String ag : argsArray) {
            if (!isBlank(ag) && ag.indexOf("=") > 0) {

                String[] keyValue = ag.split("=");
                // 如果value或者key值里包含 "="号,以第一个"="号为主 ,如 name=0=3
                // 转换后,{"name":"0=3"}, 如果不满足需求,请勿修改,自行解决.

                String key = keyValue[0];
                String value = "";
                for (int i = 1; i < keyValue.length; i++) {
                    value += keyValue[i] + "=";
                }
                value = !value.isEmpty() ? value.substring(0, value.length() - 1) : value;
                result.put(key, value);

            }
        }

        return result;
    }

    /**
     * 转换成Unicode
     * @param str
     * @return
     */
    public static String toUnicode(String str) {
        String as[] = new String[str.length()];
        String s1 = "";
        for (int i = 0; i < str.length(); i++) {
            int v = str.charAt(i);
            if (v >= 19968 && v <= 171941) {
                as[i] = Integer.toHexString(str.charAt(i) & 0xffff);
                s1 = s1 + "\\u" + as[i];
            } else {
                s1 = s1 + str.charAt(i);
            }
        }
        return s1;
    }

    /**
     * 合并数据
     * @param v
     * @return
     */
    public static String merge(Object... v) {
        StringBuilder sb = new StringBuilder();
        for (Object aV : v) {
            sb.append(aV);
        }
        return sb.toString();
    }

    /**
     * 字符串转urlcode
     * @param value 将value进行UTF-8编码
     * @return
     */
    public static String strToUrlcode(String value) {
        try {
            value = java.net.URLEncoder.encode(value, "utf-8");
            return value;
        } catch (UnsupportedEncodingException e) {
            cn.missbe.util.PrintUtil.print("字符串转换为URLCode失败,value:" + value, SystemLog.Level.error);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * urlcode转字符串
     * @param value URLCode以UTF-8解码字符串
     * @return
     */
    public static String urlcodeToStr(String value) {
        try {
            value = java.net.URLDecoder.decode(value, "utf-8");
            return value;
        } catch (UnsupportedEncodingException e) {
            PrintUtil.print("URLCode转换为字符串失败;value:" + value, SystemLog.Level.error);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断字符串是否包含汉字
     * @param txt
     * @return
     */
    public static Boolean containsCN(String txt) {
        if (isBlank(txt)) {
            return false;
        }
        for (int i = 0; i < txt.length(); i++) {

            String bb = txt.substring(i, i + 1);

            boolean cc = Pattern.matches("[\u4E00-\u9FA5]", bb);
            if (cc)
                return true;
        }
        return false;
    }

    /**
     * 把数组的空数据去掉
     * @param array
     * @return
     */
    public static List<String> array2Empty(String[] array) {
        List<String> list = new ArrayList<>();
        for (String string : array) {
            if (isNotBlank(string)) {
                list.add(string);
            }
        }
        return list;
    }

    /**
     * 把数组转换成set
     * @param array
     * @return
     */
    public static Set<?> array2Set(Object[] array) {
        Set<Object> set = new TreeSet<>();
        for (Object id : array) {
            if (null != id) {
                set.add(id);
            }
        }
        return set;
    }

    public static String sqlValidate(String str) {
        String str2 = str.toLowerCase();//统一转为小写
        String[] SqlStr1 = {"and", "exec", "execute", "insert", "select", "delete", "update", "count", "drop", "chr", "mid", "master", "truncate", "char", "declare", "sitename", "net user", "xp_cmdshell", "like", "and", "exec", "execute", "insert", "create", "drop", "table", "from", "grant", "use", "group_concat", "column_name", "information_schema.columns", "table_schema", "union", "where", "select", "delete", "update", "order", "by", "count", "chr", "mid", "master", "truncate", "char", "declare", "or"};//词语
        String[] SqlStr2 = {"\\*", "'", ";", "or", "-", "--", "\\+", "//", "/", "%", "#", "'", "\\?", "!", "\""};//特殊字符

        ///将SQL语句中的关键字以空进行替换
        for (String aSqlStr1 : SqlStr1) {
            if (str2.contains(aSqlStr1)) {
                ///i表示忽略大小写
                str = str.replaceAll("(?i)" + aSqlStr1, "");//正则替换词语，无视大小写
            }
        }
        ///将SQL语句中的引用符号以空进行替换
        for (String aSqlStr2 : SqlStr2) {
            if (str2.contains(aSqlStr2)) {
                str = str.replaceAll(aSqlStr2, "");
            }
        }
        return str;
    }

    public static String onlyLetterNumber(String str) {
        if (isBlank(str)) {
            return "";
        } else {
            return str.replaceAll("[^a-z^A-Z^0-9]", "");
        }
    }

    /**
     * 截取一段html内容里面的第一个img src(第一张图片的路径)
     *
     * @param content
     * @return
     */
    public static String getOneSrcOfImg(String content) {
        if (isBlank(content)) {
            return "";
        }
        Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
        Matcher m = p.matcher(content);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

    /**
     * 去除html标记
     * @param  htmlStr 指定的html字符串
     **/
    public static String removeHtmlTag(String htmlStr) {
        if (isBlank(htmlStr)) {
            return "";
        }
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // script
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // style
        String regEx_html = "<[^>]+>"; // HTML tag
        String regEx_space = "\\s+|\t|\r|\n";// other characters

        Pattern p_script = Pattern.compile(regEx_script,
                Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        Pattern p_style = Pattern
                .compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        Pattern p_space = Pattern
                .compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(" ");
        return htmlStr;
    }

}
