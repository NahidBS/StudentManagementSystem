import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem implements Runnable{
    private final Scanner scanner;
    private final StudentDAO studentDAO;
    private final String threadName;

    public StudentManagementSystem(String name) {
        this.threadName = name;
        this.scanner = new Scanner(System.in);
        this.studentDAO = new StudentDAO();
        try {
            studentDAO.createTable();
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }


    @Override
    public void run() {
        System.out.println("Thread " + threadName + " is running.");

        while (true) {
            System.out.println("\n" + threadName + " - Student Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. View Student by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        updateStudent();
                        break;
                    case 4:
                        deleteStudent();
                        break;
                    case 5:
                        viewStudentById();
                        break;
                    case 6:
                        System.out.println(threadName + " - Exiting system...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
        }
    }

    private void addStudent() throws SQLException {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter student email: ");
        String email = scanner.nextLine();

        System.out.print("Enter student department: ");
        String department = scanner.nextLine();

        Student student = new Student(0, name, email, department);
        studentDAO.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private void viewAllStudents() throws SQLException {
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("\nAll Students:");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private void updateStudent() throws SQLException {
        System.out.print("Enter student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found with ID: " + id);
            return;
        }

        System.out.println("Current details: " + student);
        System.out.print("Enter new name (leave blank to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) student.setName(name);

        System.out.print("Enter new email (leave blank to keep current): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) student.setEmail(email);

        System.out.print("Enter new department (leave blank to keep current): ");
        String department = scanner.nextLine();
        if (!department.isEmpty()) student.setDepartment(department);

        studentDAO.updateStudent(student);
        System.out.println("Student updated successfully!");
    }

    private void deleteStudent() throws SQLException {
        System.out.print("Enter student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found with ID: " + id);
            return;
        }

        studentDAO.deleteStudent(id);
        System.out.println("Student deleted successfully!");
    }

    private void viewStudentById() throws SQLException {
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found with ID: " + id);
        } else {
            System.out.println("Student details: " + student);
        }
    }

}
