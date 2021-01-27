import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * Created by Wz on 9/19/2020.
 */
public class ThreadTest {
    public static void main(String[] args) {
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
