import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
// Create a single scanner for all threads (shared resource)
        Scanner mainScanner = new Scanner(System.in);

        // Create multiple threads sharing the same scanner
        Thread user1 = new Thread(new StudentManagementSystem("User1", mainScanner));
        Thread user2 = new Thread(new StudentManagementSystem("User2", mainScanner));
        Thread user3 = new Thread(new StudentManagementSystem("User3", mainScanner));
        // Start the threads
        user1.start();
        user2.start();
        user3.start();

        try {
            // Wait for all threads to complete
            user1.join();
            user2.join();
            user3.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        } finally {
            // Close the database connection when all threads are done
            DatabaseConnection.closeConnection();
            mainScanner.close();
            System.out.println("All threads completed. Database connection closed.");
        }
    }
}
