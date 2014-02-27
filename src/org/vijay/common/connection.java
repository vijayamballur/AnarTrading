package org.vijay.common;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author vijay
 */
public class connection
{
    Connection con;
    public Connection conn()
    {
        try
        {
            //Class.forName("org.sqlite.JDBC");
            Class.forName("com.mysql.jdbc.Driver");
            //con=DriverManager.getConnection("jdbc:sqlite:C:\\Program Files\\InventryManagement\\DB\\inventry.db");
            //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ANAR","root","manager");
            con=DriverManager.getConnection("jdbc:mysql://192.168.1.3:3306/anar","anar","creative");
        }
        catch(Exception e)
        {}
        return con;
    }
    public static void main(String s[])
    {
        connection c=new connection();
        Connection con=c.conn();
    }
    

}
