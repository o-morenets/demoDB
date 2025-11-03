package db_high_load;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Run `mvn flyway:migrate` before starting this Simulator
 */
public class DbHighLoadSimulator {

    private static final int THREAD_COUNT = 300;
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public static void main(String[] args) {
        try (ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT)) {
            for (int i = 0; i < THREAD_COUNT; i++) {
                es.submit(() -> {
                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        String query = "SELECT COUNT(*) FROM high_load.documents WHERE content LIKE ?";
                        try (PreparedStatement ps = conn.prepareStatement(query)) {
                            ps.setString(1, "%abc123xyz%");
                            ResultSet rs = ps.executeQuery();
                            if (rs.next()) {
                                int count = rs.getInt(1);
                                System.out.println("Found: " + count + " rows");
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }
}
