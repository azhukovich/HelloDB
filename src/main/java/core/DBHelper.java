package core;

import java.sql.*;

public class DBHelper {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:h2:file:~/dbdata/test", "sa", "");
    }

    public ResultSet execQuery(String query) throws SQLException {
        Statement stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return stmt.executeQuery(query);
    }
}
