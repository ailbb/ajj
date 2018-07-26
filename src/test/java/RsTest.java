import com.ailbb.ajj.$;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Wz on 6/20/2018.
 */
public class RsTest {
//    @Rs(path = "C:\\Windows\\System32\\drivers\\etc\\", fileName = "hosts")
    @Rs(path = "C:\\Windows\\System32\\drivers\\etc\\", fileName = "hosts.ics")
    private String text = "这是内容：\r\n";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static void main(String[] args) {
        RsTest rst = new RsTest();
        Ancls<RsTest> acl = new Ancls<RsTest>();
        rst = acl.anon(rst);

        System.out.println(rst.getText());

        new C(rst).init();
    }

    static class C {
        public RsTest rst;

        public C(RsTest rst) {
            this.rst = rst;
        }

        public void init(){
            System.out.println(".....");
        }
    }

    static class Ancls<T> {

        public T anon(Object o){
            T rst = (T)o;

            Class cls = o.getClass();

            Field[] fields = cls.getDeclaredFields();

            for(Field field :fields){
                if(field.isAnnotationPresent(Rs.class)){
                    Rs rs = (Rs) field.getAnnotation(Rs.class);
                    try {
                        String name = field.getName(); // rname

                        name = name.substring(0,1).toUpperCase() + name.substring(1); // Rname

                        Method getFuc = cls.getMethod("get" + name); // getRname

                        Method setFuc = cls.getMethod("set" + name, String.class); // setRname

                        String v = getFuc.invoke(rst).toString(); // "$B{替换}"

                        setFuc.invoke(rst, v + $.readFile(rs.path() + rs.fileName())); // "$B{替换}" -> 这是B VALUE -> "$B{这是B VALUE}"

                        $.sout($.concat("读取路径：" + rs.path(), "读取文件：" + rs.fileName()));

                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

            return rst;
        }

    }

    @Target(FIELD)
    @Retention(RUNTIME)
    @Documented
    public @interface Rs {
        String path() default "";
        String fileName() default "";
    }

}
