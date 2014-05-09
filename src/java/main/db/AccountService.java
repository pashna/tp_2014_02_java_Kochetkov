package db;

import java.sql.SQLException;

import exception.*;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.MessageSystem;
import utils.*;
/*
Created by p.Kochetkov on 08.03.14.
 */

public class AccountService implements AccountServiceInterface, Abonent, Runnable {
    private UserDAO dao;
    private Address address;
    private MessageSystem ms;

    public AccountService(MessageSystem ms) {
        dao = new UserDAO(DataBaseConnector.getConnection());
        this.ms = ms;
        this.address = new Address();
        ms.addService(this);
        ms.getAddressService().setAccountService(address);
    }

    public AccountService() {
        dao = new UserDAO(DataBaseConnector.getConnection());
    }

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem(){
        return ms;
    }

    public void run(){
        while (true) {
            ms.execForAbonent(this);
            TimeHelper.sleep(100);
        }
    }

    @Override
    public int regUser(String login, String pass)
                throws SQLException {
        if (!isCorrectLogPas(login, pass)) return -2;
        if (dao.findUser(login) != null) return -1;
        dao.addUser(new UserDataSet(login, pass));
        return 1;
    }

    @Override
    public int logUser(String login, String pass)
            throws SQLException {
        if (!isCorrectLogPas(login, pass)) return -2;
        UserDataSet user = dao.findUser(login);
        if ((user == null)||(!user.getPassword().equals(pass))) return -1;
        return 1;
    }

    @Override
    public void deleteUser(String login)
            throws  AccountServiceException, SQLException, EmptyDataException {
        isCorrectLog(login);
        if (!dao.deleteUser(login)) throw new AccountServiceException("User was not removed");
    }

    private boolean isCorrectLogPas(String login, String pass) {
        if ((login == null)||(login.equals(""))||(pass.equals(""))||(pass == null))
            return false;
        return true;
    }

    private void isCorrectLog(String login) throws EmptyDataException{
        if ((login == null)||(login.equals("")))
            throw new EmptyDataException("empty string");
    }

    public Long getUserId(String name) throws SQLException {
        if (dao.findUser(name) == null) return null;
        return dao.findUser(name).getId();
    }

}
