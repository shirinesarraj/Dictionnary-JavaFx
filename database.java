/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginsignup;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Hp
 */
public class database {
    public static Connection connect()
    {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms2","root","");
           return connect;       
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
}
