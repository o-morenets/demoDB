package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Employees {

    private static final String url = "jdbc:mysql://localhost:3306/employees?serverTimezone=UTC&useSSL=false";
    private static final String user = "root";
    private static final String password = "root";

    private Employees() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(100);

        Connection connection = getConnection();

        IntStream.range(0, 100)
                .forEach(i -> es.execute(query(
//                        getConnection() // one connection per thread
                        connection // one connection shared with all threads
                )));

        System.out.println("shutting down...");
        es.shutdown();

        while (!es.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("still querying...");
        }

        connection.close();
    }

    private static Runnable query(Connection conn) {
        return () -> {
            String QUERY = """
                    select * from employees e
                    left join salaries s on s.emp_no = e.emp_no
                    left join titles t on t.emp_no = e.emp_no
                    where e.hire_date between '1999-08-15' and '1999-08-17'
                    """;

            long start;

            try (PreparedStatement stmt = conn.prepareStatement(QUERY)) {
                System.out.println(Thread.currentThread().getName() + ": " + "Start selecting employees...");
                start = System.nanoTime();
                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + ": " + (System.nanoTime() - start) / 1_000_000_000.0);
        };
    }
}
