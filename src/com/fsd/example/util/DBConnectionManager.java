package com.fsd.example.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionManager {
    private static Connection connection;

    public static Connection getConnection() throws Exception {
        if(connection==null){
            createConnection();
        }
        return connection;
    }

    public static void createConnection() throws Exception{
        Class.forName("org.h2.Driver").newInstance();
        connection= DriverManager.getConnection("jdbc:h2:~/test","sa","");
    }
}
