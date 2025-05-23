/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Aayush Kharel
 */
public class UserData {
    //private attributes
    //public methods
    private String name;
    private String email;
    private String password;


    //Making a constructor:
    public UserData (String name, String email, String password){
        this.name=name;
        this.email=email;
        this.password=password;
    }
   
    // Setters: kunai attributes ma gayera value set gardine. Aba set garesi kei return garnu pardaian tyasaile void huna!(Also a method)
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    // Getter: aba set garesu get pani garnu paryo matlab value return garnu paryo(simply a function it is!)
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
}
    

