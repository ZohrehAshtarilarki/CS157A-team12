package util;

import java.sql.Connection;

public interface DbConnectionInt {
    Connection getConnection();
    void closeConnection();
}
