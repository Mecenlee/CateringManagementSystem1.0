package com.swu.lmc.HMS.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 李敏灿
 * @version 1.0
 */

public class Test {
    public static void main(String[] args) throws SQLException {
        //测试Utility工具类
        System.out.print("请输入一个整数：");
        int i = Utility.readInt();
        System.out.println("输入的整数为："+i);

        //测试JDBCUtilsDruid工具类
        Connection connection = JDBCUtilsByDruid.getConnection();
        System.out.println(connection);
    }
}
