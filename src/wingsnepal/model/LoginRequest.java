/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.model;

/**
 *
 * @author ACER
 */
public class LoginRequest {
     private String email;
    private String password;
    public LoginRequest(String email,String password){
        this.email=email;
        this.password=password;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
}

