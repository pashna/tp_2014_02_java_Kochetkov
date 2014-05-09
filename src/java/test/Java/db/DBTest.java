package Java.db;

/*
Created by p.Kochetkov on 28.03.14.
 */

import db.AccountService;
import messageSystem.MessageSystem;
import org.junit.*;
import exception.*;
import java.util.*;

import java.sql.SQLException;

public class DBTest {
    private AccountService acManager = new AccountService();
    private static final String T_USER = getRandomString(10);
    private static final String T_PASS = getRandomString(15);

    /*private static String getRandomString(int count) {
        StringBuilder string = new StringBuilder();
        for(int i = 0; i < count ; ++i ) {
            string.append((char)(Math.random()*(128 - 32) + 32));
        }
        System.out.println(string.toString());
        return string.toString();
    }*/

    public static String getRandomString(int length)
    {
        Random random = new Random();
        char from[] = "abcdefghijklmnopqrstuvwxyzABCEFGHIGKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(from[random.nextInt((from.length))]);
        }
        return result.toString();
    }

    @BeforeClass
    public static void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        try {
            acManager.deleteUser(T_USER);
        } catch (Exception e) {
        }
    }

    @Test
    public void TestRegisterOk() throws Exception{
        boolean result;
        try {
            if (acManager.regUser(T_USER, T_PASS) == 1)
                result = true;
            else
                result = false;
        } catch (SQLException e) {
            result = false;
        }
        Assert.assertTrue(result);
    }

    @Test
    public void TestRegisterFail() throws Exception {
        boolean result;
        try {
            acManager.regUser(T_USER, T_PASS);
            if (acManager.regUser(T_USER, T_PASS) == 1)
                result = false;
            else
                result = true;
        } catch (SQLException e) {
            result = true;
        }
        Assert.assertTrue(result);
    }

    @Test
    public void TestLoginOk() throws Exception{
        boolean result;
        try {
            acManager.regUser(T_USER, T_PASS);
            if (acManager.logUser(T_USER, T_PASS) == 1)
                result = true;
            else
                result = false;
        } catch (SQLException e) {
            result = false;
        }
        Assert.assertTrue(result);
    }

    @Test
    public void TestLoginFail() throws Exception {
        boolean result;
        try {
            acManager.regUser(T_USER, T_PASS);
            if (acManager.logUser(T_USER, T_PASS+"123")==1)
                result = false;
            else
                result = true;
        } catch (SQLException e) {
            result = true;
        }
        Assert.assertTrue(result);
    }

/*    @Test
    public void TestDeleteOk() throws Exception{
        boolean result = true;
        acManager.regUser(T_USER, T_PASS);
        try {
            acManager.deleteUser(T_USER);
        } catch (SQLException|AccountServiceException e) {
            result = false;
        }
        Assert.assertTrue(result);
    }
*/
    @Test
    public void TestDeleteFail() throws Exception{
        boolean result = false;
        acManager.regUser(T_USER, T_PASS);
        try {
            acManager.deleteUser(T_USER+"123");
        } catch (SQLException|AccountServiceException e) {
            result = true;
        }
        Assert.assertTrue(result);
    }

    @Test
    public void TestEmptyDataOk() throws Exception {
        boolean result;
        try {
            if (acManager.regUser("", "") == -2)
                result = true;
            else
                result = false;
        } catch (SQLException e) {
            result = true;
        }
        Assert.assertTrue(result);
    }
}
