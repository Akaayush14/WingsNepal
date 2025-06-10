/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepalController;

import wingsnepal.model.UserData;
import wingsnepal.view.AdminDashboard;

/**
 *
 * @author ACER
 */
public class AdminDashboardController {
     private UserData user;
    AdminDashboard view ;

    public AdminDashboardController(AdminDashboard view,UserData user){
        this.view= view;
        this.user = user;
        String name = user.getName();
        this.view.getWelcomeLabel().setText("Welcome, "+name);
        
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
    }
}

}
