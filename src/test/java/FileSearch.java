import com.ailbb.ajj.$;
import com.ailbb.ajj.file.$FileRunner;

import java.io.File;

public class FileSearch {
    public static void main(String[] args) {
        $.searchPath("D:\\Demo\\Project\\demo-dataview", new $FileRunner() {
            @Override
            public void run(File f) {
                if(f.getName().indexOf("index.html") != -1) System.out.println(f.getAbsolutePath());
            }
        });
    }
}
