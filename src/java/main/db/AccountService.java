package db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import exception.*;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.MessageSystem;
import utils.*;
/*
Created by p.Kochetkov on 08.03.14.
 */

public class AccountService implements AccountManagerInterface, Abonent, Runnable {
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

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem(){
        return ms;
    }

    public void run(){
        while (true){
            ms.execForAbonent(this);
            TimeHelper.sleep(5000);
        }
    }

    @Override
    public void regUser(String login, String pass)
                throws  AccountServiceException, SQLException, EmptyDataException {
        isCorrectLogPas(login, pass);
        if (dao.findUser(login) != null) throw new AccountServiceException("User already exist");
        dao.addUser(new UserDataSet(login, pass));
    }

    @Override
    public void logUser(String login, String pass)
            throws  AccountServiceException, SQLException, EmptyDataException {
        isCorrectLogPas(login, pass);
        UserDataSet user = dao.findUser(login);
        if ((user == null)||(!user.getPassword().equals(pass))) throw new AccountServiceException("Wrong password");
    }

    @Override
    public void deleteUser(String login)
            throws  AccountServiceException, SQLException, EmptyDataException {
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

    public Long getUserId(String name) throws SQLException, NoUserIdException {
        if (dao.findUser(name) == null) throw new NoUserIdException("No user "+ name);
        return dao.findUser(name).getId();
    }

}
