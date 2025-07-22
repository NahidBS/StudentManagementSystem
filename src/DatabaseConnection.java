import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    private DatabaseConnection() {}
    public static synchronized Connection getConnection () throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Database driver not found", e);
            }
        }
        return connection;
    }

    public static void closeConnection () {
        try {
            if (connection != null && !connection.isClosed()) {
                    connection.close();
            }
        }catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}

