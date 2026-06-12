import com.ailbb.ajj.$;
import com.ailbb.ajj.file.$FileReplacer;
import com.ailbb.ajj.unit.$Suffix;

import java.io.File;
import java.io.IOException;
import java.util.*;

/*
 * Created by Wz on 6/30/2019.
 */
public class FileTest {

    public static int r = 0;
    public static long size = 0;
    public static void main(String[] args) {
        $.system.getSystemInfo();

//        $.getFile("application.yml");
//        $.suffix.addSuffix("bbb","");
//        $.file.copyFile(
//                $.getFile("D:\\Code\\BRD\\BRD_UDO\\BRD_UDO_Server\\abm-fileserver\\src\\main\\resources\\package\\parcel\\BHBASE-2.2.3.bhbase.3.9.9-el7\\BHBASE-2.2.3.bhbase.3.9.9-el7.parcel"),
//                "D:\\fileserver\\package\\"+ "aaa.bbb",
//                true, false);

//        for(String p : Arrays.asList(
//                "D:\\Demo\\Project\\demo-frontend"
//        )){
//            for(String ps : $.file.getFile(p).list()) {
//                copyFileToModifyTxt( "D:\\Demo\\Project\\demo-frontend\\"+ps, new HashMap<String,String>(){{
//                    put(".html",".txt");
//                    put(".js",".txt");
//                    put(".css",".txt");
//                    put(".json",".txt");
//                    put(".sh",".txt");
//                    put(".conf",".txt");
//                    put(".png",".txt");
//                    put(".vue",".txt");
//                }}, true, false);
//            }
//        }

//        for(String p : Arrays.asList(
//                "D:\\Demo\\Project\\demo-frontend\\public",
//                "D:\\Demo\\Project\\demo-frontend\\shell",
//                "D:\\Demo\\Project\\demo-frontend\\src",
//                "D:\\Demo\\Project\\demo-frontend\\theme"
//        )){
//            copyFileToModifyTxt(p, new HashMap<String,String>(){{
//                put(".html",".txt");
//                put(".js",".txt");
//                put(".css",".txt");
//                put(".json",".txt");
//                put(".sh",".txt");
//                put(".conf",".txt");
//                put(".png",".txt");
//                put(".vue",".txt");
//            }}, true, false);
//        }

//        for(String p : Arrays.asList(
//                "D:\\Demo\\Project\\demo-backend\\src"
//        )){
//            copyFileToModifyTxt(p, new HashMap<String,String>(){{
//                put(".*",".txt");
//            }}, true, false);
//        }


//        $.file.readLine($.getFile("D:\\Demo\\Project\\demo-project\\ExamPlatDb_data.sql"), new $FileReplacer() {
//            @Override
//            public Object getRowContext(String row, int rowIndex) {
//                if(!row.contains("INSERT"))  {
//                    System.out.println("问题数据");
////                    System.out.println("append：" + row);
////                    System.out.println("write：" + row);
////                    $.file.writeFile("D:\\Demo\\Project\\demo-project\\ExamPlatDb_data.sql", true, row+"\r\n");
//                }
////                System.out.println("skip：" + rowIndex);
//                return null;
//            }
//        });


    }

    /*
     * 删除小数点开头的文件
     */
    public static void deleteXIAOSHUDIAN(){
        for(String path : Arrays.asList(
                "D:\\Demo\\Project\\demo-java-project"
        )) {
            $.file.searchPath(path, file -> {
                if(file.getName().startsWith(".")) {
                    file.delete();
                    System.out.println("检索文件："+file.getPath());
                }
            });

        }
    }

    /*
     * 转换文件到另外一个目录，格式变为其他
     * @param path
     * @param contextFilter
     * @param isReplace
     * @param isDel
     */
    public static void copyFileToModifyTxt(String path, Map<String,String> contextFilter, boolean isReplace, boolean isDel) {
        File f = $.file.getFile(path);

        if(f.isDirectory()) {
            for(String p : f.list()) {
                copyFileToModifyTxt(path+"\\"+p, contextFilter, isReplace, isDel);
            }
        } else {
            for(String key : contextFilter.keySet()) {
                if((key.equals(".*") || $.test(key+"$", f.getPath())) && !f.getName().startsWith(".")) {
                    $.file.copyFile(
                            f,
                            "C:\\Users\\demo\\Desktop\\output\\" + f.getName().substring(0, f.getName().lastIndexOf(".")) + contextFilter.get(key),
                            isReplace, isDel);
                }
            }
        }
    }

    /*
     * 如果文件里面包含某个内容，则替换后输出
     * @param path
     * @param sourceFix
     * @param contextFilter
     * @param isReplace
     * @param isDel
     */
    public static void copyFileToModifyFix(String path, String sourceFix, Map<String,String> contextFilter, boolean isReplace, boolean isDel) {
        File f = $.file.getFile(path);

        sourceFix = (sourceFix.equals("*") || sourceFix.equals(".*")) ? "\\.[^\\.]+$" : (sourceFix + "$"); // 默认匹配结尾的
        sourceFix = sourceFix.replaceAll("\\$+", "\\$"); // 全匹配

        if(f.isDirectory()) {
            for(String p : f.list()) {
                copyFileToModifyFix(path+"\\"+p, sourceFix, contextFilter, isReplace, isDel);
            }
        } else {
            if($.test(sourceFix, f.getPath())) {
                for(String key : contextFilter.keySet()) {
                    if($.readFileToText(f.getPath()).contains(key)) {
                        $.file.copyFile(f, f.getPath().replaceAll(sourceFix, contextFilter.get(key)), isReplace, isDel);
                    }
                }
            }
        }
    }
    
    public static void main1(String[] args) throws Exception {}
}
