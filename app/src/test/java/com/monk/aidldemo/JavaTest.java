package com.monk.aidldemo;

import org.junit.Test;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JavaTest {
    private void println(Object lineText) {
        System.out.println(lineText);
    }



    /**
     * 测试子类复写父类方法后，父类方法return，会不会影响子类该复写方法的执行，不会影响
     */
    @Test
    public void testParentReturnHasRelationshipWithChid() {
        Person stu = new Student("stu", 10);
        stu.testParentReturn(0);

        println("=======正常情况=======");
        stu.testParentReturn(1);
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

        // java.util.ConcurrentModificationException
//        for (String s1 : list) {
//            if ("D".equals(s1))list.remove(s1);
//        }

        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.equals("A")) list.remove(s);
            System.out.println("-- " + list.get(i));

            System.out.println(s);
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
    public void testThread() throws InterruptedException, ExecutionException {
        ThreadPoint tp = new ThreadPoint();
        Thread threadA = tp.testJoin();

        int i = 0;
        do {
            i++;
            System.out.println(Thread.currentThread() + "：" + i + " - A线程状态：" + threadA.getState());
            //1.  A 线程先执行，执行完毕再释放CPU，其他线程再执行
//            threadA.join();

            //2. 当前线程放弃对CPU的占用，但是不一定会让线程进入暂停状态，可能由 RUNNABLE -> READY，随缘
//            Thread.yield();

            //3. 时间到了之后再对CPU的抢夺上，随缘
//            Thread.sleep(1);
        } while (i < 10);
    }

    @Test
    public void testThread2() throws ExecutionException, InterruptedException {
        ThreadPoint.testCAS();
        ThreadPoint.testCAS();

        Thread.sleep(2000);
        System.out.println(Thread.currentThread());
        System.out.println(ThreadPoint.count);

        ThreadPoint.testWaitAndSleep();
    }


    @Test
    public void test2() {
        System.out.println(Double.parseDouble("0.04"));
    }

    @Test
    public void testStringFormat() {
        // 输出 PRAGMA foreign_keys = ON
        String query = String.format("PRAGMA foreign_keys = %s", "ON");
        System.out.println(query);

        System.out.println(query.indexOf("P"));
        System.out.println(query.indexOf("_"));
        System.out.println(query.substring(query.indexOf("_") + 1));
    }

    @Test
    public void testArrayListAndLinkedList() {
        Random r = new Random();
        int next = r.nextInt(2);
        System.out.println(next);
    }


    void getRepeatCharacter(String[] letters) {
        if (letters == null || letters.length == 0)
            return;
        for (String letter : letters) {
            for (int i = 0; i < letter.length(); i++) {
                char c = letter.charAt(i);
            }
        }
    }

    void local_branch001() {
        System.out.println("测试本地分支001 ");
    }


    /** =====================Message======================*/
    Message mMessages;
    @Test
    public void testTime() {
        MQ mq = new MQ();
        Message m=null;
        for (int i = 0; i < 10; i++) {
            m = Message.obtain();
            mq.enqueueMessage(m);
        }
        System.out.println(Message.sPoolSize);

        long when = 1;

        Message prev =null;
        Message p = mMessages;

        if (when != 0) {
            while (p != null && p.when < when) {
                prev = p;
                p = p.next;
            }
        }

        int[] ints = new int[8];
        for (int anInt : ints) {
            System.out.println(anInt);
        }

        String f ="123";

    }

    static class Message{
        Message next;
        long when;
        static Message sPool;
        static int sPoolSize;
        int MAX_POOL_SIZE = 50;

        void recycleUnchecked() {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }

        static Message obtain() {
            if (sPool != null) {
                Message m = sPool;
                sPool = m.next;
                m.next = null;
                sPoolSize--;
                return m;
            }
            return new Message();
        }

    }

    class  MQ{
        Message mMessages;
        private void removeAllMessagesLocked() {
            Message p = mMessages;
            while (p != null) {
                Message n = p.next;
                p.recycleUnchecked();
                p = n;
            }
            mMessages = null;
        }

        boolean enqueueMessage(Message msg) {
            Message p = mMessages;
            if (p == null ) {
                msg.next = p;
                mMessages = msg;
            } else {
                Message prev;
                for (;;) {
                    prev = p;
                    p = p.next;
                    if (p == null ) {
                        break;
                    }
                }
                msg.next = p;
                prev.next = msg;
            }

            return true;
        }
    }

    /** =====================|= 和 &= ~ 增加和清除操作 ======================*/
    int mPrivateFlags;
    int PFLAG_FORCE_LAYOUT                = 0x00001000;
    int PFLAG_INVALIDATED                 = 0x80000000;
    @Test
    public void testYuYunSuan(){
        System.out.println("PFLAG_FORCE_LAYOUT =                 "+PFLAG_FORCE_LAYOUT);
        System.out.println("PFLAG_INVALIDATED  =                 "+PFLAG_INVALIDATED);

        // 增加操作
        mPrivateFlags |= PFLAG_FORCE_LAYOUT;
        mPrivateFlags |= PFLAG_INVALIDATED;

        int temp = mPrivateFlags & PFLAG_FORCE_LAYOUT ;
        System.out.println("mPrivateFlags & PFLAG_FORCE_LAYOUT = "+temp);

        // 清除操作
        mPrivateFlags &= ~PFLAG_FORCE_LAYOUT;
        System.out.println("mPrivateFlags =                      "+mPrivateFlags);

    }

}