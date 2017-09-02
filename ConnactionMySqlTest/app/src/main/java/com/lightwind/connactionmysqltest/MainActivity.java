package com.lightwind.connactionmysqltest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.query_mysql).setOnClickListener(this);
        findViewById(R.id.query_mysql2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3表联查
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Log.d("TAG", "加载驱动成功");
                            Connection connection = DriverManager.getConnection
                                    ("jdbc:mysql://192.168.2.222:3306/db_xwmes", "root",
                                            "ahxiuwu@5.5");
                            Log.d("TAG", "获取连接");
                            String sql = "SELECT workbenchCode,inspectorCheckResult FROM " +
                                    "flowcard_detail fd LEFT JOIN work_order_scheduled_detail " +
                                    "wosd ON fd.`workOrderScheduledDetailId`=wosd" +
                                    ".`workOrderScheduledDetailId`";
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery(sql);
                            while (resultSet.next()) {
                                int inspectorCheckResult = resultSet.getInt(("inspectorCheckResult"));
                                int workbenchCode = resultSet.getInt(("workbenchCode"));
                                Log.d("TAG", inspectorCheckResult + "---" + workbenchCode + "\n");
                            }
                            connection.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Class.forName("com.mysql.jdbc.Driver");
//                    Log.d("TAG", "加载驱动成功");
//                    Connection connection = DriverManager.getConnection
//                            ("jdbc:mysql://192.168.2.222:3306/db_xwmes", "root", "ahxiuwu@5.5");
//                    Log.d("TAG", "获取连接");
//                    String sql = "select * from work_order_scheduled";
//                    Statement statement = connection.createStatement();
//                    ResultSet resultSet = statement.executeQuery(sql);
//                    while (resultSet.next()) {
//                        String workOrderNo = resultSet.getString(resultSet.findColumn
//                                ("workOrderNo"));
//                        Log.d("TAG", workOrderNo + "\t");
//                    }
//                    connection.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
}
