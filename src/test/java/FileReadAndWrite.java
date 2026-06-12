import com.ailbb.ajj.$;
import com.ailbb.ajj.file.$FileTailReader;
import org.apache.commons.io.input.Tailer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.UUID;

public class FileReadAndWrite {
    static boolean writeFlag = true;

    public static void main(String[] args) throws InterruptedException {
        String path = "/D:/Code/AILBB/ATOOLS/ajj/target/classes/tmp/test.log";

        readInterval(path);
    }

    private static void readInterval(String path) throws InterruptedException {
        while (true) {
            System.out.println("=======获取结果========");
            System.out.println($.file.fileTailReader.getInstance(new File(path)).start().readData());
            Thread.sleep(3500);
        }
    }

}
