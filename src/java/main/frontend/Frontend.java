package frontend;

import messageSystem.*;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import utils.*;

public class Frontend extends HttpServlet implements Runnable, Abonent {

    private MessageSystem ms;
    private Address address;
    private Map<String, UserSession> sessionIdToUserSession = new HashMap<>();


    public Frontend(MessageSystem ms) {
        this.ms = ms;
        this.address = new Address();
        ms.addService(this);
    }

    public void setId(String sessionId, Long userId) {
        UserSession userSession = sessionIdToUserSession.get(sessionId);
        if (userSession == null) {
            System.out.append("Can't find user session for: ").append(sessionId);
            return;
        }
        userSession.setUserId(userId);
    }

    public Address getAddress() {
        return address;
    }

    public static String getTime() {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("HH.mm.ss");
        return formatter.format(date);
    }



    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getPathInfo().equals(Pages.TIMER_PAGE)) {
            doTimerPage(request, response);
        }

        if (request.getPathInfo().equals(Pages.REG_PAGE)) {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("Status", "");
            response.getWriter().println(PageGenerator.getPage("registration.tml", pageVariables));
        }

        if (request.getPathInfo().equals(Pages.USER_PAGE)) {
            doWaiting(request, response);
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        request.getSession().invalidate();

        if (request.getPathInfo().equals(Pages.REG_PAGE)) {
            try {
                doRegistration(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(Pages.REG_PAGE);
            }
        }

        if (request.getPathInfo().equals(Pages.MAIN_PEG)) {
            try {
                doLogin(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(Pages.MAIN_PEG);
            }
        }
    }

    public void run() {
        while (true) {
            ms.execForAbonent(this);
            TimeHelper.sleep(300);
        }
    }

    private void doWaiting(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        UserSession userSession = sessionIdToUserSession.get(session.getId());
        Map<String, Object> pageVariables = new HashMap<>();
        String action = userSession.getAction();

        if (userSession == null) { // Нет сессии
            pageVariables.put("info", "");
            pageVariables.put("userState", UserStatus.BAD_SESSION);
            pageVariables.put("refreshPeriod", "9999999");
            response.getWriter().println(PageGenerator.getPage("waiting.tml", pageVariables));
            return;
        }

        if (userSession.getUserId() == null) { // Идет регистрация
            if (action.equals(UserStatus.REG_ACTION))
                pageVariables.put("userState", UserStatus.USER_REG_WAIT);
            if (action.equals(UserStatus.LOGIN_ACTION))
                pageVariables.put("userState", UserStatus.USER_LOG_WAIT);
            pageVariables.put("info", UserStatus.USER_WAITING_INFO);
            pageVariables.put("refreshPeriod",  "1000");
            response.getWriter().println(PageGenerator.getPage("waiting.tml", pageVariables));
            return;
        }

        if (userSession.getUserId() == -1) { // Ошибка имени пользователя
            if (action.equals(UserStatus.REG_ACTION)) {
                pageVariables.put("userState", UserStatus.USER_REG_ERROR_EXIST);
                pageVariables.put("info", UserStatus.USER_REG_EXIST_INFO);
            }
            if (action.equals(UserStatus.LOGIN_ACTION)) {
                pageVariables.put("userState", UserStatus.USER_LOG_BAD_ERROR);
                pageVariables.put("info", UserStatus.USER_LOG_BAD_INFO);
            }

            pageVariables.put("refreshPeriod",  "99999999");
            response.getWriter().println(PageGenerator.getPage("waiting.tml", pageVariables));
            return;
        }

        if (userSession.getUserId() == -2) { // Ошибка ввода
            if (action.equals(UserStatus.REG_ACTION))
                pageVariables.put("userState", UserStatus.USER_REG_FAILED);
            if (action.equals(UserStatus.LOGIN_ACTION))
                pageVariables.put("userState", UserStatus.USER_LOGIN_FAILED);

            pageVariables.put("info", UserStatus.USER_INPUT_ERROR);
            pageVariables.put("refreshPeriod",  "99999999");
            response.getWriter().println(PageGenerator.getPage("waiting.tml", pageVariables));
            return;
        }

        if (userSession.getUserId() == -3) { // Ошибка базы данных или нулевое поле
            pageVariables.put("userState", UserStatus.USER_REG_FAILED);
            pageVariables.put("info", UserStatus.USER_REG_FAILED_DB_INFO);

            pageVariables.put("refreshPeriod",  "99999999");
            response.getWriter().println(PageGenerator.getPage("waiting.tml", pageVariables));
            return;
        }

        pageVariables.put("userState", UserStatus.USER_OK);
        if (action.equals(UserStatus.REG_ACTION)) {
            pageVariables.put("info", UserStatus.USER_REG_OK_INFO);
        }
        if (action.equals(UserStatus.LOGIN_ACTION)) {
            pageVariables.put("info", UserStatus.USER_LOG_OK_INFO);
        }
        pageVariables.put("refreshPeriod", "99999999");
        response.getWriter().println(PageGenerator.getPage("waiting.tml", pageVariables));
    }

    private void doTimerPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        UserSession userSession = sessionIdToUserSession.get(session.getId());
        Map<String, Object> pageVariables = new HashMap<>();

        if ( (userSession.getUserId() == -1)||
                (userSession == null) ||
                userSession.getUserId() == null) {
            response.sendRedirect(Pages.MAIN_PEG);
            return;
        }

        pageVariables.put("refreshPeriod", "1000");
        pageVariables.put("serverTime", getTime());
        pageVariables.put("Hello", "Hello, "+userSession.getName()+"! Your id = "+userSession.getUserId());
        response.getWriter().println(PageGenerator.getPage("timer.tml", pageVariables));

    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        String sessionId = request.getSession().getId();
        UserSession userSession = new UserSession(sessionId, login, ms.getAddressService(), UserStatus.LOGIN_ACTION);
        sessionIdToUserSession.put(sessionId, userSession);

        Address frontendAddress = getAddress();
        Address accountServiceAddress = userSession.getAccountService();

        ms.sendMessage(new MsgLogin(frontendAddress, accountServiceAddress, login, pass, sessionId));
        response.sendRedirect(Pages.USER_PAGE);
    }

    private void doRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        String sessionId = request.getSession().getId();
        UserSession userSession = new UserSession(sessionId, login, ms.getAddressService(), UserStatus.REG_ACTION);
        sessionIdToUserSession.put(sessionId, userSession);

        Address frontendAddress = getAddress();
        Address accountServiceAddress = userSession.getAccountService();

        ms.sendMessage(new MsgRegister(frontendAddress, accountServiceAddress, login, pass, sessionId));
        response.sendRedirect(Pages.USER_PAGE);

    }
}