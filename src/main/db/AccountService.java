package db;


import java.sql.SQLException;
import exception.*;
/*
Created by p.Kochetkov on 08.03.14.
 */

public class AccountService implements AccountManagerInterface {
    private UserDAO dao;

    public AccountService() {
        dao = new UserDAO(DataBaseConnector.getConnection());
    }

    @Override
    public void regUser(String login, String pass) throws AccountServiceException, SQLException, EmptyDataException{
        isCorrectLogPas(login, pass);
        if (dao.findUser(login) != null) throw new AccountServiceException("User already exist");
        dao.addUser(new UserDataSet(login, pass));
    }

    @Override
    public void logUser(String login, String pass) throws AccountServiceException, SQLException, EmptyDataException {
        isCorrectLogPas(login, pass);
        UserDataSet user = dao.findUser(login);
        if ((user == null)||(!user.getPassword().equals(pass))) throw new AccountServiceException("Wrong password");
    }
    @Override
    public void deleteUser(String login) throws AccountServiceException, EmptyDataException, SQLException {
        isCorrectLog(login);
        if (!dao.deleteUser(login)) throw new AccountServiceException("User was not removed");
    }

    private void isCorrectLogPas(String login, String pass) throws EmptyDataException{
        if ((login == null)||(login.equals(""))||(pass.equals(""))||(pass == null))
            throw new EmptyDataException("empty string");
    }

    private void isCorrectLog(String login) throws EmptyDataException{
        if ((login == null)||(login.equals("")))
            throw new EmptyDataException("empty string");
    }

}
