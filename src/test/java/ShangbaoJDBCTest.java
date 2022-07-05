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
public class ShangbaoJDBCTest {
    static String shangbao_business_sql = "SELECT\n" +
            "\tcase when td.uptype in ('IuCS','IuPS','LTE') then 'ESB'\n" +
            "when td.uptype in ('PDP084','CDR084','FDR084') then '集团信息化'\n" +
            "when td.uptype in ('13文件','14文件','15文件') then '管局'\n" +
            "when td.uptype in ('CM','PM','MR') then 'CMPMMR' end upgroup,\n" +
            "\ttd.uptype,\n" +
            "\tsum( td.size_gb ) size_gb ,\n" +
            "\tround(sum( td.size_gb )-ifnull(sum( yd.size_gb ), sum( td.size_gb )*0.98),2) yd_size_gb ,\n" +
            "\tround((sum( td.size_gb )-sum( yw.size_gb ))/sum( td.size_gb ),4) yw_size_gb\n" +
            "FROM\n" +
            "\tCHECK.sx_monitor_up as td \n" +
            "\tleft join CHECK.sx_monitor_up as yd on td.uptype=yd.uptype and yd.time= subdate( curdate(), 2 )\n" +
            "\tleft join CHECK.sx_monitor_up as yw on td.uptype=yw.uptype and yw.time= subdate( curdate(), 7 )\n" +
            "WHERE\n" +
            "\ttd.TIME = subdate( curdate(), 1 ) \n" +
            "GROUP BY\n" +
            "\ttd.uptype\n" +
            "\t;";

    static String shangbao_day_sql = "SELECT\n" +
            "td.TIME,\n" +
            "case when td.uptype in ('IuCS','IuPS','LTE') then 'ESB'\n" +
            "when td.uptype in ('PDP084','CDR084','FDR084') then '集团信息化'\n" +
            "when td.uptype in ('13文件','14文件','15文件') then '管局'\n" +
            "when td.uptype in ('CM','PM','MR') then 'CMPMMR' END uptype,\n" +
            "\tsum( td.size_gb ) size_gb \n" +
            "FROM\n" +
            "\tCHECK.sx_monitor_up as td \n" +
            "WHERE\t\n" +
            "\ttd.TIME > DATE_SUB(CURDATE(),INTERVAL 1 WEEK)\n" +
            "group by \n" +
            "case when td.uptype in ('IuCS','IuPS','LTE') then 'ESB'\n" +
            "when td.uptype in ('PDP084','CDR084','FDR084') then '集团信息化'\n" +
            "when td.uptype in ('13文件','14文件','15文件') then '管局'\n" +
            "when td.uptype in ('CM','PM','MR') then 'CMPMMR' END,\n" +
            "\ttd.TIME\n" +
            "\t;";

    static String shangbap_week_sql = "\n" +
            "select \n" +
            "\ttt.TIME,\n" +
            "\ttt.uptype,\n" +
            "\tsum(tt.size_gb) size_gb\n" +
            "from (\n" +
            "\tSELECT\n" +
            "\ttd.TIME,\n" +
            "\tcase when td.uptype in ('IuCS','IuPS','LTE') then 'ESB'\n" +
            "\twhen td.uptype in ('PDP084','CDR084','FDR084') then '集团信息化'\n" +
            "\twhen td.uptype in ('13文件','14文件','15文件') then '管局'\n" +
            "\twhen td.uptype in ('CM','PM','MR') then 'CMPMMR' END uptype,\n" +
            "\t\tsum( td.size_gb ) size_gb \n" +
            "\tFROM\n" +
            "\t\tCHECK.sx_monitor_up as td \n" +
            "\tWHERE\t\n" +
            "\t\ttd.TIME > DATE_SUB(CURDATE(),INTERVAL 2 MONTH)\n" +
            "\tgroup by \n" +
            "\t\tcase when td.uptype in ('IuCS','IuPS','LTE') then 'ESB'\n" +
            "\t\twhen td.uptype in ('PDP084','CDR084','FDR084') then '集团信息化'\n" +
            "\t\twhen td.uptype in ('13文件','14文件','15文件') then '管局'\n" +
            "\t\twhen td.uptype in ('CM','PM','MR') then 'CMPMMR' END,\n" +
            "\t\t\ttd.TIME\n" +
            ") tt\n" +
            "group by \n" +
            "\tDATE_FORMAT(tt.TIME, '%Y-%u'),\n" +
            "\tuptype";

