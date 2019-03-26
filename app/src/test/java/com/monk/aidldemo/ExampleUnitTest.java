package com.monk.aidldemo;

import org.junit.Test;

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

    @Test
    public void testStrIndexOf() {
        String wholeStr="我是中国共产党";
        int 共产党 = wholeStr.indexOf("共产党");
        System.out.println(共产党);
    }

    @Test
    public void testMonthSpace() {
        int monthSpace = getMonthSpace("2019-02-13", "2019-02-14");
        System.out.println(monthSpace);
    }

    private int getMonthSpace(String date1, String date2)  {
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

    @Test
    public void testParentReturnHasRelationshipWithChid() {
        Person stu = new Student("stu", 10);
        stu.testParentReturn(0);
    }

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

    @Test
    public void testArrayListK() {
        ArrayList<String> strList = new ArrayList<>();
        operateArrayList(strList);
        System.out.println(strList.toString());
    }

    private void operateArrayList(ArrayList<String> stringArrayList) {
        stringArrayList.add("A");
    }

    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);

//        int arr[]= new int[]{42,12,17,23,26,11};
//        bubbleSort(arr);
//        selectSort(arr);

//        verifyThreadPool();

//        System.out.println(0x8 & 0);

        String str = "";
        System.out.println(str.substring(0, str.length() - 1));
    }

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

    @Test
    public void testDotDays() {
        // 0.9583333333333334
        System.out.println(Math.round((23 / 24.0 * 100) / 100));

        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println(df.format(49 / 24.0));
    }

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

    @Test
    public void testCalendar() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String dateStr = sdf.format(now.getTimeInMillis());
        System.out.println("currentTime = " + dateStr);
    }

    @Test
    public void testTimeSeconds() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            String now = df.format(new Date());
            System.out.println("现在时间："+now);
            Date d1 = df.parse(now);
            Date d2 = df.parse("2019-03-26 21:23");
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
        System.out.println("January 1, 1970 秒数："+senconds2);
        long roughlyYear = System.currentTimeMillis() / (1000 * 60 * 60 * 24) / 365;
        System.out.println(roughlyYear);
    }


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
}