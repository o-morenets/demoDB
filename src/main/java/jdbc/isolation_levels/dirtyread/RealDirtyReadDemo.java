package jdbc.isolation_levels.dirtyread;

import jdbc.isolation_levels.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RealDirtyReadDemo {

    public static void main(String[] args) {

        new Thread(() -> {
            int count = 0;
            try (
                    Connection connection = MySqlConnection.getConnection();
                    PreparedStatement stmt = connection.prepareStatement("UPDATE accounts SET balance = balance + 500 WHERE id = 1")
            ) {
                connection.setAutoCommit(false);
                while (true) {
                    System.out.println("Updating balance step " + ++count);
                    stmt.executeUpdate();
                    connection.rollback();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try (
                    Connection connection = MySqlConnection.getConnection();
                    PreparedStatement stmt = connection.prepareStatement("SELECT balance FROM accounts WHERE id = 1")
            ) {
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                int count = 0;

                while (true) {
                    System.out.println("\tReading balance step " + ++count);
                    ResultSet resultSet = stmt.executeQuery();
                    if (resultSet.next()) {
                        double balance = resultSet.getDouble("balance");
                        if (balance != 100.00) {
                            System.out.println("DIRTY READ DETECTED. Expected: 100.00, got: " + balance);
                            System.exit(0);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
