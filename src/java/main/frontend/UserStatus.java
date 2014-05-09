package frontend;


public class UserStatus {
    private UserStatus() {}
    public static final String USER_REG_WAIT = "Please wait, registration";
    public static final String USER_OK = "";
    public static final String USER_REG_ERROR_EXIST = "That user already exist";
    public static final String USER_WAITING_INFO ="If you are tired of waiting, you can <a href=\"/\"> exit!</a>";
    public static final String USER_REG_OK_INFO = "Registration success! You can  visit <a href=\"/timer\">timer</a> or </a><a href=\"/\"> exit!</a>";
    public static final String USER_REG_EXIST_INFO = "Registration Failed! User already exist!<BR> <a href=\"/\"> Exit!</a>";

    public static final String USER_LOG_WAIT = "Please wait, authorization";
    public static final String USER_LOG_BAD_ERROR = "Invalid login or password";
    public static final String USER_LOG_OK_INFO = "Authorization success! You can  visit <a href=\"/timer\">timer</a> or </a><a href=\"/\"> exit!</a>";
    public static final String USER_LOG_BAD_INFO = "Authorization Failed! Invalid login or password!<BR> <a href=\"/\"> Exit!</a>";

    public static final String BAD_SESSION = "INVALID SESSION <br> <a href=\"/\"> exit!</a>";

    public static final String LOGIN_ACTION = "login";
    public static final String REG_ACTION = "registration";

    public static final String USER_REG_FAILED = "Registration was failed";
    public static final String USER_LOGIN_FAILED = "Authorization was failed";
    public static final String USER_REG_FAILED_DB_INFO = "DataBase error";

    public static final String USER_INPUT_ERROR = "Input error! <BR> <a href=\"/\"> Exit!</a>";
}