    static String shangbao_device_sql = "SELECT cf.use_type,count(distinct cf.ip_id)-count(distinct w.host) nomal,count(distinct w.host) abnomal\n" +
            "FROM\n" +
            "\tcheck.sx_cfg_server_info cf left join check.warn_result w   on w.host=cf.IP_id\n" +
            "\tand wlevel = 'urgency' and warnid not in ('10009') \n" +
            "\twhere cf.USE_TYPE IN ('2file','kafka') \n" +
            "\tgroup by cf.use_type;";

    static String shangbao_warn_sql = "SELECT \n" +
            "\tdistinct `host`,\n" +
            "\tCASE\t\t\n" +
            "\t\tWHEN wlevel = 'urgency' THEN\n" +
            "\t\t'紧急' \n" +
            "\t\tWHEN wlevel = 'general' THEN\n" +
            "\t\t'一般' ELSE '普通' \n" +
            "\tEND wlevel_cn,\n" +
            "\tw.*,\n" +
            "\tcf.*\n" +
            "FROM\n" +
            "\tcheck.warn_result w inner join check.sx_cfg_server_info cf on w.host=cf.IP_id\n" +
            "\twhere cf.USE_TYPE IN ('2file','kafka') and warnid not in ('10009');";

    static String host_list = "SELECT *\n" +
            "FROM\n" +
            "\tCHECK.sx_cfg_server_info \n";

    static String link_bandwidth = "SELECT * from sx_cfg_link_bandwidth where type='shangbao'";

    static String point_load = "\n" +
            "\tselect CASE WHEN PORT IN ('GigabitEthernet3/0/8(10G)') then 'ESB' \n" +
            "\tWHEN PORT IN ('GigabitEthernet2/0/1','GigabitEthernet2/0/2') then '集团信息化' \n" +
            "\tWHEN PORT IN ('GigabitEthernet3/0/9(10G)','GigabitEthernet2/0/0') then '管局' END momot_room,\n" +
            "\tif(avg(if(inused<0.001,null,inused))<avg(if(inused<0.001,null,outused)), avg(if(inused<0.001,null,outused)),avg(if(inused<0.001,null,inused))) up_load\n" +
            "\tfrom check.check_lte_exchanger \n" +
            "\twhere time BETWEEN date_sub( now(), INTERVAL 50 MINUTE ) \tAND now()\n" +
            "\tand port in ('GigabitEthernet3/0/8(10G)','GigabitEthernet2/0/1','GigabitEthernet2/0/1','GigabitEthernet3/0/9(10G)','GigabitEthernet2/0/0')\n" +
            " and host='172.26.1.25'\n" +
            " group by  CASE WHEN PORT IN ('GigabitEthernet3/0/8(10G)') then 'ESB' \n" +
            "\tWHEN PORT IN ('GigabitEthernet2/0/1','GigabitEthernet2/0/2') then '集团信息化' \n" +
            "\tWHEN PORT IN ('GigabitEthernet3/0/9(10G)','GigabitEthernet2/0/0') then '管局' end\n" +
            "\n" +
            "union all\n" +
            "\n" +
            "select 'TT' momot_room,tx_flow/100 up_load from check_lte_interfaceinfo  \n" +
            "where  time BETWEEN date_sub( now(), INTERVAL 30 MINUTE ) \tAND now()\n" +
            "and ip='172.26.1.198' and port='1/f/25';\n";

    static String overview_sql = "\n" +
            "select cpu_load,mem_load,disk_load\n" +
            "from \n" +
            "(\n" +
            "select \n" +
            "\t'up' up,\n" +
            "\tavg(cpuusedrate) cpu_load,\n" +
            "\tsum(usedmem)/sum(mem) mem_load \n" +
            "from\t\n" +
            "\tcheck.check_server_performancev w\n" +
            "\tinner join check.sx_cfg_server_info cf on w.host=cf.IP_id\n" +
            "where \n" +
            "\tcf.USE_TYPE IN ('2file','kafka') \n" +
            "\tand time>=date_sub(CURDATE(), interval 1 hour)\n" +
            "\tand w.system is not null and cpuusedrate>5\n" +
            ") cm\n" +
            "\n" +
            "inner join \n" +
            "\n" +
            "(\n" +
            "select \n" +
            "\t'up' up,\n" +
            "\tavg(usedrate) disk_load \n" +
            "from check_server_disk di \n" +
            "\tinner join check.sx_cfg_server_info cf on di.host=cf.IP_id\n" +
            "where \n" +
            "\tcf.USE_TYPE IN ('2file','kafka') \n" +
            "\tand time>=(SELECT date_sub(max(time), interval 1 hour) FROM check_server_disk)\n" +
            "\tand `data` regexp '^/data'\n" +
            "\tand usedrate>10 \n" +
            ") di on cm.up=di.up;";

    public static void main(String[] args) throws IOException {
        t();
    }

