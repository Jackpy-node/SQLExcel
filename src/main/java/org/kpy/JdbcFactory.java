package org.kpy;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @auther: kpy
 * @version: 1.0
 * @Package: org.kpy
 * @data: 2020-7-11 11:51
 * @discription:
 **/
public class JdbcFactory {

    String username;
    String password;
    String ip;
    String schema;
    String driver;
    JTextArea textArea1;

    public JdbcFactory(String username, String password, String ip, String schema, String driver, JTextArea textArea1) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.schema = schema;
        this.driver = driver;
        this.textArea1 = textArea1;
    }

    public void DriversFactory(String Out_path, String SQL_path) throws Exception {
        //Oracle数据库
        if ("oracle".toLowerCase().equals(driver.toLowerCase())) {
            oracleDriver(Out_path, SQL_path);
        }

        //Mysql数据库
        if ("mysql".toLowerCase().equals(driver.toLowerCase())) {
            mysqlDriver(Out_path, SQL_path);
        }
    }

    // Oracle数据库连接
    public void oracleDriver(String Out_path, String SQL_path) throws Exception {
        //1.加载驱动程序
        textArea1.append("\n");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        textArea1.append("加载数据库驱动：oracle.jdbc.driver.OracleDriver");
        textArea1.append("\n");

        //"jdbc:oracle:thin:@"+ip+":"+port+":"+database;

        //2.设置URL
        String URL = "jdbc:oracle:thin:@" + ip + ":1521:" + schema;
        textArea1.append("数据库URL："+URL);
        textArea1.append("\n");

        //3.获得数据连接
        Connection conn = DriverManager.getConnection(URL, username, password);
        textArea1.append("创建数据库成功");
        textArea1.append("\n");

        SQLExcel sqlExcel = new SQLExcel(Out_path, SQL_path, conn);
        sqlExcel.exportExcel();
    }

    // MySQL数据库连接
    public void mysqlDriver(String Out_path, String SQL_path) throws Exception {

        //1.加载驱动程序
        textArea1.append("\n");
        Class.forName("com.mysql.cj.jdbc.Driver");
        textArea1.append("加载数据库驱动：com.mysql.cj.jdbc.Driver");
        textArea1.append("\n");

        //2.设置URL
        String URL = "jdbc:mysql://" + ip + ":3306/" + schema;
        textArea1.append("数据库URL："+URL);
        textArea1.append("\n");

        //3.获得数据连接
        Connection conn = DriverManager.getConnection(URL, username, password);
        textArea1.append("创建数据库成功");
        textArea1.append("\n");

        SQLExcel sqlExcel = new SQLExcel(Out_path, SQL_path, conn);
        sqlExcel.exportExcel();
    }

}
