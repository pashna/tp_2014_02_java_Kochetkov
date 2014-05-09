package db;
// Добавить exec в анонимный класс
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 Created by p.Kochetkov on 08.03.14.
 */

public class UserDAO {

    //ExecHandler handler;
    Connection connection;

    UserDAO (Connection connect) {
        connection = connect;
        //handler = new ExecHandler();
    }

    UserDataSet findUser(String username) throws SQLException {
        return Executor.execQuery(connection, SqlStringConstructor.generateSelect(username));
    }

    boolean addUser(UserDataSet user) throws SQLException {
        return Executor.execUpdate(connection, SqlStringConstructor.generateUpdate(user));
    }

    boolean deleteUser(String username) throws SQLException {
        return Executor.execUpdate(connection, SqlStringConstructor.generateDelete(username));
    }
}
