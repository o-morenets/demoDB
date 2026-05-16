package db_high_load;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
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

        // 1. Конфігурація HikariCP
        HikariDataSource dataSource = getHikariDataSource();

        // 2. Flyway міграція (тепер використовує наш пул)
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .schemas("high_load")
                .load();
        flyway.migrate();
        System.out.println("Migration complete");

        // 3. Створення пулу потоків
        ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);
        System.out.println("Starting " + THREAD_COUNT + " threads using HikariCP...");

        for (int i = 0; i < THREAD_COUNT; i++) {
            es.submit(() -> {
                // dataSource.getConnection() тепер НЕ створює нове TCP-з'єднання,
                // а просто позичає його у HikariCP на час роботи блоку try
                try (Connection conn = dataSource.getConnection()) {
                    String query = "SELECT COUNT(*) FROM high_load.documents WHERE content LIKE ?";
                    try (PreparedStatement ps = conn.prepareStatement(query)) {
                        ps.setString(1, "%abc123%");
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                int count = rs.getInt(1);
                                System.out.println("Thread " + Thread.currentThread().getName() + " - found: " + count + " rows");
                            }
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Thread " + Thread.currentThread().getName() + " failed: " + e.getMessage());
                }
            });
        }

        es.shutdown();

        // Чекаємо завершення (збільшимо час, бо 300 важких запитів через 30 з'єднань займуть трохи часу)
        while (!es.awaitTermination(2, TimeUnit.MINUTES)) {
            System.out.println("Still waiting for tasks to complete...");
        }

        // Закриваємо пул з'єднань після завершення програми
        dataSource.close();
        System.out.println("All tasks completed. Data source closed.");
    }

    private static HikariDataSource getHikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);

        // Максимальна кількість готових з'єднань, які пул буде тримати в пам'яті
        config.setMaximumPoolSize(100);

        // Скільки потік готовий чекати на вільне з'єднання з пулу
        config.setConnectionTimeout(1_200_000);

        // Створюємо DataSource на основі пулу
        return new HikariDataSource(config);
    }
}