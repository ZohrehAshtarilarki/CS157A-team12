package util;

import java.sql.Connection;

public interface DBConnectionInt {
    Connection getConnection();
    void closeConnection();
}
