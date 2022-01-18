package auto.Day19.lianxi.util;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Classname JDBCUtils
 * @Description TODO
 * @Date 1/16/22 2:45 PM
 * @Created by lhk
 */
public class JDBCUtils {
    /**
     * 链接mysql数据库
     * @return
     */
    public static Connection getConnection() {
        //定义数据库连接
        //Oracle：jdbc:oracle:thin:@localhost:1521:DBName
        //SqlServer：jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=DBName
        //MySql：jdbc:mysql://localhost:3306/DBName(数据库名称)
        String url="jdbc:mysql://mall.lemonban.com/yami_shops?useUnicode=true&characterEncoding=utf-8";
        String user="lemon";
        String password="lemon123";
        //定义数据库连接对象
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user,password);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 查询单个结果集
     * @param sql
     * @return
     */
    public static Object querySingData(String sql){
        sql=Environment.replaceParams(sql);
        Connection connection = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        Object query = null;
        try {
            query = queryRunner.query(connection, sql, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * 查询一条结果集
     * @param sql
     * @return
     */
    public static Map queryOneData(String sql){
        sql=Environment.replaceParams(sql);
        Connection connection = getConnection();
        QueryRunner queryRunner = new QueryRunner();

        Map<String, Object> query = null;
        try {
            query = queryRunner.query(connection, sql, new MapHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return query;
    }

    /**
     * 查询多条结果集
     * @param sql
     * @return
     */
    public static List<Map<String, Object>> queryTwoDatas(String sql){
        sql=Environment.replaceParams(sql);
        Connection connection = getConnection();
        QueryRunner queryRunner = new QueryRunner();

        List<Map<String, Object>> query=null;
        try {
            query = queryRunner.query(connection, sql, new MapListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return query;
    }



    public static void main(String[] args) throws SQLException {
        //创建链接
        Connection connection = getConnection();

        QueryRunner queryRunner = new QueryRunner();

        //多条数据
//        List<Map<String, Object>> query = queryRunner.query(connection, "select * from tz_sms_log;", new MapListHandler());

//        System.out.println(query);
        //一条数据
//        Map<String, Object> query = queryRunner.query(connection,
//                "select mobile_code from tz_sms_log where id=(select max(id) from tz_sms_log);", new MapHandler());
//        System.out.println(query);
        //一个数据
        String query=queryRunner.query(connection,
                "select mobile_code from tz_sms_log where id=(select max(id) from tz_sms_log);",new ScalarHandler<>());
        System.out.println(query);
    }
}
