import com.ailbb.ajj.$;
import com.ailbb.ajj.encrypt.EncryptUtil;
import com.ailbb.ajj.encrypt.Encryption;
import com.ailbb.ajj.encrypt.util.Sm4Util;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Wz on 8/18/2020.
 */
public class EncryptTest {
//    static int times = 2000; // 总数据大小
    static int times = 500*10000; // 总数据大小
    static int threads = 100; // 总数据大小
    static int tnum = 0;
    static List<String> tt = Arrays.asList(
            "182028107", // 单条加密测试数据1
            "{\"name\":\"color\",\"sex\":\"man\"}", // 单条加密测试数据2
            "07"
    );

    public static void main(String[] args) throws Exception {
        String[] k1 = new String[]{"1","123","123456","123456789"};

        String[] k2 = new String[]{
                "*[%]",
                "#[3]",
                "#[%]",
                "*[3]*[3]",
                "*[3]*[%]",
                "*[3]#[3]",
                "*[3]#[%]",
                "*[%]*[3]",
                "*[%]*[%]",
                "*[%]#[3]",
                "*[%]#[%]",
                "#[3]*[3]",
                "#[3]*[%]",
                "#[3]#[3]",
                "#[3]#[%]",
                "#[%]*[3]",
                "#[%]*[%]",
                "#[%]#[3]",
                "#[%]#[%]"
        };

        for(String k : k1) {
            System.out.println("原始值："+k);
            for(String _k : k2)
                System.out.println("密钥："+ 11223344 + " 规则："+_k + "\t加密值：" +new Sm4Util("11223344").encrypt(_k, k));
        }
    }

    public static void main1(String[] args) throws Exception {
//        for(int i=0; i<100; i++){
//            System.out.println("\"182"+Math.round(Math.random()*9)+""+Math.round(Math.random()*9)+""+Math.round(Math.random()*9)+""+Math.round(Math.random()*9)+""+Math.round(Math.random()*9)+""+Math.round(Math.random()*9)+""+Math.round(Math.random()*9)+""+Math.round(Math.random()*9)+"\",");
//            if(i==100) return;
//        }

        List<String> keys = Arrays.asList(
                "*[11]",
                "*[%]",

                "#[3]*[%]",
                "#[3]*[8]",

                "#[3]*[6]#[%]",
                "#[3]*[%]#[2]",


                "#[3]*[%]#[3]"
                ,
                "",
                "*",
                "#",
                "%",
                "[]",
                "[",
                "]",
                "1",

                "*[3]",

                "*[3]",
                "*[%]",
                "#[3]",
                "#[%]",

                "*[3]*[3]",
                "*[3]*[%]",
                "*[3]#[3]",
                "*[3]#[%]",

                "*[%]*[3]",
                "*[%]*[%]",
                "*[%]#[3]",
                "*[%]#[%]",

                "#[3]*[3]",
                "#[3]*[%]",
                "#[3]#[3]",
                "#[3]#[%]",

                "#[%]*[3]",
                "#[%]*[%]",
                "#[%]#[3]",
                "#[%]#[%]",



                "*[3]*[3]*[3]",
                "*[3]*[3]*[%]",
                "*[3]*[3]#[3]",
                "*[3]*[3]#[%]",

                "*[3]*[%]*[3]",
                "*[3]*[%]*[%]",
                "*[3]*[%]#[3]",
                "*[3]*[%]#[%]",

                "*[3]#[3]*[3]",
                "*[3]#[3]*[%]",
                "*[3]#[3]#[3]",
                "*[3]#[3]#[%]",

                "*[3]#[%]*[3]",
                "*[3]#[%]*[%]",
                "*[3]#[%]#[3]",
                "*[3]#[%]#[%]",

                "*[3]*[3]*[3]",
                "*[%]*[3]*[%]",
                "#[3]*[3]#[3]",
                "#[%]*[3]#[%]",

                "*[3]*[%]*[3]",
                "*[%]*[%]*[%]",
                "#[3]*[%]#[3]",
                "#[%]*[%]#[%]",

                "*[3]#[3]*[3]",
                "*[%]#[3]*[%]",
                "#[3]#[3]#[3]",
                "#[%]#[3]#[%]",

                "*[3]#[%]*[3]",
                "*[%]#[%]*[%]",
                "#[3]#[%]#[3]",
                "#[%]#[%]#[%]"
        );

        System.out.println("原值："+"13452180666");
//        String ekey = UUID.randomUUID().toString().getRowContext("-","");
        String ekey = "2";

        for(String k : keys) {
            try {
                List<String> v = new Sm4Util(ekey).encrypt(k, Arrays.asList("13452180666"));
                System.out.println("秘钥：" + k +" 加密值：" + v);
                List<String> v1 = new Sm4Util(ekey).decrypt(k, v);
                System.out.println("解密" + v1);
            } catch (Exception e) {
                System.out.println("错误秘钥："+k);
                e.printStackTrace();
            }
        }

//        testBingFa();
//
        for(String k : keys) {
            System.out.println("测试规则Key："+k);
            testFor(k);
        }

    }

