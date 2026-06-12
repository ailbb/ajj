import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;

public class CompressTest {
    public static void main(String[] args) {
//        System.out.println($.getClassPath());
//        $.enableDebug();
        String sourcePath = "D:\\Demo\\Project\\demo-server\\src\\main\\resources\\package\\demo-package.tar.gz";
        String targetPath = "D:\\Demo\\data\\demo-package.tar.gz";
        $Result rs = $.mkLinkOrCopyFileAndUnZIP($.getFile(sourcePath),  $.getFile(targetPath));
//        if(rs.isSuccess()){
//            $.info("解压文件成功：" + sourcePath+" =====>>> " + targetPath);
//        } else {
//            $.warn("解压文件失败：");
//            rs.getMessage().forEach(e->{
//                $.warn(e);
//            });
//            rs.getError().forEach(e->{
//                e.printStackTrace();
//            });
//        }
    }
}
