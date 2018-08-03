package org.apache.jmeter.xuzhihua;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.jmeter.util.JMeterUtils;

public class MysqlUtil {

	public static void main(String[] args) {

	}

	public static Connection getConnection() throws Exception {  
       
  
        // 1. 准备获取连接的 4 个字符串: user, password, url, jdbcDriver  
        String user = JMeterUtils.getPropDefault("user", "test");  
        String password = JMeterUtils.getPropDefault("password", "test");  
        String url= JMeterUtils.getPropDefault("url", "url");  
        String jdbcDriver= JMeterUtils.getPropDefault("jdbcDriver", "");  
  
        // 2. 加载驱动: Class.forName(driverClass)  
        Class.forName(jdbcDriver);  
  
        // 3.获取数据库连接  
        Connection connection = DriverManager.getConnection(url, user,  
                password);  
        return connection;  
    }  
	
	public static void close(ResultSet resultSet, Statement statement,  
            Connection connection) {  
  
        if (resultSet != null) {  
            try {  
                resultSet.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
  
        if (statement != null) {  
            try {  
                statement.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
  
        if (connection != null) {  
            try {  
                connection.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    } 
}