    private static void testBingFa(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(20,100,10, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
        for (int i=0;i<threads;i++){
            executor.submit(()->{

                String json = null;
                int number = new Random().nextInt(100);
                json = tt.get(tnum)+number;
                try {
                // 自定义的32位16进制密钥
                String key = "888368581322491ace9q79348a2757d1";
                System.out.println("原值:"+json);
                String cipher = $.encrypt.SM4encode(json, key);
                System.out.println("加密:"+cipher);

                Thread.sleep(100);

                json = $.encrypt.SM4decode(cipher, key);
                System.out.println("解密:"+json);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }


        for (int i=0;i<threads;i++){
            executor.submit(()->{

                String json = null;
                int number = new Random().nextInt(100);
                json = tt.get(tnum)+number;
                try {
                // 自定义的32位16进制密钥
                String key = "888368581322491ace9q79348a2757d1";
                System.out.println("原值:"+json);

                Encryption eu = new Sm4Util(key);
                String cipher = eu.encrypt(json);
                System.out.println("加密:"+cipher);

                Thread.sleep(100);

                json = eu.decrypt(cipher);
                System.out.println("解密:"+json);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        for (int i=0;i<threads;i++){
            executor.submit(()->{

                String json = null;
                int number = new Random().nextInt(100);
                json = tt.get(tnum)+number;
                try {
                // 自定义的32位16进制密钥
                String key = "888368581322491ace9q79348a2757d1";
                String rule = "#[3]*[%]#[2]";
                System.out.println("原值:"+json);

                Encryption eu = new Sm4Util(key);
                String cipher = eu.encrypt(rule, json);
                System.out.println("加密:"+cipher);

                Thread.sleep(100);

                json = eu.decrypt(rule, cipher);
                System.out.println("解密:"+json);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 测试For循环
     * @return
     * @throws Exception
     */
    private static void testFor(String ruleKey) throws Exception {
        for(; tnum< tt.size(); tnum++) {

            for(int k=0; k<10; k++) {
                long put = 0;
                long jmt = 0;
                int lth = 0;

                for (int j=0;j<2;j++){
                    long start,end,tmp=0;
                    start = System.currentTimeMillis();
                    lth = testFor(j, ruleKey);
                    end = System.currentTimeMillis();
                    if(j == 0) put = (end - start);
                    else jmt = (end - start);
                }

                System.out.println("测试数据："+ (tnum+1) + " 长度：" + lth +  " 数据大小（万）：" + times/10000 + " 普通运行（ms）：" + put + " 加密运行（ms）：" + jmt);
            }
        }

        tnum = 0;
    }

    private static int testFor (int j, String ruleKey) throws Exception{
        String json = null;
        for (int i=0;i<times;i++){
            int number = new Random().nextInt(100);
            json = tt.get(tnum)+number;
            // 自定义的32位16进制密钥
            String key = "888368581322491ace9q79348a2757d1";

            if(j == 1) {
                String cipher = Sm4Util.getInstance(key).encrypt(ruleKey, json);
            }
        }

        return json.length();
    }
}
