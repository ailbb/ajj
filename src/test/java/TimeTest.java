import com.ailbb.ajj.$;
import com.ailbb.ajj.date.$Timeclock;
import org.junit.jupiter.api.Test;

public class TimeTest {
    @Test
    public void t() throws InterruptedException {

        new Thread(()->{
            $Timeclock t = $.newTimeclock();
            int i=0;
            t.timeclock("开始缓存主机",0);
            $.sout(t.getTimeclockCache().size());
            while (i++<3){
                t.timeclock("打印缓存主机信息");
                $.sout(t.getTimeclockCache().size());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            t.timeclock("缓存主机完成");
            $.sout(t.getTimeclockCache().size());
            t.timeclock("清空主机缓存",-1);
            $.sout(t.getTimeclockCache().size());
        }).start();

        $Timeclock t1 = $.newTimeclock();
        int i=0;
        t1.timeclock("开始缓存文件",0);
        $.sout(t1.getTimeclockCache().size());
        while (i++<5){
            t1.timeclock("打印缓存文件信息");
            $.sout(t1.getTimeclockCache().size());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        t1.timeclock("缓存文件完成");
        $.sout(t1.getTimeclockCache().size());
        t1.timeclock("清空文件缓存",-1);
        $.sout(t1.getTimeclockCache().size());
    }
}
