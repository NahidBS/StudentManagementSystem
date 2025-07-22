public class Main {
    public static void main(String[] args) {
        // Create multiple threads to simulate multiple users
        Thread user1 = new Thread(new StudentManagementSystem("User1"));
        Thread user2 = new Thread(new StudentManagementSystem("User2"));
        Thread user3 = new Thread(new StudentManagementSystem("User3"));

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
            System.out.println("All threads completed. Database connection closed.");
        }
    }
}