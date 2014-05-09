package db;

import java.sql.SQLException;
import exception.*;

/*
  Created by p.Kochetkov on 8.03.14.
*/
public interface AccountServiceInterface {

    int regUser(String login, String pass) throws EmptyDataException, AccountServiceException, SQLException;
    int logUser(String login, String pass) throws EmptyDataException, AccountServiceException, SQLException;
    void deleteUser(String login) throws AccountServiceException, EmptyDataException, SQLException;

}
