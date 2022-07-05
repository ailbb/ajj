import com.ailbb.ajj.$;
import com.ailbb.ajj.cache.$AutoDelayRunnable;
import org.junit.Test;

import java.util.Arrays;

public class CacheTest {
    public static void main(String[] args){
        $.cache.autoGetSaveData("1", new $AutoDelayRunnable<Object>() {
            @Override
            public Object loadData() {
                return Arrays.asList(1,2,3,4,5);
            }
        });
    }
}
