import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;

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

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getDepartment());
            pstmt.executeUpdate();
        }
    }

    //Get all students
    public synchronized List<Student> getAllStudents() throws SQLException{
        List<Student> students = new ArrayList<>();
        String sql = "Select * FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setDepartment(rs.getString("department"));
                students.add(student);
            }
        }
        return students;
    }

    // Update student
    public synchronized void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET name = ?, email = ?, department = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getDepartment());
            pstmt.setInt(4, student.getId());
            pstmt.executeUpdate();
        }
    }

    // Delete student
    public synchronized void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Get student by ID
    public synchronized Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setDepartment(rs.getString("department"));
            }
        }
        return student;
    }
}
