package db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by popka on 09.05.14.
 */
public interface ExecHandlerInterface {
    public UserDataSet handle() throws SQLException;
}
