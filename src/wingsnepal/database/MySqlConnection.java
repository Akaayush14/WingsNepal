/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Aayush Kharel
 */
public class MySqlConnection implements DbConnection{
    
    @Override
    public Connection openConnection() {
        try{
            String username = "root";
            String password = "@Akfalcon401040";
            String database = "wingsnepal";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection;
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/" + database + "?useSSL=false&serverTimezone=UTC",
                username,
                password
            );
            System.out.println("Database connected");
            return connection;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }


    @Override
    public void closeConnection(Connection conn) {
        try{
            if(conn !=null && !conn.isClosed()){
                conn.close();
                System.out.println("Connection closed");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try{
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            return result;
        }catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public int executeUpdate(Connection conn, String query) {
          try{
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);
            return result;
        }catch(SQLException e){
            System.out.println(e);
            return -1;
        }
    } 
    
}
