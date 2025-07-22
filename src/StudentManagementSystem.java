import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem implements Runnable{
    private final Scanner sharedScanner;
    //private final Scanner scanner;
    private final StudentDAO studentDAO;
    private final String threadName;

    public StudentManagementSystem(String name, Scanner scanner) {
        this.threadName = name;
        this.sharedScanner = scanner;
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
            // Synchronize on the shared scanner for thread-safe input
            synchronized (sharedScanner) {
            System.out.println("\n" + threadName + " - Student Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. View Student by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = sharedScanner.nextInt();
                sharedScanner.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewAllStudents();
                    case 3 -> updateStudent();
                    case 4 -> deleteStudent();
                    case 5 -> viewStudentById();
                    case 6 -> {
                        System.out.println(threadName + " - Exiting system...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Input error: " + e.getMessage());
                sharedScanner.nextLine(); // Clear invalid input
            }

            }

            // Small delay to prevent thread starvation
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // All methods below now use sharedScanner instead of scanner
    private void addStudent() throws SQLException {
        synchronized (sharedScanner) {
            System.out.print("Enter student name: ");
            String name = sharedScanner.nextLine();

            System.out.print("Enter student email: ");
            String email = sharedScanner.nextLine();

            System.out.print("Enter student department: ");
            String department = sharedScanner.nextLine();

            Student student = new Student(0, name, email, department);
            studentDAO.addStudent(student);
            System.out.println("Student added successfully!");
        }
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
        int id = sharedScanner.nextInt();
        sharedScanner.nextLine(); // consume newline

        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found with ID: " + id);
            return;
        }

        System.out.println("Current details: " + student);
        System.out.print("Enter new name (leave blank to keep current): ");
        String name = sharedScanner.nextLine();
        if (!name.isEmpty()) student.setName(name);

        System.out.print("Enter new email (leave blank to keep current): ");
        String email = sharedScanner.nextLine();
        if (!email.isEmpty()) student.setEmail(email);

        System.out.print("Enter new department (leave blank to keep current): ");
        String department = sharedScanner.nextLine();
        if (!department.isEmpty()) student.setDepartment(department);

        studentDAO.updateStudent(student);
        System.out.println("Student updated successfully!");
    }

    private void deleteStudent() throws SQLException {
        System.out.print("Enter student ID to delete: ");
        int id = sharedScanner.nextInt();
        sharedScanner.nextLine(); // consume newline

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
        int id = sharedScanner.nextInt();
        sharedScanner.nextLine(); // consume newline

        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found with ID: " + id);
        } else {
            System.out.println("Student details: " + student);
        }
    }

}
