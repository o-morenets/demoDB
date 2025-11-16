package jdbc.isolation_levels.nonrepeatableread;

import jdbc.isolation_levels.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NonRepeatableReadExample {

	public static void main(String[] args) throws InterruptedException {

		Connection connUpdater = MySqlConnection.getConnection();
		Connection connReader = MySqlConnection.getConnection();
		try {
			connUpdater.setAutoCommit(false);
			connUpdater.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED); // can be any isolation level

			connReader.setAutoCommit(false);
			connReader.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//			connUpdater.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ); // prevents repeatable read
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

        Thread reader = new Thread(new Reader(connReader));
        Thread updater = new Thread(new Updater(connUpdater));
        reader.start();
        updater.start();
        reader.join();
        updater.join();

        // restore the previous value of an account type
        System.out.println("Restoring the previous value of an account type...");
        Connection connection = MySqlConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("update accounts set balance = 100 where id = 1")) {
            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
}
