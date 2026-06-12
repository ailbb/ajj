import com.ailbb.ajj.$;
import com.ailbb.ajj.thread.$ThreadTraCKer;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/*
 * Created by Wz on 9/19/2020.
 */
public class ThreadTest {
    public static void main(String[] args) {
        $.enableDebug();
        Thread daemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        $.debugOut("我是："+Thread.currentThread().getId());
                        System.out.println($.thread.getRunThreadQueueActiveCount());
                        System.out.println($.thread.getRunPoolActiveCount());
                        System.out.println($.thread.getScheduledPoolActiveCount());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Daemon thread is running...");
                }
            }
        });

        Thread userThread = new Thread(new Runnable() {
            @Override
            public void run() {
//                while (true) {
                    try {
                        System.out.println("Daemon12312 thread is running...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("Daemon thread is running...");
                    }
//                }
            }
        });

        // Set daemon thread as daemon
//        daemonThread.setDaemon(false);
//        daemonThread.start();
        $.async(false, daemonThread);

        // Start threads
        userThread.start();
//        $.async(false, userThread);
    }


    public static void main11(String[] args) {

        $ThreadTraCKer threadTraCKer = new $ThreadTraCKer(()->{
            while (true){
                $.sout("测试内容---"+Thread.currentThread().getId()+"/"+Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                } catch (Exception e){}
            }
        });


        $ThreadTraCKer userThread = new $ThreadTraCKer(new Runnable() {
            @Override
            public void run() {
                System.out.println("User thread has finished its job.");
            }
        });

//        $.async( false, threadTraCKer.getRunnable());
        $.thread.asyncThread( true, threadTraCKer);
        $.thread.asyncThread( false, userThread);

//        $ThreadTraCKer threadTraCKer =

//        int i = 0;
//
//        while (i<5){
//            try {
//                i++;
//                if(threadTraCKer.isStart())
//                    $.sout("捕获到线程内容---"+threadTraCKer.getThread().getId()+"/"+threadTraCKer.getThread().getName());
//
//                Thread.sleep(1000);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }

        $.sout("主线程退出");
    }

    public static void main0(String[] args) {
        $.async(0, 1000,()->{ $.sout(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 当前活跃线程池数量："+$.thread.getRunPoolActiveCount() + "--"+$.thread.getScheduledPoolActiveCount()); });

        for(int j=30;j-->0;){
            int i=j;
            $.async(()->{
                $.firstOut("我是任务00"+i+"，我会挂死10秒。");
                try {
                    Thread.sleep(1000*10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            $.async(()->{ $.firstOut("我是任务A"+i+"，我只执行一次。"); });
            $.async(2000, ()->{ $.firstOut("我是任务B"+i+"，我延期2秒执行一次。"); });
            $.async(0, 1500,()->{ $.firstOut("我是任务C"+i+"，我延期0秒，每1.5秒执行一次。"); while (i%2==0){}});
            $.async(3000, 2000,()->{ $.firstOut("我是任务D"+i+"，我延期3秒，每2秒执行一次。"); });
        }

    }

    public static void main2(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("test-pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(6, 6,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(128), namedThreadFactory);


        executorService.execute(new ThreadTest.A());
        executorService.execute(new ThreadTest.B());
        executorService.execute(new ThreadTest.C());
        executorService.execute(new ThreadTest.D());
    }

    public static class A extends Thread {
        public A() {
            super.setName("aaa");
        }

        @Override
        public void run() {
            System.out.println("run A");
        }
    }

    public static class B extends Thread {
        public B() {
            super.setName("bbb");
        }

        @Override
        public void run() {
            System.out.println("run B");
        }
    }

    public static class C extends Thread {
        public C() {
            super.setName("ccc");
        }

        @Override
        public void run() {
            System.out.println("run C");
        }
    }

    public static class D implements Runnable {
        @Override
        public void run() {
            System.out.println("run D");
        }
    }

}
