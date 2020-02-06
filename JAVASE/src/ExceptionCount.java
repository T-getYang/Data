import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ExceptionCount {
    public static void main(String[] args) {
        /*
        1.
        FileOutputStream outputStream = new FileOutputStream(new File("G:\copy"));
        java.io.FileNotFoundException: G:\copy (拒绝访问。)
        原因：路径为文件夹
        解决：
        if(!new File("G:\copy").isDirectory() ){

        }
        * */


        /*
        2.
        Exception in thread "main" java.lang.NullPointerException

        String msg = null;
        msg.toString();
        原因：对象为null
        解决：
        if(msg != null){
            msg.toString();
        }
        * */



        /*
        3
        Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 3

        Integer[] ints = new Integer[2];
        ints[3].intValue();
        原因：数组下标越界
        解决：最大下标-1   ints[ints.length-1].intValue();

        * */

        /*
        4
        无法创建到数据库服务器的连接。
        com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: Could not create connection to database server
        原因：数据库未开启
        解决：开启数据库
        * */

        /*
        5
        访问被拒绝
        java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)
        原因：数据库密码错误
        解决：确认密码
        * */


        /*
        6
        Exception in thread "main" java.lang.ArithmeticException: / by zero
        int i = 0;
        int j = 0;
        System.out.println(i/j);
        原因：0/0
        解决：
        if(i != 0 || j != 0){
            System.out.println(i/j);
        }
        *
        * */

        /*
        7.
        Exception in thread "main" java.lang.StringIndexOutOfBoundsException: String index out of range: -1
        String str = "1234";

        str.substring(-1,2);
        原因：-1小于0，
        解决：
        substring(int beginIndex,int endIndex)

        beginIndex>=0， endIndex小于该 String对象的长度， beginIndex < endIndex 。

        * */


        /*
        8.
        String str = "123q";

        Integer.valueOf(str);

        Exception in thread "main" java.lang.NumberFormatException: For input string: "123q"
        原因：str不全为数字，
        解决：
        Pattern compile = Pattern.compile("[0-9]+");
        if(compile.matcher(str).matches()){
            System.out.println(Integer.valueOf(str));

        }
        * */

        /*
        9.
        Exception in thread "main" java.lang.NegativeArraySizeException
        Integer[] ints = new Integer[-1];
        原因：ints初始化长度为负数，
        解决：大于0
        * */

        /*
        10
        java.io.FileNotFoundException
        File file = new File("G.sd");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        原因：系统找不到指定的文件。
        解决：
        if(file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        * */


        /*
        11.
        Exception in thread "main" java.lang.ClassCastException:
        Object o = new Object();
        MyFile myFile = (MyFile) o;
        原因：类型强制转换异常
        解决：
        if(o instanceof MyFile){
            MyFile myFile = (MyFile) o;
            System.out.println("iioi");
        }
        * */


        /*
        12
        org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): cn.disizu.springboot.duozhuayu.duozhuayu.mapper.book.BookMapper.getBooks
        原因：绑定异常,无效的绑定语句
        解决：在配置文件中配置相应的路径
        如：type-aliases-package: cn.disizu.springboot.duozhuayu.duozhuayu.mapper
        * */


        /*
        13
         java.net.BindException: Address already in use: bind
         原因：端口被占用
        解决：改端口号或者结束该端口号
        * */


        /*
        14
        java.lang.ClassNotFoundException: ABC
        try {
            Class<?> demo = Class.forName("ABC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        原因：指定的类不存在
        解决：
        if(isForName("ABC")){
            try {
                Class<?> demo = Class.forName("ABC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("不存在");
        }
        * */

        /*
        15

        Exception in thread "main" java.lang.ArrayStoreException: java.lang.Integer

        Object[] strs = new String[1];
        strs[0] = new Integer(1);

        原因：数组存储异常
        解决：
        Object[] strs = new String[1];
        Integer integer = new Integer(1);
        if(integer.getClass().equals("string".getClass())){
            strs[0] = integer;
        }
        * */


        /*
        16.
        Color color = new Color(256, 1, 1);

        Exception in thread "main" java.lang.IllegalArgumentException: Color parameter outside of expected range: Red
        原因：方法的参数错误
        解决：参数都要大于-1并且要小于256

        * */

        /*
        17
        Exception in thread "main" java.lang.UnsupportedOperationException

        Integer[] integers = {1,2,3};
        List<Integer> list = Arrays.asList(integers);
        list.add(1);

        原因：不支持操作
        解决：

        Integer[] integers = {1,2,3};
        List<Integer> list = Arrays.asList(integers);
        List<Integer> newList = new ArrayList<>(list);
        newList.add(1);
        * */




        /*
        18.
        java.lang.NoSuchFieldException: name


        Class<MyFile> myFileClass = MyFile.class;
        try {
            Field field = myFileClass.getField("demo");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        原因：反射异常
        解决：在MyFile中添加"demo"字段

        * */


        /*
        19
        java.text.ParseException: Unparseable date: "1999-09-26"

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:HH");

        try {
            simpleDateFormat.parse("1999-09-26");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        原因：格式不对
        解决：simpleDateFormat.parse("1999-09-26:12");

        * */


        /*
        20
        java.lang.NoSuchMethodException: com.company.MyFile.getDemo()

        Class<MyFile> myFileClass = MyFile.class;
        try {
            Method field = myFileClass.getMethod("getDemo");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        原因：反射异常
        解决：在MyFile中添加"getDemo"方法
        * */


    }
    public static boolean isForName(String name){
        try {
            Class<?> demo = Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {

            return false;
        }
    }
}
