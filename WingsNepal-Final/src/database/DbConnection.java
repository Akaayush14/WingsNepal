package database;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Aayush Kharel
 */
public interface DbConnection {
    Connection openConnection();
    void closeConnection(Connection conn);
    ResultSet runQuery(Connection conn, String query);
    int executeUpdate(Connection conn, String query);
}
