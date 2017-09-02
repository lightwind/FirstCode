package com.lightwind.connactionmysqltest;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 功能：获取MySql连接的工具了类
 * 作者：刘洋
 * 时间：2017/8/29
 */

class ConnectionUtil {
    /**
     * 根据传入的url地址，用户名和密码获取MySql连接
     */
    static Connection openConnection(String url, String name, String password) {
        Connection connection = null;
        try {
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(url, name, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 查询数据库的方法
     */
    static void query(Connection connection, String sql) {
        if (connection == null) {
            return;
        }
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet != null && resultSet.first()) {
                int nameIndex = resultSet.findColumn("name");
                int ageIndex = resultSet.findColumn("age");
                Log.d("TAG", "name\t\t" + "age");
                while (!resultSet.isAfterLast()) {
                    Log.d("TAG", resultSet.getString(nameIndex) + "\t\t");
                    Log.d("TAG", String.valueOf(resultSet.getInt(ageIndex)));
                    resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
