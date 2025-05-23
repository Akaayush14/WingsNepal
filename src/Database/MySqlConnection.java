/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;
import java.sql.*;
/**
 *
 * @author Aayush Kharel
 */

public class MySqlConnection implements DbConnection{

    @Override
    public Connection openConnection() {
        try{
            // workbench ko root nai hunxa usually
            String username = "WingsNepal";  
            // password set to open projects in workbench
            String password = "@Akfalcon401040";
            // database name create in my sql
            String database = "marks";
            Class.forName("conn.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database, username, password);
            return conn;
            
        }catch(Exception e){
            return null;
            
        }
    }

    @Override
    public void closeConnection(Connection conn) {
        
    }
}
    

