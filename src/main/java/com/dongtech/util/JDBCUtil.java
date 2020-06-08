package com.dongtech.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @Description: JDBC工具类
 */
public class JDBCUtil {
    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            //1.新建属性集对象
            Properties properties = new Properties();
            //2通过反射，新建字符输入流，读取properties文件
            InputStream input = JDBCUtil.class.getClassLoader().getResourceAsStream("application.properties");
            //3.将输入流中读取到的属性，加载到properties属性集对象中
            properties.load(input);
            //4.根据键，获取properties中对应的值
            driver = properties.getProperty("spring.datasource.driver-class-name");
            url = properties.getProperty("spring.datasource.url");
            user = properties.getProperty("spring.datasource.username");
            password = properties.getProperty("spring.datasource.password");
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 建立MySQL连接
    public static  Connection getMysqlConn() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        close(JDBCUtil.getMysqlConn());

    }

    //关闭连接3
    public static void close(ResultSet rs, Statement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //关闭连接2
    public static void close(Statement ps, Connection conn) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //关闭连接1
    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
