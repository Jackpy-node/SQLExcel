package org.kpy;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther: kpy
 * @version: 1.0
 * @Package: org.kpy
 * @data: 2020-7-11 15:10
 * @discription:
 **/
public class SQLExcel {

    String sqlPath;
    String outPath;
    Connection connection;

    public SQLExcel(String outPath, String sqlPath, java.sql.Connection connection) {
        this.sqlPath = sqlPath;
        this.outPath = outPath;
        this.connection = connection;
    }

    public void exportExcel() throws Exception {

        List<String> lstStr = new ArrayList<String>();
        File file = new File(sqlPath);

        // 文件夹内文件解析成SQL
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                FileToList(lstStr, file1);
            }
        }

        // 文件解析成SQL
        if (file.isFile()) {
            FileToList(lstStr, file);
        }

        for (String sql : lstStr) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData data = resultSet.getMetaData();
            data.getColumnCount();
            System.out.println("[SQL] " + data.getColumnCount());
        }




    }

    public void FileToList(List<String> lstStr, File file) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        //声明文件大小的字节数组
        int ch;
        String str = "";
        while ((ch = fileInputStream.read()) != -1) {
            if (ch != ';') {
                str = str + String.valueOf((char) ch);
            } else {
                System.out.println("[SQL] " + str);
                lstStr.add(str);
                str = "";
            }
        }
    }
}
