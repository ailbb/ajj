import com.ailbb.ajj.$;
import com.ailbb.ajj.cache.$AutoDelayRunnable;
import com.ailbb.ajj.date.$Timeclock;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CacheTest {
    public static void main(String[] args) throws InterruptedException {
        $.cache.autoGetSaveData("1", new $AutoDelayRunnable<Object>() {
            @Override
            public Object loadData() {
                $Timeclock t = $.newTimeclock();
                try {
                    t.timeclock("[CACHE] 开始缓存信息",0);
                    Thread.sleep(200);
                    return $.now();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    t.timeclock("[CACHE] 缓存主机信息完成：", -1);
                }

            }
        }, 1000*5);

        while (true) {
            Thread.sleep(2000);
        }
    }
}
