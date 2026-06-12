import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by Wz on 6/30/2019.
 */
public class JiqunJDBCTest {
    static String sql = "SELECT\n" +
            "\ttr.d_motor_room motor_room,\n" +
            "IF\n" +
            "\t(\n" +
            "\t\tavg(\n" +
            "\t\tIF\n" +
            "\t\t\t( ce.inUsed < '5.000', NULL, ce.inUsed )) < avg(\n" +
            "\t\tIF\n" +
            "\t\t( ce.outUsed < '5.000', NULL, ce.outUsed )),\n" +
            "\t\tavg(\n" +
            "\t\tIF\n" +
            "\t\t( ce.outUsed < '5.000', NULL, ce.outUsed )),\n" +
            "\t\tavg(\n" +
            "\t\tIF\n" +
            "\t\t( ce.inUsed < '5.000', NULL, ce.inUsed ))) trans_load \n" +
            "FROM\n" +
            "\tCHECK.check_lte_exchanger ce\n" +
            "\tINNER JOIN sx_cfg_trans_info tr ON ce.PORT = tr.PORT \n" +
            "WHERE\n" +
            "\ttime BETWEEN date_sub( now(), INTERVAL 40 MINUTE ) \n" +
            "\tAND now() \n" +
            "\tAND ce.HOST = '10.0.0.3' \n" +
            "\tAND tr.d_motor_room IS NOT NULL \n" +
            "GROUP BY\n" +
            "\ttr.d_motor_room";

    static String link_bandwidth = "SELECT * from sx_cfg_link_bandwidth where type='jiqun'";

    static String point_device_count = "SELECT motor_room,\n" +
            "\tcount( ip_id ) ser_cnt \n" +
            "FROM\n" +
            "\tCHECK.sx_cfg_server_info \n" +
            "WHERE\n" +
            "\tuse_type = '集群' \n" +
            "\tgroup by motor_room;";

    public static void main(String[] args) throws IOException {
        t();
    }

    private static void t(){
        $JDBCConnConfiguration jdbcConnConfiguration = new $JDBCConnConfiguration();

        jdbcConnConfiguration.setDriver("com.mysql.jdbc.Driver");
        jdbcConnConfiguration.setPort(3306);
        jdbcConnConfiguration.setIp("demo-mysql-server");
        jdbcConnConfiguration.setDatabase("testdb");
        jdbcConnConfiguration.setUsername("testuser");
        jdbcConnConfiguration.setPassword("Test@123456");

        $.jdbc.mysql.setConnConfiguration(jdbcConnConfiguration);

        // 查询分区的结果
        List<Map<String,Object>>  list = $.jdbc.mysql.getJdbcTemplate().queryForList(point_device_count);

//        SHANGBAO_YEWU(list);
//        SHANGBAO_QUSHITU_DAY(list);
//        SHANGBAO_QUSHITU_WEEK(list);
//        SHANGBAO_DEVICE(list);
//        avg_WARN(list);
//        ALL_DEVICE(list);
//        jiqun(list);
        point_device_count(list);
    }

    private static void point_device_count(List<Map<String, Object>> list) {
        Map<String, Object> map = new HashMap<>();
        for(Map<String, Object> mp : list) map.put(mp.get("motor_room").toString(), mp.get("ser_cnt"));

        System.out.println(JSONObject.fromObject(map));
    }

    /**
     * 集群链路带宽信息
     * @param list
     */
    private static void jiqun(List<Map<String, Object>> list) {
        Map<String, Object> map = new HashMap<>();
        for(Map<String, Object> mp : list) map.put(mp.get("label").toString(), mp.get("context"));

        System.out.println(JSONObject.fromObject(map));
    }

    /**
     * 集群负载信息信息
     * @param list
     * @return
     */
    private static void avg_WARN(List<Map<String, Object>> list) {
        Map<String, Object> map = new HashMap<>();
        for(Map<String, Object> mp : list) map.put(mp.get("motor_room").toString(), mp.get("trans_load"));

        System.out.println(JSONObject.fromObject(map));
    }

}
