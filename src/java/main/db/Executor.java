package db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

/*
Created by p.Kochetkov on 08.03.14.
 */
public class Executor {
    public static boolean execUpdate(Connection connection, String update) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(update);
        int updated =  stmt.getUpdateCount();
        stmt.close();
        return updated > 0;
    }

    public static UserDataSet execQuery (Connection connection, String query
                                  /*ExecHandler handler*/) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        final ResultSet result = stmt.getResultSet();
        //UserDataSet user = handler.handle(result);
        UserDataSet user = new ExecHandlerInterface() {
            @Override
            public UserDataSet handle() throws SQLException {
                if (result.next()) {
                    return new UserDataSet(result.getLong("id"),result.getString("login"), result.getString("pass"));
                } else {
                    return null;
                }
            }
        }.handle();
        result.close();
        stmt.close();
        return user;
    }
}
