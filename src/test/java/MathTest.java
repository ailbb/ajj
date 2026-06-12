import com.ailbb.ajj.$;

import java.net.UnknownHostException;

public class MathTest {
    public static void main(String[] args) throws UnknownHostException {

        $.sout($.getInetAddress().getHostName().toLowerCase());
        $.sout($.max(1L,2L,3L,4L,5L));
        $.sout($.min(1L,2L,3L,4L,5L));
        $.sout($.avg(1L,2L,3L,4L,5L));
        $.sout($.max(1,2,3,4,5));
        $.sout($.min(1,2,3,4,5));
        $.sout($.avg(1,2,3,4,5));
    }
}
