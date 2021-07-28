package com.ailbb.ajj.jdbc;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import com.ailbb.ajj.jdbc.pojo.oracle.Field;
import com.ailbb.ajj.jdbc.pojo.oracle.FieldType;
import com.ailbb.ajj.jdbc.pojo.oracle.Table;
import com.ailbb.ajj.log.$Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

public class Oracle extends JDBCRunner {
    /*
     * demo
     private String driver = "oracle.jdbc.driver.OracleDriver";
     private String ip = "localhost";
     private int port = 1521;
     private String database = "oracle";
     private String username;
     private String password;
     *
     */
    public static final String $DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final int $PORT = 1521;
    public $Logger logger = $.logger;
    private boolean IS_TRUNCATE_PARTITION = true;
    private String ORACLE_PUSH_COMPLETE = "PUSH_COMPLETED";

    public Oracle(){}

    public Oracle($JDBCConnConfiguration connConfiguration) { setConnConfiguration(connConfiguration, null); }
    public Oracle($JDBCConnConfiguration connConfiguration, $Logger logger) { setConnConfiguration(connConfiguration, logger); }

    public JDBCRunner setConnConfiguration($JDBCConnConfiguration connConfiguration) { return setConnConfiguration(connConfiguration, null); }
    public JDBCRunner setConnConfiguration($JDBCConnConfiguration connConfiguration, $Logger logger) {
        super.setConnConfiguration(connConfiguration);

        try {

            DriverManagerDataSource dataSource=new DriverManagerDataSource();
            dataSource.setDriverClassName($.lastDef($DRIVER, connConfiguration.getDriver()));
            dataSource.setUrl(
                    !$.isEmptyOrNull(connConfiguration.getUrl()) ? connConfiguration.getUrl() :
                            String.format("jdbc:oracle:thin:@%s:%s:%s", $.notNull(connConfiguration.getIp()), $.notNull(connConfiguration.getPort()), $.notNull(connConfiguration.getDatabase()))
            );
            dataSource.setUsername($.notNull(connConfiguration.getUsername()));
            dataSource.setPassword($.notNull(connConfiguration.getPassword()));
            if(null != logger) this.logger = logger;
            return setJdbcTemplate(new JdbcTemplate(dataSource));
        } catch (Exception e) {
            $.error("由于Oracle授权问题，Maven3不提供oracle JDBC driver，我们也可以搜索ojdbc驱动包下载。");
            e.printStackTrace();
        }

        return this;
    }

