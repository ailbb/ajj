package com.ailbb.ajj.date;

import com.ailbb.ajj.$;

import java.util.ArrayList;
import java.util.List;

public class $Timeclock {
    private List<Long> tcache = new ArrayList<>();

    /**
     * 计时器 | 获取当前时间与上一次时间的偏移量
     * @return 偏移量时间
     */
    public long timeclock() {
        return timeclock(tcache.size());
    }

    /**
     * 计时器
     * @param tag 0:启动 >0:指定位置 -1获取当前时间与第一次记录的时间偏移量
     * @return 偏移量时间
     */
    public long timeclock(int tag) {
        long now = System.currentTimeMillis();
        int cSize = tcache.size();
        boolean isEnd = tag == 0 && cSize != 0; // 当不为空时候，结束

        try {
            return cSize != 0 ? ( tag > 0 && tag < cSize
                    ? tcache.get(tag) - tcache.get(tag - 1)
                    : now - tcache.get(isEnd ? tag : (tag == -1 ? 0 : cSize-1))
            ) : 0; // 如果tag大于已缓存大小，则移动下标到末尾
        } finally {
            tcache.add(now);

            if(isEnd || tcache.size()>10000*100) tcache.clear(); // 开始/结束 标识为0 清空缓存
        }
    }

    public List<Long> getTimeclockCache() {
        return tcache;
    }

    /**
     * 计时器 | 获取当前时间与上一次时间的偏移量
     * @return 偏移量时间
     */
    public long timeclock(String message) {
        long t = timeclock();
        $.info($.now()+"/"+t+"\t"+message);
        return t;
    }

    /**
     * 计时器 | 获取当前时间与上一次时间的偏移量
     * @return 偏移量时间
     */
    public long timeclock(String message, int flag) {
        long t = timeclock(flag);
        $.info($.now()+"/"+t+"\t"+message);
        return t;
    }

}
