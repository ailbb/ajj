import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class FileRunnerTest {
    @Test
    public void timeoutRunner(){
        try {
            new RunnableManage(UUID.randomUUID(), new Fuc(), 10*1000).start();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static class Fuc implements Runnable {
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static class RunnableManage {
        private UUID uuid;
        private Runnable runnable;
        private long timeout; // 超时时间
        private int status; // 1初始化 2运行中 3成功 4失败

        public RunnableManage(UUID uuid, Runnable runnable, long timeout) {
            this.uuid = uuid;
            this.runnable = runnable;
            this.timeout = timeout;
            this.status = 1;
        }

        public void start() throws TimeoutException{
            runTimeCount(); // 启动计时器
            runProgram(); // 启动程序
        }

        private void runProgram() {
            status = 2;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runnable.run();
                        status = 3;
                    } catch (Exception e) {
                        status = 4;
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        private void runTimeCount() throws TimeoutException {
            long timecount = 0; // 计时器

            do {
                System.out.println("当前时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                timecount+=1000;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while(timecount < timeout);

            if(status<3) throw new TimeoutException("响应超时！");
        }
    }
}
