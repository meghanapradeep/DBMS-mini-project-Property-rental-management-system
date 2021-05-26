/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Meghana
 */
public class ConnectionProvider {
     public static Connection getCon()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/rental","root","root");
            return con;
            
            
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println(e);
            return null;
            
        }
    }
    
}
