package com.ailbb.ajj.thread;

import com.ailbb.ajj.$;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeoutException;

/*
 * Created by Wz on 6/20/2018.
 */
public class $Thread<T> {
    public void async(Runnable... rs){
        for(Runnable r : rs) {
            new Thread(r).start();
        }
    }

    /*
     * 异步执行，且返回集合结果
     * @param rs
     * @return
     */
    Map<Integer, T> asyncAndReturnMap($Runnable<T>... rs) throws Exception {
        return asyncAndReturnMap(3*60*1000, rs);
    }

    /*
     * 异步执行，且返回集合结果
     * @param rs
     * @return
     */
    Map<Integer, T> asyncAndReturnMap(long timeOut, $Runnable<T>... rs) throws Exception {
        Map<Integer, T> result = new TreeMap<>();

        for(int i=rs.length; i-->0;){
            $Runnable<T> r = rs[i];
            final int j = i;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    result.put(j, r.run());
                }
            }).start();
        }

        long t = System.currentTimeMillis(); // 当前时间

        while (result.size() != rs.length) {
            Thread.sleep(1000);
            System.out.println("已完成任务数：" + result.size() + "!=" + " 总队列：" +rs.length);
            if(System.currentTimeMillis() - t > timeOut) throw new TimeoutException("等待连接异常！");
        };

        return result;
    }

    /*
     * 异步执行，且返回数组结果
     * @param rs
     * @return
     */
    List<T> asyncAndReturnList($Runnable<T>... rs) throws Exception {
        Map<Integer, T> result = asyncAndReturnMap(rs);
        return $.list.toList(result.values());
    }

    /*
     * 异步执行，且返回结果
     * @param rs
     * @return
     */
    List<T> asyncAndReturn($Runnable<T>... rs) throws Exception {
        return asyncAndReturnList(rs);
    }
}
