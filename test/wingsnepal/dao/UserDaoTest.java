/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package wingsnepal.dao;
import org.junit.Assert;
import org.junit.Test;
import wingsnepal.model.LoginRequest;
import wingsnepal.model.UserData;

//hami dao ma bhako data lai mock gari rahaxum
/**
 *
 * @author Aayush Kharel
 */
public class UserDaoTest {
    //Instance variable
    String correctEmail = "test@gmail.com";
    String correctName = "Testt user";
    String password = "passwordfortestt";
    UserDao dao = new UserDao();
    //Before testing we have to write it:
    @Test
    public void registerWithNewDetails(){
        UserData user = new UserData(correctName, correctEmail, password);
        boolean result = dao.register(user);
        Assert.assertTrue("Register should work with unique details",result);
        
    }
    
    @Test
    public void registerWithDuplicateDeatils(){
        UserData user = new UserData(correctName, correctEmail, password);
        boolean result = dao.register(user);
        Assert.assertFalse("Register should fall with duplicate credentials", result);
    }
    @Test
    public void loginwithCorrectCreds(){
        LoginRequest req = new LoginRequest(correctemail, password);
        UserData user = dao.login(req);
        Assert.assertNotNull("User should not be null", user);
        Assert.assertEquals("Name should match", correctName, user.getName());
        Assert.assertEquals("Email should match", correctEmail, user.getPassword());
    }
    @Test
    public void loginWithInvalidCreds(){
        LoginRequest req = new LoginRequest("abc@test.com", "iouytre");
        UserData user = dao.login(req);
        Assert.assertNull("User should be null",user);
        
    }
}
