/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;
import wingsnepal.model.Registration;
import java.sql.*;
import wingsnepal.model.LoginRequest;
import wingsnepal.model.ResetRequest;
import wingsnepal.database.MySqlConnection;
/**
 *
 * @author ACER
 */
public class UserDao {
     MySqlConnection mySql = new MySqlConnection();
    public boolean register(Registration user){
      String query="INSERT INTO users(name,email,password) VALUES(?,?,?)";  
      Connection conn = mySql.openConnection();
      try{
          PreparedStatement stmnt = conn.prepareStatement(query);
          stmnt.setString(1, user.getName());
          stmnt.setString(2,user.getEmail());
          stmnt.setString(3,user.getPassword());
          int result = stmnt.executeUpdate();
          boolean value= result>0;
          return value;
      } catch(Exception e){
          return false;
      } finally{
          mySql.closeConnection(conn);
      }
    }
    
    public Registration login(LoginRequest loginData){
        String query= "SELECT * FROM users WHERE email=? and password=?";
        Connection conn= mySql.openConnection();
        try{
            PreparedStatement stmnt= conn.prepareStatement(query);
            stmnt.setString(1,loginData.getEmail());
            stmnt.setString(2,loginData.getPassword());
            // always use excuteQuery for select
            ResultSet result= stmnt.executeQuery();
            System.out.println("Result:"+result);
//            result returns row if email and password matches else it returns null;
            if (result.next()){
//                retrieving value from resultset
                String name = result.getString("name"); // use the name of column in database
                String id = result.getString("id");
                String email = result.getString("email");
                String password = result.getString("password");
//                wrapping the data in model
                Registration user = new Registration(id,name,email,password);
                return user;
            } else{
                return null;
            }
        } catch(Exception e){
            System.out.println("Exception "+e);
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
        
    }
    
    public Registration checkEmail(String email){
        String query = "SELECT * from users where email=?";
        Connection conn= mySql.openConnection();
        try{
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1,email);
            ResultSet result = stmnt.executeQuery();
            if(result.next()){
                String name = result.getString("name"); // use the name of column in database
                String id = result.getString("id");
                String password = result.getString("password");
                Registration user = new Registration(id,name,email,password);
                return user;
            } else {
                return null;
            }
        } catch (Exception e){
            return null;
        } finally{
            mySql.closeConnection(conn);
        }
    }
    
    public boolean updatePassword(ResetRequest reset){
        String query= "UPDATE users SET password=? where email=?";// code to update password
        Connection conn = mySql.openConnection();
        try{
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1,reset.getPassword());           
            stmnt.setString(2,reset.getEmail());
            int result = stmnt.executeUpdate();
            return result>0;
        } catch(Exception e){
            return false;
        } finally{
            mySql.closeConnection(conn);
        }
    }

   
}


