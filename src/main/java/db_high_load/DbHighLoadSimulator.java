package db_high_load;

import org.flywaydb.core.Flyway;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DbHighLoadSimulator {

    private static final int THREAD_COUNT = 300;

    private static final String DB_URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public static void main(String[] args) throws InterruptedException {

        Flyway flyway = Flyway.configure()
                .dataSource(DB_URL, DB_USER, DB_PASSWORD)
                .locations("classpath:db/migration")
                .schemas("high_load")
                .load();
        flyway.migrate();
        System.out.println("Migration complete");

        ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);
        System.out.println("Starting " + THREAD_COUNT + " threads...");

        for (int i = 0; i < THREAD_COUNT; i++) {
            es.submit(() -> {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT COUNT(*) FROM high_load.documents WHERE content LIKE ?";
                    try (PreparedStatement ps = conn.prepareStatement(query)) {
                        ps.setString(1, "%abc123xyz%");
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            int count = rs.getInt(1);
                            System.out.println("Thread " + Thread.currentThread().getName() + " - found: " + count + " rows");
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        // tell the executor that no new tasks will be submitted
        es.shutdown();

        // wait for all tasks to complete (up to 1 minute)
        while (!es.awaitTermination(1, TimeUnit.MINUTES)) {
            System.out.println("Still waiting for tasks to complete...");
        }

        System.out.println("All tasks completed.");
    }
}