    /*
     * .查询出oracle的表信息，构建出insert语句，和后续动态setValue
     * <p/>
     * 1：字段名    2：字段类型
     */
    public Table queryTableInfo(String tableName) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Table table = new Table(tableName);
        String sql = "SELECT data_type,column_name   FROM user_tab_columns WHERE table_name=? ORDER BY column_id ASC";
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, tableName);
//            List<Field> columnInfos = new ArrayList<Field>();
            rs = ps.executeQuery();
            while (rs.next()) {
//                ColumnInfo columnInfo = new ColumnInfo();
//                columnInfo.setColumnName(rs.getString("COLUMN_NAME"));
//                columnInfo.setColumnType(rs.getString("DATA_TYPE"));
//                columnInfos.add(columnInfo);
                Field field = new Field();
                field.setFieldName(rs.getString("COLUMN_NAME"));
                field.setFieldType(rs.getString("DATA_TYPE"));
//                columnInfos.add(field);
                table.addField(field);
            }
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        }
        return table;
    }


    /*
     * 是否存在该分区
     */
    public boolean isHasPartition(String tableName, String partitionName) throws Exception {
        Connection conn = null;
        String sql = "SELECT COUNT(*) AS isExist FROM  user_tab_partitions WHERE table_name= ? AND partition_name = ?";
        boolean isExist = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, tableName);
            ps.setString(2, partitionName);
            rs = ps.executeQuery();
            while (rs.next()) {
                isExist = rs.getInt("isExist") == 0 ? false : true;
            }
            logger.msg(tableName+":分区"+ partitionName + (isExist == true ? " 存在!" : " 不存在！"));
            return isExist;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        }


    }


    public boolean trunTable(String orclTable) throws SQLException {
        Connection conn = null;
        String sql = new StringBuilder().append("truncate table ")
                .append(orclTable).toString();
        Statement stmt = null;
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            stmt = conn.createStatement();
            logger.msg(new StringBuilder().append("truncate table:")
                    .append(orclTable).toString());
            boolean flag = stmt.execute(sql);
            System.out.println("flag:"+flag);
            return flag;
        } catch (Exception e){
            logger.err(e);
            return false;
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }

    /*
     * @param tableName
     * @param partitionName
     * @param partitionValue
     * @param partColType
     * @throws SQLException
     */
    public void createParetition(String tableName, String partitionName,
                                 String partitionValue, String partColType) throws Exception {
        Connection conn = null;
        partitionValue = parseType(partitionValue, partColType);
        String sql = "ALTER TABLE " + tableName + " ADD PARTITION  " + partitionName + " VALUES(" + partitionValue + ") COMPRESS FOR OLTP";
        Statement stmt = null;
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);
            logger.msg(tableName+":创建分区" + partitionName+"成功");
        }catch (Exception e){
            logger.err(tableName+":创建分区"+partitionName+"失败");
            throw e;
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }

    public static String parseType(String partitionValue, String partColType) {
        partitionValue = partitionValue.replace("'", "");
        if (partColType.equals("DATE")) {
            partitionValue = "to_date('" + partitionValue + "','yyyy-mm-dd hh24:mi:ss')";
        }
        if (partColType.equals("VARCHAR2")) {
            partitionValue = "'" + partitionValue + "'";
        }
        return partitionValue;
    }

    /*
     * truncate的某个分区
     */
    public void truncatePartition(String tableName, String partition) throws Exception {
        Connection conn = null;
        String sql = "ALTER TABLE " + tableName + " truncate  partition " + partition;
        Statement stmt = null;
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);
            logger.msg("截断分区:" + tableName + "." + partition);
        }catch (Exception e){
            logger.msg("截断分区失败:" + tableName + "." + partition);
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }

    public void truncateOrAddPartition(String tableName, String partName, String partValue, String partColType) throws Exception {
        boolean flag = this.isHasPartition(tableName, partName);
        if (flag) {
            if(IS_TRUNCATE_PARTITION){
                this.truncatePartition(tableName, partName);
            }
        } else {
            try {
                this.createParetition(tableName, partName, partValue, partColType);
            }catch (Exception e){
                flag = this.isHasPartition(tableName, partName);
                if (flag) {
                    logger.err(tableName+":分区"+partName+"已经存在!");
                }
                else{
                    throw e;
                }
            }
        }
    }


    public int batchInsert(Table table, List<String[]> lines)
            throws SQLException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            String sql = buildSQL4InSert(table);
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            FieldType fieldTypes[] = table.getFTArray();
            ps = conn.prepareStatement(sql);
            for (Iterator i$ = lines.iterator(); i$.hasNext(); ps.addBatch()) {
                String line[] = (String[]) i$.next();
                for (int i = 0; i <= line.length - 1; i++)
                    setValue(ps, i + 1, line[i], fieldTypes[i]);

            }

            int count[] = ps.executeBatch();
            conn.commit();
            return count.length;
        } finally {
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        }
    }

    public int batchInsertWithExtCol(List<String[]> lines, Table table,
                                     String columnValue) throws SQLException {
        PreparedStatement ps = null;
        Connection conn = null;
        FieldType[] fieldTypes = table.getFTArray();
        try {
            String sql = buildSQL4InSert(table);
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            for (Iterator i$ = lines.iterator(); i$.hasNext();) {
                String[] line = (String[]) i$.next();
                int i = -1;
                for (i = 0; i <= line.length - 1; i++) {
                    setValue(ps, i + 1, line[i], fieldTypes[i]);
                }
                setValue(ps, i + 1, columnValue, fieldTypes[i]);
                ps.addBatch();
            }
            int[] count = ps.executeBatch();
            conn.commit();
            return count.length;
        } finally {
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        }
    }

    public void setValue(PreparedStatement ps, int paramIndex,
                         String value, FieldType fieldType) {
        try {
            switch (fieldType) {
                case FLOAT:
                    if (value == null || value.trim().isEmpty()) {
                        ps.setNull(paramIndex, Types.FLOAT);
                    } else {
                        ps.setDouble(paramIndex, Float.parseFloat(value));
                    }
                    break;
                case NUMBER:
                    if (value == null || value.trim().isEmpty()) {
                        ps.setNull(paramIndex, Types.DOUBLE);
                    } else {
                        ps.setDouble(paramIndex, Double.parseDouble(value));
                    }
                    break;
                case VARCHAR2:
                    if (value == null || value.trim().isEmpty()) {
                        ps.setNull(paramIndex, Types.VARCHAR);
                    } else {
                        ps.setString(paramIndex, value);
                    }
                    break;
                case DATE:
                    // 由于java.sql.date只会给出年月日的数据，与oracle的date不匹配
                    // 所以这里扔入Timestamp
                    if (value == null || value.trim().isEmpty()) {
                        ps.setNull(paramIndex, Types.TIMESTAMP);
                    } else {
//                        ps.setTimestamp(paramIndex, SQLDateUtils.String2Date(value));
                    }
                    break;
                case TIMESTAMP:
                    if (value == null || value.trim().isEmpty()) {
                        ps.setNull(paramIndex, Types.TIMESTAMP);
                    } else {
//                        ps.setTimestamp(paramIndex, SQLDateUtils.String2Timestamp(value));
                    }
                    break;
            }
        } catch (Exception e) {
            logger.err("第" + paramIndex + "参数," + "类型:" + fieldType
                    + " 插入的值:" + value+" 错误为："+e.getCause().toString());
        }
    }


    /*
     * 记录推送完成
     *
     * @param tableName
     * @param partitionName
     * @throws SQLException
     * @throws ParseException
     */
    public void recordPush(String tableName, String partitionName) throws SQLException {

        String sql = "insert into   " + ORACLE_PUSH_COMPLETE
                + " values(?,?,?) ";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, tableName);
            ps.setString(2, partitionName);
            ps.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
            ps.executeUpdate();
            conn.commit();
            logger.msg("记录成功 push_completed:" + tableName + "." + partitionName);
        } catch (Exception e) {
            logger.err(e.getCause().toString());
            logger.err("记录失败 push_completed:" + tableName + "." + partitionName);
        } finally {
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        }
    }

    public static String buildSQL4InSert(Table table) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ")
                .append(table.getTableName())
                .append("(")
                .append(new StringBuilder().append(table.getFNString())
                        .append(")").toString()).append(" VALUES(");

        int fieldsSize = table.fieldSize();
        for (int i = 1; i <= fieldsSize; i++) {
            if (i < fieldsSize)
                sql.append("?,");
            else {
                sql.append("?");
            }
        }
        sql.append(")");
        return sql.toString();
    }

    public void truncateTable(String orclTable) throws Exception {
        Connection conn = null;
        String sql = "truncate table " + orclTable;
        Statement stmt = null;
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);
            logger.msg("truncate table:" + orclTable);
        } finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }

    public boolean getPushRecord(Connection conn, String tableName, String partitionName) throws Exception {
        String sql = "SELECT count(*) AS isCompleted FROM  " + ORACLE_PUSH_COMPLETE + " WHERE table_name= ? AND partition_name = ?";
        boolean isCompleted = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, tableName);
            ps.setString(2, partitionName);
            rs = ps.executeQuery();
            while (rs.next()) {
                isCompleted = rs.getInt("isCompleted") == 0 ? false : true;
            }
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        }
        return isCompleted;
    }

    public void updatePushRecord(Connection conn, String tableName, String partitionName) throws Exception {
        String sql = "UPDATE  " + ORACLE_PUSH_COMPLETE + " SET completed_time= ? " +
                "WHERE table_name=? AND partition_name=?";
        PreparedStatement ps = null;
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));
            ps.setString(2, tableName);
            ps.setString(3, partitionName);
            ps.executeUpdate();
            conn.commit();
            logger.msg("update push_completed:" + tableName + "." + partitionName);
        } finally {
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        }
    }

    public void dropPartition(String tableName, String partition) throws Exception {
        String sql = "ALTER TABLE " + tableName + " DROP PARTITION " + partition;
        boolean flag = this.isHasPartition(tableName, partition);
        if (flag == true) {
            Statement stmt = null;
            Connection conn = null;
            try {
//                conn = DBConnectTool.getConnection(driver , url , user , pwd);
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                logger.msg(tableName + ":删除分区" + partition+"成功。");
            }finally {
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            }
        }
    }

    public $Logger getLogger() {
        return logger;
    }

    public void setLogger($Logger logger) {
        this.logger = logger;
    }

    public boolean isIS_TRUNCATE_PARTITION() {
        return IS_TRUNCATE_PARTITION;
    }

    public void setIS_TRUNCATE_PARTITION(boolean IS_TRUNCATE_PARTITION) {
        this.IS_TRUNCATE_PARTITION = IS_TRUNCATE_PARTITION;
    }

    public String getORACLE_PUSH_COMPLETE() {
        return ORACLE_PUSH_COMPLETE;
    }

    public void setORACLE_PUSH_COMPLETE(String ORACLE_PUSH_COMPLETE) {
        this.ORACLE_PUSH_COMPLETE = ORACLE_PUSH_COMPLETE;
    }
}
