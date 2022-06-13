package com.news.dao;

import com.news.util.db.DbUtils;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @Author 谭志扬
 * @Date 2022/5/21
 */
public class BaseDao {

    /**数据库连接地址 */
    private static String url;
    /**用户名 */
    private static String username;
    /**密码 */
    private static String password;
    /**mysql驱动类 */
    private static String driver;

    /**
     * 私有构造方法
     * 作用：无法被实例
     */
//    private BaseDao(){}

    //使用静态代码块加载驱动
    static {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("F:\\MyProjext\\JAVA\\maven_workspace\\news\\src\\main\\java\\com\\news\\util\\db\\db-config.properties"));
            driver = prop.getProperty("driver");
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取数据库连接
     */
    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("获取连接失败!");
        }
        return conn;
    }
    /**
     * 关闭数据库连接
     */
    public static void closeAll(ResultSet rs, Statement stat, Connection conn) {
        try {
            if(rs != null) {
                rs.close();
            }
            if(stat != null) {
                stat.close();
            }
            if(conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("关闭数据库异常");
        }
    }

    /**
     * 执行基本增删改操作
     */
    public static int executeUpdate(String sql, String[] args) {
        int index = -1;
        Connection conn = getConn();
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql);
            //获取占位符个数
            int argsLen = DbUtils.getParaLength(sql);
            //设置占位符参数
            for (int i = 0; i < argsLen; i++) {
                prep.setObject((i + 1), args[i]);
            }
            //执行相关操作
            index = prep.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, prep, conn);
        }
        //返回影响的行数
        return index;
    }

    /**
     * 执行基本查询操作
     */
    /*public static List executeQuery(String sql, String[] args) {
        List list = null;
        Connection conn = getConn();
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql);
            //获取占位符个数
            int argsLen = DbUtils.getParaLength(sql);
            //设置占位符参数
            for (int i = 0; i < argsLen; i++) {
                prep.setObject((i + 1), args[i]);
            }
            //执行查询操作
            ResultSet rs = prep.executeQuery();
            //遍历结果集
            list = new ArrayList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, prep, conn);
        }
        //返回影响的行数
        return index;
    }*/
}
