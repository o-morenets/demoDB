package jdbc.isolation_levels._03_phantom_read;

import jdbc.isolation_levels.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PhantomReadExample {

	public static void main(String[] args) throws InterruptedException {

		Connection connInsert = MySqlConnection.getConnection();
		Connection connReader = MySqlConnection.getConnection();
		try {
			connInsert.setAutoCommit(false);
			connInsert.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED); // can be any isolation level

			connReader.setAutoCommit(false);
			connReader.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//			connReader.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); // prevents phantom read
//			connReader.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ); // prevents, but should not
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

        Thread reader = new Thread(new Reader(connReader));
        Thread inserter = new Thread(new Inserter(connInsert));
        reader.start();
        inserter.start();
        reader.join();
        inserter.join();

        // restore the previous number of accounts
        System.out.println("Restoring the previous number of accounts...");
        Connection connection = MySqlConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("delete from accounts where id > 1")) {
            stmt.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
}
