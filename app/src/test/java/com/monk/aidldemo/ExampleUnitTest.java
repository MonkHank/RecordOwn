package com.monk.aidldemo;

import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private void println(Object lineText) {
        System.out.println(lineText);
    }

    /**
     * 测试 string IndexOf()函数
     */
    @Test
    public void testStrIndexOf() {
        String wholeStr = "我是中国共产党";
        int 共产党 = wholeStr.indexOf("共产党");
        System.out.println(共产党);
    }

    /**
     * 测试月份之间相差的数目
     */
    @Test
    public void testMonthSpace() {
        int monthSpace = getMonthSpace("2019-02-13", "2019-02-14");
        System.out.println(monthSpace);
    }

    private int getMonthSpace(String date1, String date2) {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(date1));
            c2.setTime(sdf.parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return result == 0 ? 1 : Math.abs(result);
    }

    /**
     * 测试子类复写父类方法后，父类方法return，会不会影响子类该复写方法的执行
     */
    @Test
    public void testParentReturnHasRelationshipWithChid() {
        Person stu = new Student("stu", 10);
        stu.testParentReturn(0);
    }

    /**
     * 测试 list 的 subList()函数
     */
    @Test
    public void testSubList() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("a", 1));
        list.add(new Person("b", 2));
        list.add(new Person("c", 3));
        list.add(new Person("d", 4));
        list.add(new Person("e", 5));

        list = list.subList(0, 4);

        System.out.println(list);
    }

    /**
     * 测试 ArrayList 作为参数传入其他方法执行后，会不会影响该 ArrayList；会
     */
    @Test
    public void testArrayListK() {
        ArrayList<String> strList = new ArrayList<>();
        operateArrayList(strList);
        System.out.println(strList.toString());
    }

    private void operateArrayList(ArrayList<String> stringArrayList) {
        stringArrayList.add("A");
    }

    /**
     * 测试 空字符串 substring() 的结果；报异常
     *
     * @throws Exception
     */
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);

//        int arr[]= new int[]{42,12,17,23,26,11};
//        bubbleSort(arr);
//        selectSort(arr);

//        verifyThreadPool();

