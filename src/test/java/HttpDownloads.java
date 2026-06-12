import com.ailbb.ajj.$;
import org.junit.jupiter.api.Test;

public class HttpDownloads {
    @Test
    public void tt() throws InterruptedException {
        String tag="span";
        String gjz = "https://cdn.bootcdn.net/ajax/libs/font-awesome/";
        String s = $.file.readFileToText("C:\\Users\\demo\\Desktop\\codemirror.html");
        $.downloader.setMaxThread(15).smartDownload(s,"C:\\Users\\demo\\Desktop\\", tag, gjz);
    }

    @Test
    public void t1() throws InterruptedException {
        String url ="https://cdn.bootcdn.net/ajax/libs/codemirror/6.65.7/mode/asn.1/asn.1.js";
        String target = "C:\\Users\\demo\\Desktop\\";
        $.downloader.setMaxThread(15).download(url, target);
    }
}
