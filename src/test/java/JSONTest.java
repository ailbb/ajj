import com.ailbb.ajj.$;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by Wz on 6/30/2019.
 */
public class JSONTest {
    public static void main(String[] args) {
        new JSONTest().test("{\"tableName\":\"hivelineage\",\"columnInfo\":{\"queryId\":\"root_20191118105858_c5664fdd-b3fd-445a-b7cf-e4df94d2dd3e\"}}");
    }

    public JsonTable test(String json) {
        JSONObject jo = $.toJsonObject(json);
        String tableName = $.notNull(jo.get("tableName"), "数据不能为空"); // 表名
        Map<String,Object> columnInfo = $.toJsonObject($.notNull(jo.get("tableName"), "数据不能为空")); // 列
        return new JsonTable(tableName, columnInfo);
    }


    public class JsonTable {
        private String tableName;
        private Map<String,Object> columnInfo;

        public JsonTable(String tableName, Map<String, Object> columnInfo) {
            this.tableName = tableName;
            this.columnInfo = columnInfo;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public Map<String, Object> getColumnInfo() {
            return columnInfo;
        }

        public void setColumnInfo(Map<String, Object> columnInfo) {
            this.columnInfo = columnInfo;
        }
    }
}
