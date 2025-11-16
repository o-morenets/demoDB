package jdbc.isolation_levels._02_repeatable_read;

import jdbc.isolation_levels.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class RealRepeatableReadDemo {

    public static void main(String[] args) throws InterruptedException {

        AtomicBoolean stopFlag = new AtomicBoolean(false);

        Thread txReader = new Thread(() -> {
            try (Connection connection = MySqlConnection.getConnection()) {
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                int count = 0;

                while (!stopFlag.get()) {
                    System.out.println("\tReading balance #1 step " + ++count);
                    double balance1;
                    try (PreparedStatement stmt = connection.prepareStatement("SELECT balance FROM accounts WHERE id = 1")) {
                        ResultSet rs = stmt.executeQuery();
                        rs.next();
                        balance1 = rs.getDouble("balance");
                    }

                    System.out.println("\tReading balance #2 step " + ++count);
                    double balance2;
                    try (PreparedStatement stmt = connection.prepareStatement("SELECT balance FROM accounts WHERE id = 1")) {
                        ResultSet rs = stmt.executeQuery();
                        rs.next();
                        balance2 = rs.getDouble("balance");
                    }

                    if (balance1 != balance2) {
                        System.out.println("Non-repeatable read detected: " + balance1 + " -> " + balance2);
                        stopFlag.set(true);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        txReader.start();

        Thread txUpdater = new Thread(() -> {
            try (
                    Connection connection = MySqlConnection.getConnection();
                    PreparedStatement stmt = connection.prepareStatement("UPDATE accounts SET balance = balance + 5 WHERE id = 1")
            ) {
                int count = 0;

                while (!stopFlag.get()) {
                    System.out.println("Updating balance step " + ++count);
                    stmt.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        txUpdater.start();

        txReader.join();
        txUpdater.join();

        // restore the previous value of an account type
        System.out.println("Restoring the previous value of an account type...");
        Connection connection = MySqlConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE accounts SET balance = 100 WHERE id = 1")) {
            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
