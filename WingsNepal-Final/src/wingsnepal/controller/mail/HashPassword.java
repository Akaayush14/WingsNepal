/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.controller.mail;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Aayush Kharel
 */
public class HashPassword {
    public static void main(String[] args) {
        String password = "arbaz123"; // <-- Replace with your actual admin password
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("BCrypt hash: " + hashed);
    }
}

