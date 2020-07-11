package org.kpy;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * @auther: kpy
 * @version: 1.0
 * @Package: org.kpy
 * @data: 2020-7-10 18:57
 * @discription:
 **/
public class MainForm {
    private JPanel MainPane;
    private JLabel label1;
    private JComboBox comboBox1;
    private JTextField TextField;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton SQLButton;
    private JButton ChooseButton;
    private JButton OutButton;
    public JTextArea textArea1;
    private JScrollPane ScrollPane;
    private JButton ResetButton;

    private String sComboBox1;
    private String sSchema_TextField;
    private String sIP_TextField2;
    private String sUser_textField3;
    private String sPassWDd_textField4;
    private String SQL_textField5;
    private String Out_textField6;
    private String Out_path;
    private String SQL_path;


    public MainForm() {

        sComboBox1 = (String) comboBox1.getSelectedItem();
        sSchema_TextField = TextField.getText();
        sIP_TextField2 = textField2.getText();
        sUser_textField3 = textField3.getText();
        sPassWDd_textField4 = textField4.getText();

        /*
        数据库类型
         */
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    sComboBox1 = (String) comboBox1.getSelectedItem();
                    textArea1.append("\n");
                    textArea1.append("数据库类型：" + sComboBox1);
                    textArea1.append("\n");
                }
            }
        });

        /*
        数据库类型
         */
        comboBox1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                sComboBox1 = (String) comboBox1.getSelectedItem();
                textArea1.append("\n");
                textArea1.append("数据库类型：" + sComboBox1);
                textArea1.append("\n");
            }
        });

        /*
        数据库实例
         */
        TextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                sSchema_TextField = TextField.getText();
                if (sSchema_TextField != null && sSchema_TextField.length() != 0) {
                    textArea1.append("数据库Schema："+sSchema_TextField);
                    textArea1.append("\n");
                }
            }
        });

        /*
        数据库IP
         */
        textField2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                sIP_TextField2 = textField2.getText();
                if (sIP_TextField2 != null && sIP_TextField2.length() != 0) {
                    textArea1.append("数据库IP："+sIP_TextField2);
                    textArea1.append("\n");
                }
            }
        });

        /*
        数据库用户名
         */
        textField3.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                sUser_textField3 = textField3.getText();
                if (sUser_textField3 != null && sUser_textField3.length() != 0) {
                    textArea1.append("数据库用户名："+sUser_textField3);
                    textArea1.append("\n");
                }
            }
        });

        /*
        数据库密码
         */
        textField4.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                sPassWDd_textField4 = textField4.getText();
                if (sPassWDd_textField4 != null && sPassWDd_textField4.length() != 0) {
                    textArea1.append("数据库密码："+sPassWDd_textField4);
                    textArea1.append("\n");
                }
            }
        });


        FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
        textField6.setText(fsv.getHomeDirectory().getAbsolutePath());


        /*
        SQL脚本路径
         */
        SQLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
                fileChooser.setDialogTitle("请选择SQL脚本");
                fileChooser.setApproveButtonText("确定");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int result = fileChooser.showOpenDialog(MainPane);
                if (JFileChooser.APPROVE_OPTION == result) {
                    SQL_path=fileChooser.getSelectedFile().getPath();
                    textField5.setText(SQL_path);
                    textArea1.append("SQL脚本路径："+SQL_path);
                    textArea1.append("\n");
                }
            }
        });

        /*
        导出路径选择
         */
        ChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
                fileChooser.setDialogTitle("请选择导出路径...");
                fileChooser.setApproveButtonText("确定");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(MainPane);
                if (JFileChooser.APPROVE_OPTION == result) {
                    Out_path=fileChooser.getSelectedFile().getPath();
                    textField6.setText(Out_path);
                    textArea1.append("导出路径："+Out_path);
                    textArea1.append("\n");
                }
            }
        });

        /*
        导出
         */
        OutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sSchema_TextField != null && sSchema_TextField.length()!=0){

                    //JOptionPane.showMessageDialog(MainPane, "数据库连接失败");

                    // 数据库连接工厂
                    JdbcFactory JdbcFactory = new JdbcFactory(sUser_textField3, sPassWDd_textField4, sIP_TextField2, sSchema_TextField, sComboBox1,textArea1);

                    // 数据库连接
                    try {
                        JdbcFactory.DriversFactory(Out_path, SQL_path);
                    } catch (Exception ex) {
                        textArea1.append(String.valueOf(ex.getMessage()));
                        JOptionPane.showMessageDialog(MainPane, "数据库连接失败");
                    }

                }
            }
        });

        /*
        重置
         */
        ResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
                comboBox1.setSelectedIndex(0);
                textField5.setText("");
            }
        });

    }

    public static void main(String[] args) throws Exception {

        FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
        File file = fsv.getHomeDirectory();
        String path = file.getAbsolutePath();

        PrintStream print=new PrintStream(file.getAbsolutePath() + File.separator + "log.txt");  //写好输出位置文件；
        System.setOut(print);

        JFrame frame = new JFrame("SQLExcel");
        frame.setContentPane(new MainForm().MainPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500,400,500,600);
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
        frame.setVisible(true);
    }
}

/**
 * DAO服务.数据源.oradb[0].id=default
 * DAO服务.数据源.oradb[0].测试SQL=select 1 from dual
 * DAO服务.数据源.oradb[0].地址及实例=3.1.12.60:1521:cbsdevdb
 * DAO服务.数据源.oradb[0].用户名=vlog
 * DAO服务.数据源.oradb[0].密码={{enc:vlog}}
 */
