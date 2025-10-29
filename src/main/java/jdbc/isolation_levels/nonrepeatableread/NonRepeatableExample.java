package jdbc.isolation_levels.nonrepeatableread;

import jdbc.isolation_levels.MySqlConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class NonRepeatableExample {

	public static void main(String[] args) {

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

		new Thread(new Reader(connReader)).start();
		new Thread(new Updater(connUpdater)).start();
	}
}
