import com.ailbb.ajj.$;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class FileWriteInterval {
    static boolean writeFlag = true;

    public static void main(String[] args) throws InterruptedException {
        String path = "/D:/Code/AILBB/ATOOLS/ajj/target/classes/tmp/test.log";
        writeLog(path, 200); // 写日志文件线程
    }


    private static void writeLog(String path, int sleeptime) throws InterruptedException {
        try
        {
            path = $.getPath(path);
            File f = new File(path);
            f.delete();
            $.mkdir($.sout(path));

            FileOutputStream fos = new FileOutputStream(f, true);
            int i=0;
            while (writeFlag)
            {
                if(i == 1000) writeFlag = false;
                if(i%5==0){
                    for(int j=0;j<5;j++) {
                        fos.write(($.sout("["+new Date() + "] "+demoLog2.split("\n")[i++])).getBytes());
                        fos.write("\r\n".getBytes());
                    }
                }
                fos.write(($.sout("["+new Date() + "] "+demoLog2.split("\n")[i++])).getBytes());
                fos.write("\r\n".getBytes());
                fos.flush();

                Thread.sleep(Math.round(Math.random()*sleeptime));
            }
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Thread.sleep(90*1000);

        writeLog(path, sleeptime);
    }


    static String demoLog = "测试中文乱码\n\"C:\\Program Files\\Java\\jdk-17.0.3.1\\bin\\java.exe\" -Dmaven.multiModuleProjectDirectory=D:\\Code\\AILBB\\ATOOLS\\ajj \"-Dmaven.home=D:\\Program Files\\Apache\\apache-maven-3.8.6\" \"-Dclassworlds.conf=D:\\Program Files\\Apache\\apache-maven-3.8.6\\bin\\m2.conf\" \"-Dmaven.ext.class.path=C:\\Program Files\\JetBrains\\IntelliJ IDEA 2022.2.3\\plugins\\maven\\lib\\maven-event-listener.jar\" \"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA 2022.2.3\\lib\\idea_rt.jar=63934:C:\\Program Files\\JetBrains\\IntelliJ IDEA 2022.2.3\\bin\" -Dfile.encoding=UTF-8 -classpath \"D:\\Program Files\\Apache\\apache-maven-3.8.6\\boot\\plexus-classworlds-2.6.0.jar;D:\\Program Files\\Apache\\apache-maven-3.8.6\\boot\\plexus-classworlds.license\" org.codehaus.classworlds.Launcher -Didea.version=2022.2.3 -s \"D:\\Program Files\\Apache\\apache-maven-3.8.6\\conf\\settings.xml\" -Dmaven.repo.local=C:\\Users\\demo\\.m2\\repository install\n" +
            "[INFO] Scanning for projects...\n" +
            "[INFO] Inspecting build with total of 1 modules...\n" +
            "[INFO] Installing Nexus Staging features:\n" +
            "[INFO]   ... total of 1 executions of maven-deploy-plugin replaced with nexus-staging-maven-plugin\n" +
            "[INFO] \n" +
            "[INFO] ---------------------------< com.ailbb:ajj >----------------------------\n" +
            "[INFO] Building com.ailbb:ajj 1.14\n" +
            "[INFO] --------------------------------[ jar ]---------------------------------\n" +
            "[INFO] \n" +
            "[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ ajj ---\n" +
            "[INFO] Using 'UTF-8' encoding to copy filtered resources.\n" +
            "[INFO] Copying 2 resources\n" +
            "[INFO] \n" +
            "[INFO] --- maven-compiler-plugin:3.11.0:compile (default-compile) @ ajj ---\n" +
            "[INFO] Changes detected - recompiling the module! :source\n" +
            "[INFO] Compiling 98 source files with javac [debug target 17] to target\\classes\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/log/$Logger.java:[97,30] non-varargs call of varargs method with inexact argument type for last parameter;\n" +
            "  cast to java.lang.Object for a varargs call\n" +
            "  cast to java.lang.Object[] for a non-varargs call and to suppress this warning\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/http/$Http.java:[421,26] non-varargs call of varargs method with inexact argument type for last parameter;\n" +
            "  cast to java.lang.Object for a varargs call\n" +
            "  cast to java.lang.Object[] for a non-varargs call and to suppress this warning\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/http/$Http.java:[522,50] non-varargs call of varargs method with inexact argument type for last parameter;\n" +
            "  cast to java.lang.Object for a varargs call\n" +
            "  cast to java.lang.Object[] for a non-varargs call and to suppress this warning\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/http/$Url.java:[61,60] non-varargs call of varargs method with inexact argument type for last parameter;\n" +
            "  cast to java.lang.Object for a varargs call\n" +
            "  cast to java.lang.Object[] for a non-varargs call and to suppress this warning\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/date/$Date.java:[50,66] non-varargs call of varargs method with inexact argument type for last parameter;\n" +
            "  cast to java.lang.Object for a varargs call\n" +
            "  cast to java.lang.Object[] for a non-varargs call and to suppress this warning\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/lang/$String.java:[76,31] non-varargs call of varargs method with inexact argument type for last parameter;\n" +
            "  cast to java.lang.Object for a varargs call\n" +
            "  cast to java.lang.Object[] for a non-varargs call and to suppress this warning\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/lang/$String.java:[88,31] non-varargs call of varargs method with inexact argument type for last parameter;\n" +
            "  cast to java.lang.Object for a varargs call\n" +
            "  cast to java.lang.Object[] for a non-varargs call and to suppress this warning\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/lang/$String.java:[190,27] Character(char) in java.lang.Character has been deprecated and marked for removal\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/lang/$Object.java:[51,72] non-varargs call of varargs method with inexact argument type for last parameter;\n" +
            "  cast to java.lang.Object for a varargs call\n" +
            "  cast to java.lang.Object[] for a non-varargs call and to suppress this warning\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/mail/$Mail.java:[71,56] non-varargs call of varargs method with inexact argument type for last parameter;\n" +
            "  cast to java.lang.Object for a varargs call\n" +
            "  cast to java.lang.Object[] for a non-varargs call and to suppress this warning\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/mybatis/$MybatisDynamicSQL.java:[52,28] non-varargs call of varargs method with inexact argument type for last parameter;\n" +
            "  cast to java.lang.Object for a varargs call\n" +
            "  cast to java.lang.Object[] for a non-varargs call and to suppress this warning\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/date/$Cron.java:[16,13] org.springframework.scheduling.support.CronSequenceGenerator in org.springframework.scheduling.support has been deprecated and marked for removal\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/date/$Cron.java:[39,42] org.springframework.scheduling.support.CronSequenceGenerator in org.springframework.scheduling.support has been deprecated and marked for removal\n" +
            "[WARNING] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/date/$Cron.java:[39,38] CronSequenceGenerator(java.lang.String) in org.springframework.scheduling.support.CronSequenceGenerator has been deprecated and marked for removal\n" +
            "[INFO] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/lang/$Double.java: Some input files use or override a deprecated API.\n" +
            "[INFO] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/lang/$Double.java: Recompile with -Xlint:deprecation for details.\n" +
            "[INFO] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/jdbc/$JDBC.java: Some input files use unchecked or unsafe operations.\n" +
            "[INFO] /D:/Code/AILBB/ATOOLS/ajj/src/main/java/com/ailbb/ajj/jdbc/$JDBC.java: Recompile with -Xlint:unchecked for details.\n" +
            "[INFO] \n" +
            "[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ ajj ---\n" +
            "[INFO] Using 'UTF-8' encoding to copy filtered resources.\n" +
            "[INFO] skip non existing resourceDirectory D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\test\\resources\n" +
            "[INFO] \n" +
            "[INFO] --- maven-compiler-plugin:3.11.0:testCompile (default-testCompile) @ ajj ---\n" +
            "[INFO] Changes detected - recompiling the module! :dependency\n" +
            "[INFO] Compiling 27 source files with javac [debug target 17] to target\\test-classes\n" +
            "[INFO] /D:/Code/AILBB/ATOOLS/ajj/src/test/java/DateTest.java: D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\test\\java\\DateTest.java uses or overrides a deprecated API.\n" +
            "[INFO] /D:/Code/AILBB/ATOOLS/ajj/src/test/java/DateTest.java: Recompile with -Xlint:deprecation for details.\n" +
            "[INFO] /D:/Code/AILBB/ATOOLS/ajj/src/test/java/FileRename.java: Some input files use unchecked or unsafe operations.\n" +
            "[INFO] /D:/Code/AILBB/ATOOLS/ajj/src/test/java/FileRename.java: Recompile with -Xlint:unchecked for details.\n" +
            "[INFO] \n" +
            "[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ ajj ---\n" +
            "[INFO] Surefire report directory: D:\\Code\\AILBB\\ATOOLS\\ajj\\target\\surefire-reports\n" +
            "\n" +
            "-------------------------------------------------------\n" +
            " T E S T S\n" +
            "-------------------------------------------------------\n" +
            "Running CacheTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running CronTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running DateTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running EncryptTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running FileRunnerTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running FileTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec\n" +
            "Running HttpTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running JarTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running JDBCTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running JiqunJDBCTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running JSONTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running MainTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running PartitionLastTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running PropertiesTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running ShangbaoJDBCTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running SQLTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running StringEncryptorTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "Running ThreadTest\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec\n" +
            "\n" +
            "Results :\n" +
            "\n" +
            "Tests run: 0, Failures: 0, Errors: 0, Skipped: 0\n" +
            "\n" +
            "[INFO] \n" +
            "[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ ajj ---\n" +
            "[INFO] Building jar: D:\\Code\\AILBB\\ATOOLS\\ajj\\target\\ajj-1.14.jar\n" +
            "[INFO] \n" +
            "[INFO] --- maven-source-plugin:3.3.0:jar-no-fork (attach-sources) @ ajj ---\n" +
            "[INFO] Building jar: D:\\Code\\AILBB\\ATOOLS\\ajj\\target\\ajj-1.14-sources.jar\n" +
            "[INFO] \n" +
            "[INFO] --- maven-javadoc-plugin:3.5.0:jar (attach-javadocs) @ ajj ---\n" +
            "[INFO] Configuration changed, re-generating javadoc.\n" +
            "[WARNING] Javadoc Warnings\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:56: warning: no comment\n" +
            "[WARNING] public class $ {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:58: warning: no comment\n" +
            "[WARNING] public static String $NAME = \"\";\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:64: warning: no comment\n" +
            "[WARNING] public static Map<String, Proxy> $PROXY = new HashMap<String, Proxy>();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:62: warning: no comment\n" +
            "[WARNING] public static final String $PROXY_PATH = \"ajj.json\";\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:59: warning: no comment\n" +
            "[WARNING] public static String $ROOT = \"/\" + $NAME;\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:61: warning: no comment\n" +
            "[WARNING] public static final int $TIMEOUT = 100000;\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:130: warning: no comment\n" +
            "[WARNING] public static Encryption aes = new AESUtil();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:114: warning: no comment\n" +
            "[WARNING] public static $Bean bean = new $Bean();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:111: warning: no comment\n" +
            "[WARNING] public static $Byte byter = new $Byte();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:81: warning: no comment\n" +
            "[WARNING] public static $CacheManagerImpl cache = new $CacheManagerImpl();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:102: warning: no comment\n" +
            "[WARNING] public static $Charset charset = new $Charset();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:78: warning: no comment\n" +
            "[WARNING] public static $Date date = new $Date();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:109: warning: no comment\n" +
            "[WARNING] public static $Double doubled = new $Double();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:68: warning: no comment\n" +
            "[WARNING] public static $Download downloader = new $Download();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:118: warning: no comment\n" +
            "[WARNING] public static EncryptUtil encrypt = new EncryptUtil();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:74: warning: no comment\n" +
            "[WARNING] public static $File file = new $File();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:67: warning: no comment\n" +
            "[WARNING] public static $Http http = new $Http();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:107: warning: no comment\n" +
            "[WARNING] public static $Integer integer = new $Integer();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:90: warning: no comment\n" +
            "[WARNING] public static $Jar jar = new $Jar();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:91: warning: no comment\n" +
            "[WARNING] public static $Java java = new $Java();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:124: warning: no comment\n" +
            "[WARNING] public static $JDBC jdbc = new $JDBC();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:105: warning: no comment\n" +
            "[WARNING] public static $Json json = new $Json();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:112: warning: no comment\n" +
            "[WARNING] public static $List list = new $List();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:127: warning: no comment\n" +
            "[WARNING] public static $Logger logger = new $Logger();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:108: warning: no comment\n" +
            "[WARNING] public static $Longer longer = new $Longer();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:136: warning: no comment\n" +
            "[WARNING] public static $Mail mail = new $Mail();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:113: warning: no comment\n" +
            "[WARNING] public static $Map map = new $Map();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:115: warning: no comment\n" +
            "[WARNING] public static $Mybatis mybatis = new $Mybatis();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:110: warning: no comment\n" +
            "[WARNING] public static $Object object = new $Object();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:75: warning: no comment\n" +
            "[WARNING] public static $Path path = new $Path();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:134: warning: no comment\n" +
            "[WARNING] public static $Properties properties = file.properties;\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:94: warning: no comment\n" +
            "[WARNING] public static $Regex regex = new $Regex();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:69: warning: no comment\n" +
            "[WARNING] public static $Server server = new $Server();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:131: warning: no comment\n" +
            "[WARNING] public static Encryption sm4 = new Sm4Util();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:106: warning: no comment\n" +
            "[WARNING] public static $String string = new $String();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:129: warning: no comment\n" +
            "[WARNING] public static Encryption stringencryptor = new StringEncryptorUtil();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:101: warning: no comment\n" +
            "[WARNING] public static $Suffix suffix = new $Suffix();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:121: warning: no comment\n" +
            "[WARNING] public static $System system = new $System();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:97: warning: no comment\n" +
            "[WARNING] public static $Test test = new $Test();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:87: warning: no comment\n" +
            "[WARNING] public static $Thread thread = new $Thread();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:84: warning: no comment\n" +
            "[WARNING] public static $Tomcat tomcat = new $Tomcat();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:100: warning: no comment\n" +
            "[WARNING] public static $Unit unit = new $Unit();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:70: warning: no comment\n" +
            "[WARNING] public static $Url url = new $Url();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:71: warning: no comment\n" +
            "[WARNING] public static $Velocity velocity = new $Velocity();\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:690: warning: no comment\n" +
            "[WARNING] public static Encryption AES(String key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:204: warning: no comment\n" +
            "[WARNING] public static $Result ajax(Ajax ajax)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:180: warning: no comment\n" +
            "[WARNING] public static $Result ajax(String url)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:456: warning: no comment\n" +
            "[WARNING] public static void async(boolean daemon, Runnable... r){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:451: warning: no comment\n" +
            "[WARNING] public static void async(Runnable... r){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:653: warning: no comment\n" +
            "[WARNING] public static String camel2under(String c){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:502: warning: no comment\n" +
            "[WARNING] public static String concat(Object... str){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:492: warning: no comment\n" +
            "[WARNING] public static String convert(double num) {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:496: warning: no comment\n" +
            "[WARNING] public static String convert(double num, String u) {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:351: warning: no comment\n" +
            "[WARNING] public static void copyFile(String sourcePath, String destPath)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:355: warning: no comment\n" +
            "[WARNING] public static void copyFile(String sourcePath, String destPath, boolean isReplace)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:601: warning: no comment\n" +
            "[WARNING] public static String debug(Object... o){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:664: warning: no comment\n" +
            "[WARNING] public static String decompilerClass(String sourcePath, String targetPath){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:661: warning: no comment\n" +
            "[WARNING] public static String decompilerJar(String sourcePath, String targetPath){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:345: warning: no comment\n" +
            "[WARNING] public static $Result delete(File... files)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:337: warning: no comment\n" +
            "[WARNING] public static $Result delete(String... paths)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:341: warning: no comment\n" +
            "[WARNING] public static $Result deleteFile(File... files)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:333: warning: no comment\n" +
            "[WARNING] public static $Result deleteFile(String... paths)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:695: warning: no comment\n" +
            "[WARNING] public static boolean download(String url, String targetPath){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:609: warning: no comment\n" +
            "[WARNING] public static String error(Object... o){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:605: warning: no comment\n" +
            "[WARNING] public static Exception exception(Exception... e){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:526: warning: no comment\n" +
            "[WARNING] public static String first(String... strs) {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:530: warning: no comment\n" +
            "[WARNING] public static String firstDef(String def, String... strs) {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:437: warning: no comment\n" +
            "[WARNING] public static String format(String patten, Date... d){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:441: warning: no comment\n" +
            "[WARNING] public static String format(Date... d){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:184: warning: no comment\n" +
            "[WARNING] public static $Result get(Ajax ajax)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:154: warning: no comment\n" +
            "[WARNING] public static $Result get(String url)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:160: warning: no comment\n" +
            "[WARNING] public static $Result get(String url, boolean isClearSession)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:248: warning: no comment\n" +
            "[WARNING] public static Cookie[] getCookie(HttpServletRequest request){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:252: warning: no comment\n" +
            "[WARNING] public static String getCookie(HttpServletRequest request, String key){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:375: warning: no comment\n" +
            "[WARNING] public static File getFile(String path){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:379: warning: no comment\n" +
            "[WARNING] public static List<File> getFiles(String... path){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:216: warning: no comment\n" +
            "[WARNING] public static Map<String, Host> getHosts() {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:220: warning: no comment\n" +
            "[WARNING] public static Map<String, Host> getHostsByName() {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:224: warning: no comment\n" +
            "[WARNING] public static Map<String, Host> getHostsIp() {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:232: warning: no comment\n" +
            "[WARNING] public static InetAddress getInetAddress(String... name) throws UnknownHostException {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:228: warning: no comment\n" +
            "[WARNING] public static String getIp(String... name) throws UnknownHostException {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:192: warning: no comment\n" +
            "[WARNING] public static JSONObject getJSON(Ajax ajax)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:168: warning: no comment\n" +
            "[WARNING] public static JSONObject getJSON(String url)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:200: warning: no comment\n" +
            "[WARNING] public static JSONArray getJSONArray(Ajax ajax)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:176: warning: no comment\n" +
            "[WARNING] public static JSONArray getJSONArray(String url)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:196: warning: no comment\n" +
            "[WARNING] public static JSONObject getJSONObject(Ajax ajax)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:172: warning: no comment\n" +
            "[WARNING] public static JSONObject getJSONObject(String url)  {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:280: warning: no comment\n" +
            "[WARNING] public static String getParameterStr(HttpServletRequest request){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:276: warning: no comment\n" +
            "[WARNING] public static String getParameterStr(Object... objects){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:272: warning: no comment\n" +
            "[WARNING] public static String getParameterStr(Map<String, String[]>... map){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:391: warning: no comment\n" +
            "[WARNING] public static String getPath(){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:395: warning: no comment\n" +
            "[WARNING] public static String getPath(String p){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:399: warning: no comment\n" +
            "[WARNING] public static String getPath(String... ps){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:419: warning: no comment\n" +
            "[WARNING] public String getRelativePath(String _path){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:244: warning: no comment\n" +
            "[WARNING] public static Object getRequestBody(HttpServletRequest request) {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:403: warning: no comment\n" +
            "[WARNING] public static String getRootPath(){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:407: warning: no comment\n" +
            "[WARNING] public static String getWebRootPath(){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:411: warning: no comment\n" +
            "[WARNING] public static String getWebRootPath(Class clazz){\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:556: warning: no comment\n" +
            "[WARNING] public static List<Integer> indexOfList(String r, String str) {\n" +
            "[WARNING] ^\n" +
            "[WARNING] D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\java\\com\\ailbb\\ajj\\$.java:629: warning: no comment\n" +
            "[WARNING] public static String info(Object... o){\n" +
            "[WARNING] ^\n" +
            "[WARNING] 100 warnings\n" +
            "[INFO] Building jar: D:\\Code\\AILBB\\ATOOLS\\ajj\\target\\ajj-1.14-javadoc.jar\n" +
            "[INFO] \n" +
            "[INFO] --- maven-gpg-plugin:3.1.0:sign (sign-artifacts) @ ajj ---\n" +
            "[INFO] Signing 4 files with default secret key.\n" +
            "[INFO] \n" +
            "[INFO] --- maven-install-plugin:2.4:install (default-install) @ ajj ---\n" +
            "[INFO] Installing D:\\Code\\AILBB\\ATOOLS\\ajj\\target\\ajj-1.14.jar to C:\\Users\\demo\\.m2\\repository\\com\\ailbb\\ajj\\1.14\\ajj-1.14.jar\n" +
            "[INFO] Installing D:\\Code\\AILBB\\ATOOLS\\ajj\\pom.xml to C:\\Users\\demo\\.m2\\repository\\com\\ailbb\\ajj\\1.14\\ajj-1.14.pom\n" +
            "[INFO] Installing D:\\Code\\AILBB\\ATOOLS\\ajj\\target\\ajj-1.14-sources.jar to C:\\Users\\demo\\.m2\\repository\\com\\ailbb\\ajj\\1.14\\ajj-1.14-sources.jar\n" +
            "[INFO] Installing D:\\Code\\AILBB\\ATOOLS\\ajj\\target\\ajj-1.14-javadoc.jar to C:\\Users\\demo\\.m2\\repository\\com\\ailbb\\ajj\\1.14\\ajj-1.14-javadoc.jar\n" +
            "[INFO] Installing D:\\Code\\AILBB\\ATOOLS\\ajj\\target\\ajj-1.14.jar.asc to C:\\Users\\demo\\.m2\\repository\\com\\ailbb\\ajj\\1.14\\ajj-1.14.jar.asc\n" +
            "[INFO] Installing D:\\Code\\AILBB\\ATOOLS\\ajj\\target\\ajj-1.14.pom.asc to C:\\Users\\demo\\.m2\\repository\\com\\ailbb\\ajj\\1.14\\ajj-1.14.pom.asc\n" +
            "[INFO] Installing D:\\Code\\AILBB\\ATOOLS\\ajj\\target\\ajj-1.14-sources.jar.asc to C:\\Users\\demo\\.m2\\repository\\com\\ailbb\\ajj\\1.14\\ajj-1.14-sources.jar.asc\n" +
            "[INFO] Installing D:\\Code\\AILBB\\ATOOLS\\ajj\\target\\ajj-1.14-javadoc.jar.asc to C:\\Users\\demo\\.m2\\repository\\com\\ailbb\\ajj\\1.14\\ajj-1.14-javadoc.jar.asc\n" +
            "[INFO] ------------------------------------------------------------------------\n" +
            "[INFO] BUILD SUCCESS\n" +
            "[INFO] ------------------------------------------------------------------------\n" +
            "[INFO] Total time:  13.094 s\n" +
            "[INFO] Finished at: 2023-09-13T17:30:56+08:00\n" +
            "[INFO] ------------------------------------------------------------------------\n" +
            "\n" +
            "Process finished with exit code 0\n";

    static String demoLog2="\"C:\\Program Files\\Java\\jdk-17.0.3.1\\bin\\java.exe\" -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:12345,suspend=y,server=n -XX:TieredStopAtLevel=1 -Dspring.output.ansi.enabled=always -Dcom.sun.management.jmxremote -Dspring.jmx.enabled=true -Dspring.liveBeansView.mbeanDomain -Dspring.application.admin.enabled=true -javaagent:C:\\Users\\demo\\AppData\\Local\\JetBrains\\IntelliJIdea2022.2\\captureAgent\\debugger-agent.jar=file:/C:/Users/demo/AppData/Local/Temp/capture.props -Dfile.encoding=UTF-8 -classpath \"D:\\Demo\\Project\\demo-server\\abm-server\\target\\classes;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot-starter-web\\3.1.2\\spring-boot-starter-web-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot-starter\\3.1.2\\spring-boot-starter-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot\\3.1.2\\spring-boot-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot-starter-logging\\3.1.2\\spring-boot-starter-logging-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\ch\\qos\\logback\\logback-classic\\1.4.8\\logback-classic-1.4.8.jar;C:\\Users\\demo\\.m2\\repository\\ch\\qos\\logback\\logback-core\\1.4.8\\logback-core-1.4.8.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\logging\\log4j\\log4j-to-slf4j\\2.20.0\\log4j-to-slf4j-2.20.0.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\logging\\log4j\\log4j-api\\2.20.0\\log4j-api-2.20.0.jar;C:\\Users\\demo\\.m2\\repository\\org\\slf4j\\jul-to-slf4j\\2.0.7\\jul-to-slf4j-2.0.7.jar;C:\\Users\\demo\\.m2\\repository\\jakarta\\annotation\\jakarta.annotation-api\\2.1.1\\jakarta.annotation-api-2.1.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\spring-core\\6.0.11\\spring-core-6.0.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\spring-jcl\\6.0.11\\spring-jcl-6.0.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot-starter-json\\3.1.2\\spring-boot-starter-json-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\jackson\\datatype\\jackson-datatype-jdk8\\2.15.2\\jackson-datatype-jdk8-2.15.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\jackson\\datatype\\jackson-datatype-jsr310\\2.15.2\\jackson-datatype-jsr310-2.15.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\jackson\\module\\jackson-module-parameter-names\\2.15.2\\jackson-module-parameter-names-2.15.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot-starter-tomcat\\3.1.2\\spring-boot-starter-tomcat-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\tomcat\\embed\\tomcat-embed-core\\10.1.11\\tomcat-embed-core-10.1.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\tomcat\\embed\\tomcat-embed-el\\10.1.11\\tomcat-embed-el-10.1.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\tomcat\\embed\\tomcat-embed-websocket\\10.1.11\\tomcat-embed-websocket-10.1.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\spring-web\\6.0.11\\spring-web-6.0.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\spring-beans\\6.0.11\\spring-beans-6.0.11.jar;C:\\Users\\demo\\.m2\\repository\\io\\micrometer\\micrometer-observation\\1.11.2\\micrometer-observation-1.11.2.jar;C:\\Users\\demo\\.m2\\repository\\io\\micrometer\\micrometer-commons\\1.11.2\\micrometer-commons-1.11.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\spring-webmvc\\6.0.11\\spring-webmvc-6.0.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\spring-aop\\6.0.11\\spring-aop-6.0.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\spring-context\\6.0.11\\spring-context-6.0.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\spring-expression\\6.0.11\\spring-expression-6.0.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot-starter-webflux\\3.1.2\\spring-boot-starter-webflux-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot-starter-reactor-netty\\3.1.2\\spring-boot-starter-reactor-netty-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\io\\projectreactor\\netty\\reactor-netty-http\\1.1.9\\reactor-netty-http-1.1.9.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-codec-http\\4.1.94.Final\\netty-codec-http-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-codec\\4.1.94.Final\\netty-codec-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-handler\\4.1.94.Final\\netty-handler-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-codec-http2\\4.1.94.Final\\netty-codec-http2-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-resolver-dns\\4.1.94.Final\\netty-resolver-dns-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-resolver\\4.1.94.Final\\netty-resolver-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-codec-dns\\4.1.94.Final\\netty-codec-dns-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-resolver-dns-native-macos\\4.1.94.Final\\netty-resolver-dns-native-macos-4.1.94.Final-osx-x86_64.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-resolver-dns-classes-macos\\4.1.94.Final\\netty-resolver-dns-classes-macos-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-transport-native-epoll\\4.1.94.Final\\netty-transport-native-epoll-4.1.94.Final-linux-x86_64.jar;C:\\Users\\demo\\.m2\\repository\\io\\projectreactor\\netty\\reactor-netty-core\\1.1.9\\reactor-netty-core-1.1.9.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-handler-proxy\\4.1.94.Final\\netty-handler-proxy-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\spring-webflux\\6.0.11\\spring-webflux-6.0.11.jar;C:\\Users\\demo\\.m2\\repository\\io\\projectreactor\\reactor-core\\3.5.8\\reactor-core-3.5.8.jar;C:\\Users\\demo\\.m2\\repository\\org\\reactivestreams\\reactive-streams\\1.0.4\\reactive-streams-1.0.4.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot-starter-thymeleaf\\3.1.2\\spring-boot-starter-thymeleaf-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\thymeleaf\\thymeleaf-spring6\\3.1.1.RELEASE\\thymeleaf-spring6-3.1.1.RELEASE.jar;C:\\Users\\demo\\.m2\\repository\\org\\thymeleaf\\thymeleaf\\3.1.1.RELEASE\\thymeleaf-3.1.1.RELEASE.jar;C:\\Users\\demo\\.m2\\repository\\org\\attoparser\\attoparser\\2.0.6.RELEASE\\attoparser-2.0.6.RELEASE.jar;C:\\Users\\demo\\.m2\\repository\\org\\unbescape\\unbescape\\1.1.6.RELEASE\\unbescape-1.1.6.RELEASE.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\jackson\\core\\jackson-core\\2.15.2\\jackson-core-2.15.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\jackson\\core\\jackson-annotations\\2.15.2\\jackson-annotations-2.15.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\jackson\\core\\jackson-databind\\2.15.2\\jackson-databind-2.15.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\jackson\\module\\jackson-module-jaxb-annotations\\2.15.2\\jackson-module-jaxb-annotations-2.15.2.jar;C:\\Users\\demo\\.m2\\repository\\jakarta\\xml\\bind\\jakarta.xml.bind-api\\4.0.0\\jakarta.xml.bind-api-4.0.0.jar;C:\\Users\\demo\\.m2\\repository\\jakarta\\activation\\jakarta.activation-api\\2.1.2\\jakarta.activation-api-2.1.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\jackson\\module\\jackson-module-mrbean\\2.15.2\\jackson-module-mrbean-2.15.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\jackson\\datatype\\jackson-datatype-joda\\2.15.2\\jackson-datatype-joda-2.15.2.jar;C:\\Users\\demo\\.m2\\repository\\joda-time\\joda-time\\2.1\\joda-time-2.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\projectlombok\\lombok\\1.18.28\\lombok-1.18.28.jar;C:\\Users\\demo\\.m2\\repository\\cn\\hutool\\hutool-all\\4.4.5\\hutool-all-4.4.5.jar;C:\\Users\\demo\\.m2\\repository\\commons-lang\\commons-lang\\2.6\\commons-lang-2.6.jar;C:\\Users\\demo\\.m2\\repository\\mysql\\mysql-connector-java\\5.1.46\\mysql-connector-java-5.1.46.jar;C:\\Users\\demo\\.m2\\repository\\com\\alibaba\\easyexcel\\2.1.7\\easyexcel-2.1.7.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\poi\\poi\\3.17\\poi-3.17.jar;C:\\Users\\demo\\.m2\\repository\\commons-codec\\commons-codec\\1.8\\commons-codec-1.8.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\commons\\commons-collections4\\4.1\\commons-collections4-4.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\poi\\poi-ooxml\\3.17\\poi-ooxml-3.17.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\poi\\poi-ooxml-schemas\\3.17\\poi-ooxml-schemas-3.17.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\xmlbeans\\xmlbeans\\2.6.0\\xmlbeans-2.6.0.jar;C:\\Users\\demo\\.m2\\repository\\stax\\stax-api\\1.0.1\\stax-api-1.0.1.jar;C:\\Users\\demo\\.m2\\repository\\com\\github\\virtuald\\curvesapi\\1.04\\curvesapi-1.04.jar;C:\\Users\\demo\\.m2\\repository\\cglib\\cglib\\2.2\\cglib-2.2.jar;C:\\Users\\demo\\.m2\\repository\\asm\\asm\\3.1\\asm-3.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\slf4j\\slf4j-api\\2.0.7\\slf4j-api-2.0.7.jar;C:\\Users\\demo\\.m2\\repository\\org\\ehcache\\ehcache\\3.7.1\\ehcache-3.7.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\glassfish\\jaxb\\jaxb-runtime\\4.0.3\\jaxb-runtime-4.0.3.jar;C:\\Users\\demo\\.m2\\repository\\org\\glassfish\\jaxb\\jaxb-core\\4.0.3\\jaxb-core-4.0.3.jar;C:\\Users\\demo\\.m2\\repository\\org\\eclipse\\angus\\angus-activation\\2.0.1\\angus-activation-2.0.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\glassfish\\jaxb\\txw2\\4.0.3\\txw2-4.0.3.jar;C:\\Users\\demo\\.m2\\repository\\com\\sun\\istack\\istack-commons-runtime\\4.1.2\\istack-commons-runtime-4.1.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\cronutils\\cron-utils\\9.1.3\\cron-utils-9.1.3.jar;C:\\Users\\demo\\.m2\\repository\\org\\glassfish\\javax.el\\3.0.0\\javax.el-3.0.0.jar;C:\\Users\\demo\\.m2\\repository\\net\\jodah\\expiringmap\\0.5.8\\expiringmap-0.5.8.jar;C:\\Users\\demo\\.m2\\repository\\com\\jcraft\\jsch\\0.1.55\\jsch-0.1.55.jar;C:\\Users\\demo\\.m2\\repository\\com\\googlecode\\aviator\\aviator\\5.2.4\\aviator-5.2.4.jar;C:\\Users\\demo\\.m2\\repository\\com\\github\\pagehelper\\pagehelper\\4.1.0\\pagehelper-4.1.0.jar;C:\\Users\\demo\\.m2\\repository\\com\\github\\jsqlparser\\jsqlparser\\0.9.4\\jsqlparser-0.9.4.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\commons\\commons-lang3\\3.12.0\\commons-lang3-3.12.0.jar;C:\\Users\\demo\\.m2\\repository\\com\\baomidou\\mybatis-plus-boot-starter\\3.5.3.1\\mybatis-plus-boot-starter-3.5.3.1.jar;C:\\Users\\demo\\.m2\\repository\\com\\baomidou\\mybatis-plus\\3.5.3.1\\mybatis-plus-3.5.3.1.jar;C:\\Users\\demo\\.m2\\repository\\com\\baomidou\\mybatis-plus-extension\\3.5.3.1\\mybatis-plus-extension-3.5.3.1.jar;C:\\Users\\demo\\.m2\\repository\\com\\baomidou\\mybatis-plus-core\\3.5.3.1\\mybatis-plus-core-3.5.3.1.jar;C:\\Users\\demo\\.m2\\repository\\com\\baomidou\\mybatis-plus-annotation\\3.5.3.1\\mybatis-plus-annotation-3.5.3.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\mybatis\\mybatis-spring\\2.0.7\\mybatis-spring-2.0.7.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot-autoconfigure\\3.1.2\\spring-boot-autoconfigure-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot-starter-jdbc\\3.1.2\\spring-boot-starter-jdbc-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\zaxxer\\HikariCP\\5.0.1\\HikariCP-5.0.1.jar;C:\\Users\\demo\\.m2\\repository\\net\\sourceforge\\htmlunit\\htmlunit\\2.34.0\\htmlunit-2.34.0.jar;C:\\Users\\demo\\.m2\\repository\\xalan\\xalan\\2.7.2\\xalan-2.7.2.jar;C:\\Users\\demo\\.m2\\repository\\xalan\\serializer\\2.7.2\\serializer-2.7.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\commons\\commons-text\\1.6\\commons-text-1.6.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\httpcomponents\\httpmime\\4.5.7\\httpmime-4.5.7.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\httpcomponents\\httpclient\\4.5.7\\httpclient-4.5.7.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\httpcomponents\\httpcore\\4.4.16\\httpcore-4.4.16.jar;C:\\Users\\demo\\.m2\\repository\\net\\sourceforge\\htmlunit\\htmlunit-core-js\\2.34.0\\htmlunit-core-js-2.34.0.jar;C:\\Users\\demo\\.m2\\repository\\net\\sourceforge\\htmlunit\\neko-htmlunit\\2.34.0\\neko-htmlunit-2.34.0.jar;C:\\Users\\demo\\.m2\\repository\\xerces\\xercesImpl\\2.12.0\\xercesImpl-2.12.0.jar;C:\\Users\\demo\\.m2\\repository\\xml-apis\\xml-apis\\1.4.01\\xml-apis-1.4.01.jar;C:\\Users\\demo\\.m2\\repository\\net\\sourceforge\\htmlunit\\htmlunit-cssparser\\1.3.0\\htmlunit-cssparser-1.3.0.jar;C:\\Users\\demo\\.m2\\repository\\commons-io\\commons-io\\2.4\\commons-io-2.4.jar;C:\\Users\\demo\\.m2\\repository\\commons-logging\\commons-logging\\1.2\\commons-logging-1.2.jar;C:\\Users\\demo\\.m2\\repository\\commons-net\\commons-net\\3.6\\commons-net-3.6.jar;C:\\Users\\demo\\.m2\\repository\\org\\eclipse\\jetty\\websocket\\websocket-client\\9.4.15.v20190215\\websocket-client-9.4.15.v20190215.jar;C:\\Users\\demo\\.m2\\repository\\org\\eclipse\\jetty\\jetty-client\\11.0.15\\jetty-client-11.0.15.jar;C:\\Users\\demo\\.m2\\repository\\org\\eclipse\\jetty\\jetty-http\\11.0.15\\jetty-http-11.0.15.jar;C:\\Users\\demo\\.m2\\repository\\org\\eclipse\\jetty\\jetty-alpn-client\\11.0.15\\jetty-alpn-client-11.0.15.jar;C:\\Users\\demo\\.m2\\repository\\org\\eclipse\\jetty\\jetty-xml\\11.0.15\\jetty-xml-11.0.15.jar;C:\\Users\\demo\\.m2\\repository\\org\\eclipse\\jetty\\jetty-util\\11.0.15\\jetty-util-11.0.15.jar;C:\\Users\\demo\\.m2\\repository\\org\\eclipse\\jetty\\jetty-io\\11.0.15\\jetty-io-11.0.15.jar;C:\\Users\\demo\\.m2\\repository\\org\\eclipse\\jetty\\websocket\\websocket-common\\9.4.15.v20190215\\websocket-common-9.4.15.v20190215.jar;C:\\Users\\demo\\.m2\\repository\\org\\eclipse\\jetty\\websocket\\websocket-api\\9.4.15.v20190215\\websocket-api-9.4.15.v20190215.jar;C:\\Users\\demo\\.m2\\repository\\org\\jsoup\\jsoup\\1.12.1\\jsoup-1.12.1.jar;C:\\Users\\demo\\.m2\\repository\\net\\coobird\\thumbnailator\\0.4.8\\thumbnailator-0.4.8.jar;C:\\Users\\demo\\.m2\\repository\\ch\\ethz\\ganymed\\ganymed-ssh2\\262\\ganymed-ssh2-262.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\commons\\commons-email\\1.5\\commons-email-1.5.jar;C:\\Users\\demo\\.m2\\repository\\com\\sun\\mail\\javax.mail\\1.5.6\\javax.mail-1.5.6.jar;C:\\Users\\demo\\.m2\\repository\\javax\\activation\\activation\\1.1\\activation-1.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\postgresql\\postgresql\\42.3.2\\postgresql-42.3.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\checkerframework\\checker-qual\\3.5.0\\checker-qual-3.5.0.jar;D:\\Demo\\Project\\demo-server\\abm-parcel\\abm-parcel-infra\\target\\classes;D:\\Demo\\Project\\demo-server\\abm-parcel\\abm-parcel-spec\\target\\classes;C:\\Users\\demo\\.m2\\repository\\com\\google\\guava\\guava\\14.0\\guava-14.0.jar;C:\\Users\\demo\\.m2\\repository\\com\\google\\code\\findbugs\\jsr305\\2.0.1\\jsr305-2.0.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\boot\\spring-boot-starter-validation\\3.1.2\\spring-boot-starter-validation-3.1.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\hibernate\\validator\\hibernate-validator\\8.0.1.Final\\hibernate-validator-8.0.1.Final.jar;C:\\Users\\demo\\.m2\\repository\\org\\jboss\\logging\\jboss-logging\\3.5.3.Final\\jboss-logging-3.5.3.Final.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\classmate\\1.5.1\\classmate-1.5.1.jar;C:\\Users\\demo\\.m2\\repository\\commons-beanutils\\commons-beanutils\\1.8.3\\commons-beanutils-1.8.3.jar;C:\\Users\\demo\\.m2\\repository\\commons-cli\\commons-cli\\1.5.0\\commons-cli-1.5.0.jar;C:\\Users\\demo\\.m2\\repository\\commons-configuration\\commons-configuration\\1.9\\commons-configuration-1.9.jar;C:\\Users\\demo\\.m2\\repository\\log4j\\log4j\\1.2.16\\log4j-1.2.16.jar;C:\\Users\\demo\\.m2\\repository\\org\\slf4j\\slf4j-reload4j\\2.0.7\\slf4j-reload4j-2.0.7.jar;C:\\Users\\demo\\.m2\\repository\\ch\\qos\\reload4j\\reload4j\\1.2.22\\reload4j-1.2.22.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\commons\\commons-compress\\1.7\\commons-compress-1.7.jar;C:\\Users\\demo\\.m2\\repository\\org\\tukaani\\xz\\1.4\\xz-1.4.jar;D:\\Demo\\Project\\demo-server\\abm-parcel\\abm-parcel-validator\\target\\classes;D:\\Demo\\Project\\demo-server\\abm-udo-management\\target\\classes;C:\\Users\\demo\\.m2\\repository\\org\\springdoc\\springdoc-openapi-starter-webmvc-ui\\2.2.0\\springdoc-openapi-starter-webmvc-ui-2.2.0.jar;C:\\Users\\demo\\.m2\\repository\\org\\springdoc\\springdoc-openapi-starter-webmvc-api\\2.2.0\\springdoc-openapi-starter-webmvc-api-2.2.0.jar;C:\\Users\\demo\\.m2\\repository\\org\\springdoc\\springdoc-openapi-starter-common\\2.2.0\\springdoc-openapi-starter-common-2.2.0.jar;C:\\Users\\demo\\.m2\\repository\\io\\swagger\\core\\v3\\swagger-core-jakarta\\2.2.15\\swagger-core-jakarta-2.2.15.jar;C:\\Users\\demo\\.m2\\repository\\io\\swagger\\core\\v3\\swagger-annotations-jakarta\\2.2.15\\swagger-annotations-jakarta-2.2.15.jar;C:\\Users\\demo\\.m2\\repository\\io\\swagger\\core\\v3\\swagger-models-jakarta\\2.2.15\\swagger-models-jakarta-2.2.15.jar;C:\\Users\\demo\\.m2\\repository\\org\\webjars\\swagger-ui\\5.2.0\\swagger-ui-5.2.0.jar;D:\\Demo\\Project\\demo-server\\abm-csd\\abm-csd-runner\\target\\classes;D:\\Demo\\Project\\demo-server\\abm-csd\\abm-csd-projection\\target\\classes;D:\\Demo\\Project\\demo-server\\abm-csd\\abm-csd-configwriter\\target\\classes;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\jackson\\dataformat\\jackson-dataformat-xml\\2.15.2\\jackson-dataformat-xml-2.15.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\codehaus\\woodstox\\stax2-api\\4.2.1\\stax2-api-4.2.1.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\woodstox\\woodstox-core\\6.5.1\\woodstox-core-6.5.1.jar;C:\\Users\\demo\\.m2\\repository\\com\\hubspot\\jinjava\\jinjava\\2.7.1\\jinjava-2.7.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\javassist\\javassist\\3.24.1-GA\\javassist-3.24.1-GA.jar;C:\\Users\\demo\\.m2\\repository\\com\\google\\re2j\\re2j\\1.2\\re2j-1.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\googlecode\\java-ipv6\\java-ipv6\\0.17\\java-ipv6-0.17.jar;C:\\Users\\demo\\.m2\\repository\\com\\google\\code\\findbugs\\annotations\\3.0.1\\annotations-3.0.1.jar;C:\\Users\\demo\\.m2\\repository\\com\\fasterxml\\jackson\\dataformat\\jackson-dataformat-yaml\\2.15.2\\jackson-dataformat-yaml-2.15.2.jar;C:\\Users\\demo\\.m2\\repository\\ch\\obermuhlner\\big-math\\2.0.0\\big-math-2.0.0.jar;D:\\Demo\\Project\\demo-server\\abm-agent\\abm-agent-api\\target\\classes;C:\\Users\\demo\\.m2\\repository\\jakarta\\validation\\jakarta.validation-api\\3.0.2\\jakarta.validation-api-3.0.2.jar;D:\\Demo\\Project\\demo-server\\abm-csd\\abm-csd-commons\\target\\classes;C:\\Users\\demo\\.m2\\repository\\com\\ailbb\\ajj\\1.14\\ajj-1.14.jar;C:\\Users\\demo\\.m2\\repository\\net\\sf\\json-lib\\json-lib\\2.4\\json-lib-2.4-jdk15.jar;C:\\Users\\demo\\.m2\\repository\\commons-collections\\commons-collections\\3.2.1\\commons-collections-3.2.1.jar;C:\\Users\\demo\\.m2\\repository\\net\\sf\\ezmorph\\ezmorph\\1.0.6\\ezmorph-1.0.6.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\velocity\\velocity-engine-core\\2.3\\velocity-engine-core-2.3.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\velocity\\tools\\velocity-tools-generic\\3.1\\velocity-tools-generic-3.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\commons\\commons-digester3\\3.2\\commons-digester3-3.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\github\\cliftonlabs\\json-simple\\3.0.2\\json-simple-3.0.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\velocity\\tools\\velocity-tools-view\\3.1\\velocity-tools-view-3.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\velocity\\tools\\velocity-tools-view-jsp\\3.0\\velocity-tools-view-jsp-3.0.jar;C:\\Users\\demo\\.m2\\repository\\jakarta\\mail\\jakarta.mail-api\\2.1.2\\jakarta.mail-api-2.1.2.jar;C:\\Users\\demo\\.m2\\repository\\com\\mysql\\mysql-connector-j\\8.0.33\\mysql-connector-j-8.0.33.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\commons\\commons-fileupload2-core\\2.0.0-M1\\commons-fileupload2-core-2.0.0-M1.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\commons\\commons-fileupload2-jakarta\\2.0.0-M1\\commons-fileupload2-jakarta-2.0.0-M1.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\spring-jdbc\\6.0.11\\spring-jdbc-6.0.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\spring-tx\\6.0.11\\spring-tx-6.0.11.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\security\\spring-security-core\\6.1.2\\spring-security-core-6.1.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\springframework\\security\\spring-security-crypto\\6.1.2\\spring-security-crypto-6.1.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\seleniumhq\\selenium\\selenium-firefox-driver\\4.8.3\\selenium-firefox-driver-4.8.3.jar;C:\\Users\\demo\\.m2\\repository\\com\\google\\auto\\service\\auto-service-annotations\\1.0.1\\auto-service-annotations-1.0.1.jar;C:\\Users\\demo\\.m2\\repository\\com\\google\\auto\\service\\auto-service\\1.0.1\\auto-service-1.0.1.jar;C:\\Users\\demo\\.m2\\repository\\com\\google\\auto\\auto-common\\1.2\\auto-common-1.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\seleniumhq\\selenium\\selenium-api\\4.8.3\\selenium-api-4.8.3.jar;C:\\Users\\demo\\.m2\\repository\\org\\seleniumhq\\selenium\\selenium-devtools-v85\\4.8.3\\selenium-devtools-v85-4.8.3.jar;C:\\Users\\demo\\.m2\\repository\\org\\seleniumhq\\selenium\\selenium-http\\4.8.3\\selenium-http-4.8.3.jar;C:\\Users\\demo\\.m2\\repository\\dev\\failsafe\\failsafe\\3.3.1\\failsafe-3.3.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\seleniumhq\\selenium\\selenium-json\\4.8.3\\selenium-json-4.8.3.jar;C:\\Users\\demo\\.m2\\repository\\org\\seleniumhq\\selenium\\selenium-manager\\4.8.3\\selenium-manager-4.8.3.jar;C:\\Users\\demo\\.m2\\repository\\org\\seleniumhq\\selenium\\selenium-remote-driver\\4.8.3\\selenium-remote-driver-4.8.3.jar;C:\\Users\\demo\\.m2\\repository\\com\\beust\\jcommander\\1.82\\jcommander-1.82.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-buffer\\4.1.94.Final\\netty-buffer-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-common\\4.1.94.Final\\netty-common-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-transport-classes-epoll\\4.1.94.Final\\netty-transport-classes-epoll-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-transport-classes-kqueue\\4.1.94.Final\\netty-transport-classes-kqueue-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-transport-native-epoll\\4.1.94.Final\\netty-transport-native-epoll-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-transport-native-kqueue\\4.1.94.Final\\netty-transport-native-kqueue-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-transport-native-unix-common\\4.1.94.Final\\netty-transport-native-unix-common-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-transport\\4.1.94.Final\\netty-transport-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-api\\1.25.0\\opentelemetry-api-1.25.0.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-context\\1.25.0\\opentelemetry-context-1.25.0.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-exporter-logging\\1.25.0\\opentelemetry-exporter-logging-1.25.0.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-sdk-metrics\\1.25.0\\opentelemetry-sdk-metrics-1.25.0.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-sdk-logs\\1.25.0-alpha\\opentelemetry-sdk-logs-1.25.0-alpha.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-api-logs\\1.25.0-alpha\\opentelemetry-api-logs-1.25.0-alpha.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-api-events\\1.25.0-alpha\\opentelemetry-api-events-1.25.0-alpha.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-sdk-common\\1.25.0\\opentelemetry-sdk-common-1.25.0.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-sdk-extension-autoconfigure-spi\\1.25.0\\opentelemetry-sdk-extension-autoconfigure-spi-1.25.0.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-sdk-extension-autoconfigure\\1.24.0-alpha\\opentelemetry-sdk-extension-autoconfigure-1.24.0-alpha.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-sdk-trace\\1.25.0\\opentelemetry-sdk-trace-1.25.0.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-sdk\\1.25.0\\opentelemetry-sdk-1.25.0.jar;C:\\Users\\demo\\.m2\\repository\\io\\opentelemetry\\opentelemetry-semconv\\1.24.0-alpha\\opentelemetry-semconv-1.24.0-alpha.jar;C:\\Users\\demo\\.m2\\repository\\io\\ous\\jtoml\\2.0.0\\jtoml-2.0.0.jar;C:\\Users\\demo\\.m2\\repository\\net\\bytebuddy\\byte-buddy\\1.14.5\\byte-buddy-1.14.5.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\commons\\commons-exec\\1.3\\commons-exec-1.3.jar;C:\\Users\\demo\\.m2\\repository\\org\\asynchttpclient\\async-http-client\\2.12.3\\async-http-client-2.12.3.jar;C:\\Users\\demo\\.m2\\repository\\org\\asynchttpclient\\async-http-client-netty-utils\\2.12.3\\async-http-client-netty-utils-2.12.3.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-codec-socks\\4.1.94.Final\\netty-codec-socks-4.1.94.Final.jar;C:\\Users\\demo\\.m2\\repository\\io\\netty\\netty-transport-native-kqueue\\4.1.94.Final\\netty-transport-native-kqueue-4.1.94.Final-osx-x86_64.jar;C:\\Users\\demo\\.m2\\repository\\com\\typesafe\\netty\\netty-reactive-streams\\2.0.4\\netty-reactive-streams-2.0.4.jar;C:\\Users\\demo\\.m2\\repository\\com\\sun\\activation\\jakarta.activation\\1.2.2\\jakarta.activation-1.2.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\yaml\\snakeyaml\\1.33\\snakeyaml-1.33.jar;C:\\Users\\demo\\.m2\\repository\\org\\jdom\\jdom2\\2.0.6.1\\jdom2-2.0.6.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\httpcomponents\\client5\\httpclient5\\5.2.1\\httpclient5-5.2.1.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\httpcomponents\\core5\\httpcore5\\5.2.2\\httpcore5-5.2.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\apache\\httpcomponents\\core5\\httpcore5-h2\\5.2.2\\httpcore5-h2-5.2.2.jar;C:\\Users\\demo\\.m2\\repository\\org\\bouncycastle\\bcprov-jdk15on\\1.70\\bcprov-jdk15on-1.70.jar;C:\\Users\\demo\\.m2\\repository\\org\\mybatis\\mybatis\\3.5.13\\mybatis-3.5.13.jar;C:\\Users\\demo\\.m2\\repository\\com\\github\\ulisesbocchio\\jasypt-spring-boot-starter\\3.0.5\\jasypt-spring-boot-starter-3.0.5.jar;C:\\Users\\demo\\.m2\\repository\\com\\github\\ulisesbocchio\\jasypt-spring-boot\\3.0.5\\jasypt-spring-boot-3.0.5.jar;C:\\Users\\demo\\.m2\\repository\\org\\jasypt\\jasypt\\1.9.3\\jasypt-1.9.3.jar;C:\\Program Files\\JetBrains\\IntelliJ IDEA 2022.2.3\\lib\\idea_rt.jar\" com.abm.AbmServiceApplication\n" +
            "Connected to the target VM, address: '127.0.0.1:12345', transport: 'socket'\n" +
            "SLF4J: Class path contains multiple SLF4J providers.\n" +
            "SLF4J: Found provider [ch.qos.logback.classic.spi.LogbackServiceProvider@3901d134]\n" +
            "SLF4J: Found provider [org.slf4j.reload4j.Reload4jServiceProvider@14d3bc22]\n" +
            "SLF4J: See https://www.slf4j.org/codes.html#multiple_bindings for an explanation.\n" +
            "SLF4J: Actual provider is of type [ch.qos.logback.classic.spi.LogbackServiceProvider@3901d134]\n" +
            "17:32:09,672 |-INFO in ch.qos.logback.classic.LoggerContext[default] - This is logback-classic version 1.4.8\n" +
            "17:32:09,740 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback-test.xml]\n" +
            "17:32:09,741 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback.xml]\n" +
            "17:32:09,744 |-INFO in ch.qos.logback.classic.BasicConfigurator@31c269fd - Setting up default configuration.\n" +
            "17:32:10,438 |-INFO in ch.qos.logback.core.joran.util.ConfigurationWatchListUtil@372b0d86 - Adding [jar:file:/C:/Users/demo/.m2/repository/org/springframework/boot/spring-boot/3.1.2/spring-boot-3.1.2.jar!/org/springframework/boot/logging/logback/defaults.xml] to configuration watch list.\n" +
            "17:32:10,438 |-INFO in ch.qos.logback.core.joran.spi.ConfigurationWatchList@47747fb9 - URL [jar:file:/C:/Users/demo/.m2/repository/org/springframework/boot/spring-boot/3.1.2/spring-boot-3.1.2.jar!/org/springframework/boot/logging/logback/defaults.xml] is not of type file\n" +
            "17:32:10,441 |-INFO in ch.qos.logback.core.joran.action.ConversionRuleAction - registering conversion word clr with class [org.springframework.boot.logging.logback.ColorConverter]\n" +
            "17:32:10,441 |-INFO in ch.qos.logback.core.joran.action.ConversionRuleAction - registering conversion word wex with class [org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter]\n" +
            "17:32:10,441 |-INFO in ch.qos.logback.core.joran.action.ConversionRuleAction - registering conversion word wEx with class [org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter]\n" +
            "17:32:10,443 |-INFO in ch.qos.logback.core.joran.action.ConversionRuleAction - registering conversion word clr with class [org.springframework.boot.logging.logback.ColorConverter]\n" +
            "17:32:10,443 |-INFO in ch.qos.logback.core.joran.action.ConversionRuleAction - registering conversion word wex with class [org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter]\n" +
            "17:32:10,443 |-INFO in ch.qos.logback.core.joran.action.ConversionRuleAction - registering conversion word wEx with class [org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter]\n" +
            "17:32:10,474 |-INFO in ch.qos.logback.classic.model.processor.LoggerModelHandler - Setting level of logger [org.apache.catalina.startup.DigesterFactory] to ERROR\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@3113a37 - Propagating ERROR level on Logger[org.apache.catalina.startup.DigesterFactory] onto the JUL framework\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.model.processor.LoggerModelHandler - Setting level of logger [org.apache.catalina.util.LifecycleBase] to ERROR\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@3113a37 - Propagating ERROR level on Logger[org.apache.catalina.util.LifecycleBase] onto the JUL framework\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.model.processor.LoggerModelHandler - Setting level of logger [org.apache.coyote.http11.Http11NioProtocol] to WARN\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@3113a37 - Propagating WARN level on Logger[org.apache.coyote.http11.Http11NioProtocol] onto the JUL framework\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.model.processor.LoggerModelHandler - Setting level of logger [org.apache.sshd.common.util.SecurityUtils] to WARN\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@3113a37 - Propagating WARN level on Logger[org.apache.sshd.common.util.SecurityUtils] onto the JUL framework\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.model.processor.LoggerModelHandler - Setting level of logger [org.apache.tomcat.util.net.NioSelectorPool] to WARN\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@3113a37 - Propagating WARN level on Logger[org.apache.tomcat.util.net.NioSelectorPool] onto the JUL framework\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.model.processor.LoggerModelHandler - Setting level of logger [org.eclipse.jetty.util.component.AbstractLifeCycle] to ERROR\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@3113a37 - Propagating ERROR level on Logger[org.eclipse.jetty.util.component.AbstractLifeCycle] onto the JUL framework\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.model.processor.LoggerModelHandler - Setting level of logger [org.hibernate.validator.internal.util.Version] to WARN\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@3113a37 - Propagating WARN level on Logger[org.hibernate.validator.internal.util.Version] onto the JUL framework\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.model.processor.LoggerModelHandler - Setting level of logger [org.springframework.boot.actuate.endpoint.jmx] to WARN\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@3113a37 - Propagating WARN level on Logger[org.springframework.boot.actuate.endpoint.jmx] onto the JUL framework\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.core.model.processor.AppenderModelHandler - Processing appender named [console]\n" +
            "17:32:10,475 |-INFO in ch.qos.logback.core.model.processor.AppenderModelHandler - About to instantiate appender of type [ch.qos.logback.core.ConsoleAppender]\n" +
            "17:32:10,479 |-INFO in ch.qos.logback.core.model.processor.ImplicitModelHandler - Assuming default type [ch.qos.logback.classic.encoder.PatternLayoutEncoder] for [encoder] property\n" +
            "17:32:10,492 |-WARN in ch.qos.logback.core.model.processor.AppenderModelHandler - Appender named [CONSOLE] not referenced. Skipping further processing.\n" +
            "17:32:10,492 |-INFO in ch.qos.logback.core.model.processor.AppenderModelHandler - Processing appender named [FILE]\n" +
            "17:32:10,492 |-INFO in ch.qos.logback.core.model.processor.AppenderModelHandler - About to instantiate appender of type [ch.qos.logback.core.rolling.RollingFileAppender]\n" +
            "17:32:10,514 |-INFO in c.q.l.core.rolling.TimeBasedRollingPolicy@557725225 - No compression will be used\n" +
            "17:32:10,514 |-INFO in c.q.l.core.rolling.TimeBasedRollingPolicy@557725225 - Will use the pattern ./log/abm-server-%d{yyyy-MM-dd}.log for the active file\n" +
            "17:32:10,561 |-INFO in c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy - The date pattern is 'yyyy-MM-dd' from file name pattern './log/abm-server-%d{yyyy-MM-dd}.log'.\n" +
            "17:32:10,561 |-INFO in c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy - Roll-over at midnight.\n" +
            "17:32:10,581 |-INFO in c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy - Setting initial period to 2023-09-13T09:29:53.311Z\n" +
            "17:32:10,582 |-INFO in ch.qos.logback.core.model.processor.ImplicitModelHandler - Assuming default type [ch.qos.logback.classic.encoder.PatternLayoutEncoder] for [encoder] property\n" +
            "17:32:10,584 |-INFO in ch.qos.logback.core.rolling.RollingFileAppender[FILE] - Active log file name: ./log/abm-server.log\n" +
            "17:32:10,584 |-INFO in ch.qos.logback.core.rolling.RollingFileAppender[FILE] - File property is set to [./log/abm-server.log]\n" +
            "17:32:10,584 |-INFO in ch.qos.logback.classic.model.processor.RootLoggerModelHandler - Setting level of ROOT logger to INFO\n" +
            "17:32:10,584 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@3113a37 - Propagating INFO level on Logger[ROOT] onto the JUL framework\n" +
            "17:32:10,585 |-INFO in ch.qos.logback.core.model.processor.AppenderRefModelHandler - Attaching appender named [console] to Logger[ROOT]\n" +
            "17:32:10,585 |-INFO in ch.qos.logback.core.model.processor.AppenderRefModelHandler - Attaching appender named [FILE] to Logger[ROOT]\n" +
            "17:32:10,585 |-INFO in ch.qos.logback.core.model.processor.DefaultProcessor@4e9658b5 - End of configuration.\n" +
            "17:32:10,585 |-INFO in org.springframework.boot.logging.logback.SpringBootJoranConfigurator@2a7b6f69 - Registering current configuration as safe fallback point\n" +
            "\n" +
            "\n" +
            "  .   ____          _            __ _ _\n" +
            " /\\\\ / ___'_ __ _ _(_)_ __  __ _ \\ \\ \\ \\\n" +
            "( ( )\\___ | '_ | '_| | '_ \\/ _` | \\ \\ \\ \\\n" +
            " \\\\/  ___)| |_)| | | | | || (_| |  ) ) ) )\n" +
            "  '  |____| .__|_| |_|_| |_\\__, | / / / /\n" +
            " =========|_|==============|___/=/_/_/_/\n" +
            " :: Spring Boot ::                (v3.1.2)\n" +
            "\n" +
            "2023-09-13T17:32:10.693+08:00  INFO 40384 --- [           main] com.abm.AbmServiceApplication            : Starting AbmServiceApplication using Java 17.0.3.1 with PID 40384 (D:\\Demo\\Project\\demo-server\\abm-server\\target\\classes started by demo_user in D:\\Demo\\Project\\demo-server)\n" +
            "2023-09-13T17:32:10.695+08:00  INFO 40384 --- [           main] com.abm.AbmServiceApplication            : The following 1 profile is active: \"local\"\n" +
            "2023-09-13T17:32:12.989+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'inspectionMapper' and 'com.abm.mapper.auto.InspectionMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.990+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'sysMenuMapper' and 'com.abm.mapper.Menu.SysMenuMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.990+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'loadHiveMoniterMapper' and 'com.abm.mapper.Monitor.HiveMonitor.LoadHiveMoniterMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.990+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'monitorHiveInfoMapper' and 'com.abm.mapper.Monitor.HiveMonitor.MonitorHiveInfoMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.990+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'monitorThresholdsMapper' and 'com.abm.mapper.Monitor.MonitorThresholdsMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.990+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'cronModelMapper' and 'com.abm.mapper.PublicMapper.CronModelMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.990+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'taskProcessMapper' and 'com.abm.mapper.PublicMapper.TaskProcessMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.990+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'softClickHouseMapper' and 'com.abm.mapper.soft.ClickHouse.SoftClickHouseMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.990+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'softZookeeperMapper' and 'com.abm.mapper.soft.hadoop.zookeeper.SoftZookeeperMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.990+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'mysqlInfoMapper' and 'com.abm.mapper.soft.mysql.MysqlInfoMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.990+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'mysqlStateMapper' and 'com.abm.mapper.soft.mysql.MysqlStateMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.991+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'softMysqlMapper' and 'com.abm.mapper.soft.mysql.SoftMysqlMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:12.991+08:00  WARN 40384 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : Skipping MapperFactoryBean with name 'runCommandMapper' and 'com.abm.mapper.soft.RunCommand.RunCommandMapper' mapperInterface. Bean already defined with the same name!\n" +
            "2023-09-13T17:32:13.278+08:00  INFO 40384 --- [           main] ptablePropertiesBeanFactoryPostProcessor : Post-processing PropertySource instances\n" +
            "2023-09-13T17:32:13.280+08:00  INFO 40384 --- [           main] c.u.j.EncryptablePropertySourceConverter : Skipping PropertySource configurationProperties [class org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource\n" +
            "2023-09-13T17:32:13.281+08:00  INFO 40384 --- [           main] c.u.j.EncryptablePropertySourceConverter : Skipping PropertySource servletConfigInitParams [class org.springframework.core.env.PropertySource$StubPropertySource\n" +
            "2023-09-13T17:32:13.282+08:00  INFO 40384 --- [           main] c.u.j.EncryptablePropertySourceConverter : Skipping PropertySource servletContextInitParams [class org.springframework.core.env.PropertySource$StubPropertySource\n" +
            "2023-09-13T17:32:13.283+08:00  INFO 40384 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource systemProperties [org.springframework.core.env.PropertiesPropertySource] to EncryptableMapPropertySourceWrapper\n" +
            "2023-09-13T17:32:13.283+08:00  INFO 40384 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource systemEnvironment [org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor$OriginAwareSystemEnvironmentPropertySource] to EncryptableSystemEnvironmentPropertySourceWrapper\n" +
            "2023-09-13T17:32:13.283+08:00  INFO 40384 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource random [org.springframework.boot.env.RandomValuePropertySource] to EncryptablePropertySourceWrapper\n" +
            "2023-09-13T17:32:13.283+08:00  INFO 40384 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource Config resource 'class path resource [application-local.yml]' via location 'optional:classpath:/' [org.springframework.boot.env.OriginTrackedMapPropertySource] to EncryptableMapPropertySourceWrapper\n" +
            "2023-09-13T17:32:13.283+08:00  INFO 40384 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource Config resource 'class path resource [application.yml]' via location 'optional:classpath:/' [org.springframework.boot.env.OriginTrackedMapPropertySource] to EncryptableMapPropertySourceWrapper\n" +
            "2023-09-13T17:32:13.590+08:00  INFO 40384 --- [           main] c.u.j.filter.DefaultLazyPropertyFilter   : Property Filter custom Bean not found with name 'encryptablePropertyFilter'. Initializing Default Property Filter\n" +
            "2023-09-13T17:32:13.598+08:00  INFO 40384 --- [           main] c.u.j.r.DefaultLazyPropertyResolver      : Property Resolver custom Bean not found with name 'encryptablePropertyResolver'. Initializing Default Property Resolver\n" +
            "2023-09-13T17:32:13.600+08:00  INFO 40384 --- [           main] c.u.j.d.DefaultLazyPropertyDetector      : Property Detector custom Bean not found with name 'encryptablePropertyDetector'. Initializing Default Property Detector\n" +
            "2023-09-13T17:32:13.806+08:00  INFO 40384 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 9999 (http)\n" +
            "2023-09-13T17:32:13.818+08:00  INFO 40384 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]\n" +
            "2023-09-13T17:32:13.818+08:00  INFO 40384 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.11]\n" +
            "2023-09-13T17:32:13.945+08:00  INFO 40384 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/abm]    : Initializing Spring embedded WebApplicationContext\n" +
            "2023-09-13T17:32:13.945+08:00  INFO 40384 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 3021 ms\n" +
            " _ _   |_  _ _|_. ___ _ |    _ \n" +
            "| | |\\/|_)(_| | |_\\  |_)||_|_\\ \n" +
            "     /               |         \n" +
            "                        3.5.3.1 \n" +
            "2023-09-13T17:32:15.587+08:00  INFO 40384 --- [           main] f.a.AutowiredAnnotationBeanPostProcessor : Autowired annotation is not supported on static fields: private static com.abm.config.CommonConfig com.abm.util.FileUtil.RestTemplateFileUtil.commonConfig\n" +
            "2023-09-13T17:32:15.590+08:00  INFO 40384 --- [           main] com.abm.util.IdGeneratorSnowflake        : 当前机器的workerId: 0\n" +
            "2023-09-13T17:32:16.062+08:00  INFO 40384 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page template: index\n" +
            "2023-09-13T17:32:16.236+08:00  WARN 40384 --- [           main] o.thymeleaf.templatemode.TemplateMode    : [THYMELEAF][main] Unknown Template Mode 'HTML5'. Must be one of: 'HTML', 'XML', 'TEXT', 'JAVASCRIPT', 'CSS', 'RAW'. Using default Template Mode 'HTML'.\n" +
            "2023-09-13T17:32:16.556+08:00  INFO 40384 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 9999 (http) with context path '/abm'\n" +
            "2023-09-13T17:32:16.558+08:00  INFO 40384 --- [           main] u.j.c.RefreshScopeRefreshedEventListener : Refreshing cached encryptable property sources on ServletWebServerInitializedEvent\n" +
            "2023-09-13T17:32:16.559+08:00  INFO 40384 --- [           main] CachingDelegateEncryptablePropertySource : Property Source systemProperties refreshed\n" +
            "2023-09-13T17:32:16.559+08:00  INFO 40384 --- [           main] CachingDelegateEncryptablePropertySource : Property Source systemEnvironment refreshed\n" +
            "2023-09-13T17:32:16.559+08:00  INFO 40384 --- [           main] CachingDelegateEncryptablePropertySource : Property Source random refreshed\n" +
            "2023-09-13T17:32:16.559+08:00  INFO 40384 --- [           main] CachingDelegateEncryptablePropertySource : Property Source Config resource 'class path resource [application-local.yml]' via location 'optional:classpath:/' refreshed\n" +
            "2023-09-13T17:32:16.559+08:00  INFO 40384 --- [           main] CachingDelegateEncryptablePropertySource : Property Source Config resource 'class path resource [application.yml]' via location 'optional:classpath:/' refreshed\n" +
            "2023-09-13T17:32:16.559+08:00  INFO 40384 --- [           main] c.u.j.EncryptablePropertySourceConverter : Skipping PropertySource configurationProperties [class org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource\n" +
            "2023-09-13T17:32:16.559+08:00  INFO 40384 --- [           main] c.u.j.EncryptablePropertySourceConverter : Skipping PropertySource servletConfigInitParams [class org.springframework.core.env.PropertySource$StubPropertySource\n" +
            "2023-09-13T17:32:16.559+08:00  INFO 40384 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource servletContextInitParams [org.springframework.web.context.support.ServletContextPropertySource] to EncryptableEnumerablePropertySourceWrapper\n" +
            "2023-09-13T17:32:16.572+08:00  INFO 40384 --- [           main] com.abm.AbmServiceApplication            : Started AbmServiceApplication in 6.635 seconds (process running for 8.145)\n" +
            "2023-09-13T17:32:18.626+08:00  INFO 40384 --- [nio-9999-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/abm]    : Initializing Spring DispatcherServlet 'dispatcherServlet'\n" +
            "2023-09-13T17:32:18.626+08:00  INFO 40384 --- [nio-9999-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'\n" +
            "2023-09-13T17:32:18.628+08:00  INFO 40384 --- [nio-9999-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 2 ms\n" +
            "2023-09-13T17:32:31.577+08:00  INFO 40384 --- [taskScheduler-1] com.abm.task.ScheduledTask               : sumDiskSizeCacheTask------------2023-09-13 17:32:31\n" +
            "2023-09-13T17:32:31.646+08:00  WARN 40384 --- [taskScheduler-1] com.zaxxer.hikari.HikariConfig           : HikariPool-1 - idleTimeout is close to or more than maxLifetime, disabling it.\n" +
            "2023-09-13T17:32:31.647+08:00  INFO 40384 --- [taskScheduler-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...\n" +
            "2023-09-13T17:32:31.962+08:00  INFO 40384 --- [taskScheduler-1] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection com.mysql.jdbc.JDBC4Connection@1add2b03\n" +
            "2023-09-13T17:32:31.968+08:00  INFO 40384 --- [taskScheduler-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.\n" +
            "2023-09-13T17:32:36.569+08:00  INFO 40384 --- [taskScheduler-2] com.abm.task.ScheduledTask               : initTask------------2023-09-13 17:32:36\n" +
            "2023-09-13T17:32:36.576+08:00  INFO 40384 --- [taskScheduler-2] com.abm.task.ScheduledTask               : 本机IP地址：10.0.0.100\n";
}
