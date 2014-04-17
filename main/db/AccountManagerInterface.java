package db;

import java.sql.SQLException;
import exception.*;

/*
  Created by p.Kochetkov on 8.03.14.
*/
public interface AccountManagerInterface {

    void regUser(String login, String pass) throws EmptyDataException, AccountServiceException, SQLException;
    void logUser(String login, String pass) throws EmptyDataException, AccountServiceException, SQLException;
    void deleteUser(String login) throws AccountServiceException, EmptyDataException, SQLException;

}
