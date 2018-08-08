import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Wz on 7/27/2018.
 */
public class InterviewQuestion {
    /*
    1、	简述Java的优点及特性，以及Java擅长哪些领域 // 10分
            优点：跨平台、虚拟机、规范，严谨、安全、面向对象；
            特性：抽象、封装、继承、多态；
            擅长领域：大规模的应用，企业级应用、科学应用、安卓、金融、网站、嵌入式、大数据；
     */

    /*
    2、	使用正则表达式，实现方法，String trim(String str); 使得传入String参数得到的结果，去除前后空格。
     */
    static String trim(String str){
        if(null == str) return null; // 5分

        return str.replaceAll("^\\s+|\\s+$", ""); // 10分
    }

    /*
    3、	实现方法Map<String, Integer> count(String str); 统计传入String参数中每个字母和数字出现的次数。
     */
    static Map<String, Integer> count(String str) {
        if(null == str) return null; // 10分

        Map<String, Integer> map = new HashMap<>();

        for(String k: str.split("")) { // 20分
            Integer v = map.get(k);
            if(null == v) map.put(k, v = 0);

            map.put(k, ++v);
        }

        return map;
    }

    /*
    3、	实现方法Map<String, Integer> count(String str); 统计传入String参数中每个字母和数字出现的次数。
     */
    static Integer count(String str, String key) {
        Map<String, Integer> map = count(str); // // 10分

        if(null != map) return map.get(key);

        return null;
    }

    /*
    4、	实现冒泡排序 List<Integer> sort(List<Integer> list);
     */
    static List<Integer> sort(List<Integer> list){
        if(null == list) return null; // 5分

        for(int i=0;i<list.size()-1;i++){// 20分
            for(int j=0;j<list.size()-1-i;j++){
                if(list.get(j)>list.get(j)){
                    int temp=list.get(j);
                    list.set(j, list.get(j+1));
                    list.set(j+1, temp);
                }
            }
        }

        return list;
    }

    /*
    5、	设计一个线程池，实现可以启动一定数量的线程，使得每个线程每5秒，打印一次当前时间，时间格式为【“2018-08-08 08：08:08”】，并且某个线程可以随时被停止。
     */
    static class TimerThread {
        public static Map<String, Boolean> pool = new HashMap<>();

        public static void start(int num) { // 5分
            for(int i=0; i<num; i++) {
                Thread thread = new Thread(new TimerRunnableImpl()); // 5分
                pool.put(thread.getName(), true);
                thread.start();
            }
        }

        public static void stop(String name){ // 5分
            pool.put(name, false);
        }

        public static void main(String[] args) {
            TimerThread.start(5);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(7000);
                        for(String key : TimerThread.pool.keySet())
                            TimerThread.stop(key);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    static class TimerRunnableImpl implements Runnable {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();

            System.out.println(threadName + "：" + " has been started...");

            while(TimerThread.pool.get(threadName)) { // 5分
                System.out.println(threadName + "：" +  new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()) ); // 10分
                try {
                    Thread.sleep(5*1000); // 5分
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(threadName + "：" + " has been stoped...");
        }
    }
}