//        System.out.println(0x8 & 0);

        String time = "2019-4-4 13:39:19";

        System.out.println(time.substring(0, time.length() - 3));

        String str = "";
        System.out.println(str.substring(0, str.length() - 1));

    }

    /**
     * 冒泡排序
     *
     * @param arr
     */
    void bubbleSort(int[] arr) {
        int temp;
        int flag = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    flag = 1;
                }
                System.out.println(Arrays.toString(arr));
            }
            System.out.println("\t" + Arrays.toString(arr));
            if (flag == 0) {
                break;
            }
        }
        System.out.println("bubbleSort--->" + Arrays.toString(arr));
    }

    /**
     * 选择排序
     *
     * @param arr
     */
    void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] > arr[minIndex]) {
                    minIndex = j;
                }
                System.out.println(Arrays.toString(arr));
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
            System.out.println("\t" + Arrays.toString(arr));
        }
        System.out.println("selectSort--->" + Arrays.toString(arr));
    }

    /**
     * 测试 线程池规则
     *
     * @throws InterruptedException
     */
    void verifyThreadPool() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ExecutorService service = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        service.execute(runnable);
        service.execute(runnable);
        service.execute(runnable);

        System.out.println("===先开3个线程===");
        System.out.println("核心线程数:" + ((ThreadPoolExecutor) service).getCorePoolSize());
        System.out.println("线程池中线程数:" + ((ThreadPoolExecutor) service).getPoolSize());
        System.out.println("队列任务数:" + ((ThreadPoolExecutor) service).getQueue().size());

        service.execute(runnable);
        service.execute(runnable);
        service.execute(runnable);

        System.out.println("===再开3个线程===");
        System.out.println("核心线程数:" + ((ThreadPoolExecutor) service).getCorePoolSize());
        System.out.println("线程池中线程数:" + ((ThreadPoolExecutor) service).getPoolSize());
        System.out.println("队列任务数:" + ((ThreadPoolExecutor) service).getQueue().size());

        Thread.sleep(8000);

        System.out.println("===8秒之后===");
        System.out.println("核心线程数:" + ((ThreadPoolExecutor) service).getCorePoolSize());
        System.out.println("线程池中线程数:" + ((ThreadPoolExecutor) service).getPoolSize());
        System.out.println("队列任务数:" + ((ThreadPoolExecutor) service).getQueue().size());

    }

    private static int s;
    private int nots;

    class InnerClass {
        void getint() {
            s = 1;
            nots = 2;
        }
    }

    /**
     * 计算小数形式的天数
     */
    @Test
    public void testDotDays() {
        // 0.9583333333333334
        System.out.println(Math.round((23 / 24.0 * 100) / 100));

        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println(df.format(49 / 24.0));
    }

    /**
     * 测试 Map 输出结果
     */
    @Test
    public void testMap() {
        ArrayList<String> mediaList = new ArrayList<>();
        ArrayList<String> imageList = new ArrayList<>();
        ArrayList<String> audioList = new ArrayList<>();
        imageList.add("http://www.baidu.com");
        audioList.add("http://sina.com.cn");

        for (String url : imageList) {
            String jsonArray = "{" + "\"" + "Type" + "\"" + ":" + 0 + "," + "\"" + "Guid" + "\"" + ":" + "\"" + "" + "\"" + "," + "\"" + "Url" + "\"" + ":" + "\"" + url + "\"" + "}";
            mediaList.add(jsonArray);
        }
        String videoUrl = "http://qq.com";
        if (videoUrl != null) {
            String jsonArray = "{" + "\"" + "Type" + "\"" + ":" + 1 + "," + "\"" + "Guid" + "\"" + ":" + "\"" + "" + "\"" + "," + "\"" + "Url" + "\"" + ":" + "\"" + videoUrl + "\"" + "}";
            mediaList.add(jsonArray);
        }
        for (String url : audioList) {
            String jsonArray = "{" + "\"" + "Type" + "\"" + ":" + 2 + "," + "\"" + "Guid" + "\"" + ":" + "\"" + "" + "\"" + "," + "\"" + "Url" + "\"" + ":" + "\"" + url + "\"" + "}";
            mediaList.add(jsonArray);
        }

        System.out.println(mediaList.toString());
    }

    /**
     * 测试 Calendar 获取当前时间
     */
    @Test
    public void testCalendar() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String dateStr = sdf.format(now.getTimeInMillis());
        System.out.println("currentTime = " + dateStr);
    }

    /**
     * 计算两个时间之间相差多少
     */
    @Test
    public void testTimeSeconds() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            String now = df.format(new Date());
            System.out.println("现在时间：" + now);
            Date d1 = df.parse(now);
            Date d2 = df.parse("2019-04-19 20:33");
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是毫秒级别
            long days = diff / (1000 * 60 * 60 * 24);

            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            System.out.println("" + days + "天" + hours + "小时" + minutes + "分");

            long secondes = hours * 3600 + minutes * 60;
            System.out.println("秒数：" + secondes);
        } catch (Exception e) {
        }
        Date date = new Date();
        long time = date.getTime();
        long senconds2 = time / (1000 * 60 * 60 * 24);
        System.out.println("January 1, 1970 秒数：" + senconds2);
        long roughlyYear = System.currentTimeMillis() / (1000 * 60 * 60 * 24) / 365;
        System.out.println(roughlyYear);
    }


    /**
     * 测试成员变量的赋值情况；null
     */
    @Test
    public void testParameter() {
        String[] strings = new String[]{"a", "b"};
        Base base = new Base(strings);
    }

    class Base {
        private String[] strings;

        StringCallBackImpl callback = new StringCallBackImpl(strings);

        Base(String[] strings) {
            this.strings = strings;
        }
    }

    class StringCallBackImpl {
        String[] strings;

        StringCallBackImpl(String[] strings) {
            this.strings = strings;
            System.out.println(Arrays.toString(strings));
        }
    }

    /**
     * 测试反射常见api
     *
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    @Test
    public void testReflect() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        /**===================== 类加载器 ==========================*/
        println("==================类加载器====================");
        // 1.获取一个系统的类加载器(可以获取，当前这个类 ExampleUnitTest 就是它加载的)
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        println("系统类加载器:" + classLoader);


        // 2.获取系统类加载器的父类加载器（扩展类加载器，可以获取）.
        classLoader = classLoader.getParent();
        println("扩展类加载器:" + classLoader);

        // 3.获取扩展类加载器的父类加载器（引导类加载器，不可获取）.
        classLoader = classLoader.getParent();
        println("引导类加载器:" + classLoader);


        // 4.测试当前类由哪个类加载器进行加载（系统类加载器）:
        classLoader = Class.forName("com.monk.aidldemo.ReflectClass").getClassLoader();
        System.out.println("系统类加载器:" + classLoader);
        InputStream in = classLoader.getResourceAsStream("Student.java");
        println("获取文件：" + in);

        //5. 测试 JDK 提供的 Object 类由哪个类加载器负责加载（引导类）
        classLoader = Class.forName("java.lang.Object").getClassLoader();
        println("Object类加载器：" + classLoader);

        /***===================================== 常用API =================================*/

        println("=================== 获取 Class 的三种方式 ==================");
        Class clazz = null;
        //1.通过类名
        clazz = ReflectClass.class;


        //2.通过对象名，这种方式是用在传进来一个对象，却不知道对象类型的时候使用
