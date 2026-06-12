import com.ailbb.ajj.$;

public class JarTest {
    public static void main(String[] args) {
//        $.decompilerJar(
//                "C:\\Users\\demo\\Desktop\\demo-app.jar",
//                "C:\\Users\\demo\\Desktop\\aaa"
//                );
        String path = $.decompilerJar(
                "C:\\Users\\demo\\Desktop\\demo-lib.jar",
                "C:\\Users\\demo\\Desktop\\aaa"
                );

        $.file.compress.unzip(path);
    }
}
