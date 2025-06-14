/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package wingsnepal.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wingsnepal.model.LoginRequest;
import wingsnepal.model.Registration;
import wingsnepal.model.ResetRequest;

/**
 *
 * @author Momtaj
 */
public class UserDaoTest {
    
    public UserDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of register method, of class UserDao.
     */
    @Test
    public void testRegister() {
        System.out.println("register");
        Registration user = null;
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.register(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class UserDao.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        LoginRequest loginData = null;
        UserDao instance = new UserDao();
        Registration expResult = null;
        Registration result = instance.login(loginData);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkEmail method, of class UserDao.
     */
    @Test
    public void testCheckEmail() {
        System.out.println("checkEmail");
        String email = "";
        UserDao instance = new UserDao();
        Registration expResult = null;
        Registration result = instance.checkEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePassword method, of class UserDao.
     */
    @Test
    public void testUpdatePassword() {
        System.out.println("updatePassword");
        ResetRequest reset = null;
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.updatePassword(reset);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
