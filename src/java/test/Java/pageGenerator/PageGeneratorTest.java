package Java.pageGenerator;

import Java.db.DBTest;
import db.AccountService;
import exception.AccountServiceException;
import frontend.Frontend;
import frontend.Pages;
import messageSystem.MessageSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.mockito.Mockito.*;

/**
 Created by p.Kochetkov on 29.03.14.
 */
/*
public class PageGeneratorTest {
    private Frontend frontend;
    private StringWriter stringWriter;

    private static final String T_USER = DBTest.getRandomString(10);
    private static final String T_PASS = DBTest.getRandomString(15);

    final static private HttpServletRequest REQUEST = mock(HttpServletRequest.class);
    final static private HttpServletResponse RESPONSE = mock(HttpServletResponse.class);
    final static private HttpSession SESSION = mock(HttpSession.class);
    final static private AccountService ACCOUNT_SERVICE = mock(AccountService.class);

    @Before
    public void setUp() throws Exception {
        MessageSystem ms = new MessageSystem();
        frontend = new Frontend(ms);
        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(REQUEST.getSession()).thenReturn(SESSION);
        when(REQUEST.getParameter("login")).thenReturn(T_USER);
        when(REQUEST.getParameter("pass")).thenReturn(T_PASS);
        when(RESPONSE.getWriter()).thenReturn(writer);
    }

    @Test
    public void testGetIndexPage() throws Exception {
        when(REQUEST.getPathInfo()).thenReturn(Pages.MAIN_PEG);
        frontend.doGet(REQUEST,RESPONSE);
        Assert.assertTrue(stringWriter.toString().contains("Индекс"));
    }

    @Test
    public void testFailAuth() throws Exception {
        when(REQUEST.getParameter("username")).thenReturn(T_USER);
        when(REQUEST.getParameter("password")).thenReturn(T_PASS);
        when(REQUEST.getPathInfo()).thenReturn(Pages.MAIN_PEG);
        doThrow(new AccountServiceException("fail auth")).when(ACCOUNT_SERVICE).logUser(T_USER,T_PASS);
        frontend.doPost(REQUEST, RESPONSE);
        System.out.println("!!!!!");
        System.out.println(stringWriter.toString());
        System.out.println("!!!!");
//        Assert.assertTrue(stringWriter.toString().contains(""));
    }
    @Test
    public void testFailReg() throws Exception {
        when(REQUEST.getParameter("username")).thenReturn(T_USER);
        when(REQUEST.getParameter("password")).thenReturn(T_PASS);
        when(REQUEST.getPathInfo()).thenReturn(Pages.REG_PAGE);
        doThrow(new AccountServiceException("fail reg")).when(ACCOUNT_SERVICE).regUser(T_USER,T_PASS);
        frontend.doPost(REQUEST, RESPONSE);
        Assert.assertTrue(stringWriter.toString().contains("Регистрация"));
    }

}*/