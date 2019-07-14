package com.g12.autodoc.util.db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @Author sjx
 * @Classname DataBaseUtil
 * @Description 数据库工具类
 * @Date 2019/7/14 2:17
 */
public class DataBaseUtil {

    public static Connection getConnect(String dbType, String host, String port, String database, String user, String pwd) throws Exception {

        /*url参数
          jdbc:postgresql://10.1.192.171:5432/bpm
          jdbc:mysql://106.14.220.188:3306/ttxg
          thin模式，orcl为服务名或sid
          jdbc:oracle:thin:@localhost:1521:xxx
        */
        String url = DriverUtil.getCurrentDriverUrl(dbType, host, port, database);
        Properties connProp = new Properties();
        connProp.setProperty("user", user);
        connProp.setProperty("password", pwd);
        //设置可以获取remarks信息
        connProp.setProperty("remarks", "true");
        //设置可以获取tables remarks信息
        connProp.setProperty("useInformationSchema", "true");
        Connection conn = DriverManager.getConnection(url, connProp);
        return conn;
    }



}