//        ReflectClass reflectClass = new ReflectClass();
//        clazz = reflectClass.getClass();
        //上面这个例子的意义不大，因为已经知道 reflectClass 类型是 ReflectClass 类，再这样写就没有必要了
        //如果传进来是一个Object类，这种做法就是应该的
//        Object obj = new ReflectClass();
//        clazz = obj.getClass();


        //3.通过全类名(会抛出异常)
        //一般框架开发中这种用的比较多，因为配置文件中一般配的都是全类名，通过这种方式可以得到Class实例
        String className = "com.monk.aidldemo.ReflectClass";
        clazz = Class.forName(className);

        println(clazz);

        println("=================== 无参构造和有参构造 ===================");
        // 默认无参构造函数，没有无参构造函数则会报错
        ReflectClass newInstance = (ReflectClass) clazz.newInstance();
        Constructor constructor = clazz.getConstructor(String.class, int.class);
        ReflectClass nancy = (ReflectClass) constructor.newInstance("nancy", 12);

        println(newInstance);
        println(nancy);

        println("=================== 反射方法 =============================");
        // 1. 获取指定类所有方法，不含父类方法，private方法也可以获取
        Method[] methods = clazz.getDeclaredMethods();
        for (Method t : methods) {
            println(t.getName());
        }

        // 2.获取指定方法，int直接写，不用转Integer
        Method setName = clazz.getDeclaredMethod("setName", String.class);
        Method setAge = clazz.getDeclaredMethod("setAge", int.class);
        System.out.println();
        println(setName);
        println(setAge);

        // 3. 执行方法，需要有对象
        setName.invoke(newInstance, "jack");
        setAge.invoke(newInstance, 11);
        println(newInstance.toString());

        println("=================== 反射字段 =============================");
        // 1. 获取所有字段，公有私有，不包含父类
        Field[] fields = clazz.getDeclaredFields();
        for (Field t : fields) {
            println(t.getName());
        }
        System.out.println();
        // 2. 获取指定字段
        Field name = clazz.getDeclaredField("name");
        println(name.getName());
        // 3. 使用字段，获取指定对象指定变量的值get()，修改指定对象指定字段的值set()
        Object value = name.get(newInstance);
        println(value);
        name.set(newInstance, "jack_set");
        println(name.get(newInstance));


    }


    /*** 测试枚举*/
    @Test
    public void testEnum() {
        System.out.println(Alphabet.values().length);
    }

    enum Alphabet {
        A, B, C, D
    }

    /**
     * 测试不同switch执行情况，看看适不适合将长的switch语句分段成小switch方法
     */
    @Test
    public void testSwitch() {
        int condition = 1;
        switch1(condition);
        switch2(condition);
    }

    void switch1(int condition) {
        println("switch1");
        switch (condition) {
            case 1:
                println("condition：" + condition);
                break;
            default:
                break;
        }
    }

    void switch2(int condition) {
        println("switch2");
        switch (condition) {
            case 2:
                println("condition：" + condition);
                break;
            default:
                break;
        }
    }

    /**
     * 测试 i++ 和 ++i
     */
    @Test
    public void testIPlusPlus() {
        int i = 3;
        int j;
        //先输出 i = 3，在执行 i+1 = 4
        j = i++;
        println(j);

        // 先执行 i+1 =5，再赋值j = 5
        j = ++i;
        println(j);

    }


    /**
     * 测试 for循环内部删除集合，集合长度是否自动变换
     */
    @Test
    public void testForDeleteList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("b");
        list.add("c");
        list.add("D");


        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.equals("D")) {
                list.remove(s);
            }
        }
    }

    /**
     * 双重for循环判断问题，只有一个可以不判断
     */
    @Test
    public void testDoubleFor() {

        String current = "7";
        List<String> list = Utils.strList();
        for (int i = 0; i < list.size(); i++) {
            String bean = list.get(i);
            System.out.println(bean);
            String next;
            if (i + 1 < list.size()) {
                next = list.get(i + 1);
                if (current.equals(next)) {
                    System.out.println("执行了" + next);
                }
                if (i + 1 == list.size() - 1) {
                    break;
                }
            }

        }
    }

    /**
     * 死锁
     */
    @Test
    public void testDeadLock() {
        DeadLock td1 = new DeadLock();
        DeadLock td2 = new DeadLock();
        td1.flag = 1;
        td2.flag = 0;
        //td1,td2都处于可执行状态，但JVM线程调度先执行哪个线程是不确定的。
        //td2的run()可能在td1的run()之前运行
        new Thread(td1).start();
        new Thread(td2).start();
    }

    @Test
    public void test2() {
        System.out.println( Double.parseDouble("0.04"));
    }


    @Test
    public void testStringFormat() {
        // 输出 PRAGMA foreign_keys = ON
        String query = String.format("PRAGMA foreign_keys = %s", "ON");
        System.out.println(query);
    }
}