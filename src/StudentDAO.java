import java.sql.*;

public class StudentDAO {
    //create table if not existed
    public void createTable() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS students(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) UNIQUE NOT NULL, " +
                "department VARCHAR(50))";

        try (Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        }
    }

    /*Insert Student*/
    public synchronized void addStudent(Student student) throws SQLException{
        String sql = "INSERT INTO students (name, email, department) VALUES(?, ?, ?)";


    }



}