    private static void t(){
        $JDBCConnConfiguration jdbcConnConfiguration = new $JDBCConnConfiguration();

        jdbcConnConfiguration.setDriver("com.mysql.jdbc.Driver");
        jdbcConnConfiguration.setPort(43306);
        jdbcConnConfiguration.setIp("127.0.0.1");
        jdbcConnConfiguration.setDatabase("check");
        jdbcConnConfiguration.setUsername("root");
        jdbcConnConfiguration.setPassword("broadtech");

        $.jdbc.mysql.setConnConfiguration(jdbcConnConfiguration);

        // 查询分区的结果
        List<Map<String,Object>>  list = $.jdbc.mysql.getJdbcTemplate().queryForList(overview_sql);

//        SHANGBAO_YEWU(list);
//        SHANGBAO_QUSHITU_DAY(list);
//        SHANGBAO_QUSHITU_WEEK(list);
//        SHANGBAO_DEVICE(list);
//        SHANGBAO_WARN(list);
//        ALL_DEVICE(list);
//        SHANGBAO_linkBandwidth(list);
//        SHANGBAO_point_load(list);
        SHANGBAO_overview(list);
    }

    private static void SHANGBAO_overview(List<Map<String, Object>> list) {
        Map<String, Object> map = new HashMap<>();
        for(Map<String, Object> mp : list) {
            map.put("cpu_load", mp.get("cpu_load"));
            map.put("mem_load", mp.get("mem_load"));
            map.put("disk_load", mp.get("disk_load"));
        }

        System.out.println(JSONObject.fromObject(map));
    }

    private static void SHANGBAO_point_load(List<Map<String, Object>> list) {
        Map<String, Object> map = new HashMap<>();
        for(Map<String, Object> mp : list) map.put(mp.get("momot_room").toString(), mp.get("up_load"));

        System.out.println(JSONObject.fromObject(map));
    }

    private static void SHANGBAO_linkBandwidth(List<Map<String, Object>> list) {
        Map<String, Object> map = new HashMap<>();
        for(Map<String, Object> mp : list) map.put(mp.get("label").toString(), mp.get("context"));

        System.out.println(JSONObject.fromObject(map));
    }

    /**
     * 上报业务数据
     * @param list
     * @return
     */
    private static void SHANGBAO_YEWU(List<Map<String, Object>> list) {
        Map<String, List<Map<String,Object>>> result = new HashMap<>();
        for(Map<String, Object> mp : list) {
            String ugroup = mp.get("upgroup").toString();
            if(null == result.get(ugroup)) result.put(ugroup, new ArrayList<>());
            result.get(ugroup).add(mp);
        }

        System.out.println(JSONObject.fromObject(result));
    }

    /**
     * 上报业务数据
     * @param list
     * @return
     */
    private static void SHANGBAO_QUSHITU_DAY(List<Map<String, Object>> list) {
        Map<String, Map<String,Object>> result = new HashMap<>();
        for(Map<String, Object> mp : list) {
            String time = mp.get("time").toString();
            String uptype = mp.get("uptype").toString();
            if(null == result.get(time)) result.put(time, new HashMap<>());
            if(null == result.get(time).get(uptype)) result.get(time).put(uptype, mp.get("size_gb"));
        }

        System.out.println(JSONObject.fromObject(result));
    }

    /**
     * 上报业务数据
     * @param list
     * @return
     */
    private static void SHANGBAO_QUSHITU_WEEK(List<Map<String, Object>> list) {
        Map<String, Map<String,Object>> result = new HashMap<>();
        for(Map<String, Object> mp : list) {
            String time = mp.get("time").toString();
            String uptype = mp.get("uptype").toString();
            if(null == result.get(time)) result.put(time, new HashMap<>());
            if(null == result.get(time).get(uptype)) result.get(time).put(uptype, mp.get("size_gb"));
        }

        System.out.println(JSONObject.fromObject(result));
    }

    /**
     * 上报业务数据
     * @param list
     * @return
     */
    private static void SHANGBAO_DEVICE(List<Map<String, Object>> list) {
        Map<String, Map<String,Object>> result = new HashMap<>();
        for(Map<String, Object> mp : list) {
            String use_type = mp.get("use_type").toString();
            result.put(use_type, mp);
        }

        System.out.println(JSONObject.fromObject(result));
    }

    /**
     * 上报业务数据
     * @param list
     * @return
     */
    private static void SHANGBAO_WARN(List<Map<String, Object>> list) {
        System.out.println(JSONArray.fromObject(list));
    }


    private static void ALL_DEVICE(List<Map<String, Object>> list) {
        System.out.println(JSONArray.fromObject(list));
    }
}
