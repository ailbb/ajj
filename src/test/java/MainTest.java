import java.util.*;
import java.util.stream.Collectors;

public class MainTest {
    public static void main(String[] args) {
        long T = System.currentTimeMillis();
        System.out.println("开始准备数据");
        List<AAA> A = new ArrayList<>();
        List<AAA> B = new ArrayList<>();

        for(int i=1; i<10*1000; i++) {
            A.add(new AAA().setName(i+""));
            B.add(new AAA().setName(Math.round(Math.random()*10*1000)+""));
        }

        System.out.println("准备数据完成"+A.size()+"---"+B.size());

        System.out.println("开始执行2222");
        long T2 = System.currentTimeMillis();
        List diff2 = new ArrayList();

        Map<String,Boolean> result = new HashMap<>();
        A.forEach(r->result.put(r.getName(), true));
        B.forEach(r->{
            if(null == result.get(r.getName()) || !result.get(r.getName()))
                diff2.add(r.getName());
        });

        System.out.println("毫秒："+(System.currentTimeMillis()-T2));
        System.out.println("大小："+diff2.size());
        System.out.println("2----执行完成");

        System.out.println("开始执行1111");

        List diff1 = A.stream().filter(a -> !B.stream().anyMatch(b -> a.getName() .equals(b.getName()))).collect(Collectors.toList());

        System.out.println("毫秒："+(System.currentTimeMillis()-T));
        System.out.println("大小："+diff1.size());
        System.out.println("1----执行完成");

    }

    static class AAA {
        String name;

        public String getName() {
            return name;
        }

        public AAA setName(String name) {
            this.name = name;
            return this;
        }
    }
}
