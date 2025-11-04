package jdbc.isolation_levels.dirtyread;

import jdbc.utils.MySqlConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Kunaal A Trehan
 */
public class DirtyReadExample {

	public static void main(String[] args) {

		Connection connPayment = MySqlConnection.getConnection();
		Connection connReader = MySqlConnection.getConnection();
		try {
			connPayment.setAutoCommit(false);
			connPayment.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED); // can be any isolation level

			connReader.setAutoCommit(false);
			connReader.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//			connReader.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // prevents dirty read
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}


		Thread paymentThread = new Thread(new Updater(connPayment));
		Thread readerThread = new Thread(new Reader(connReader));

		paymentThread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		readerThread.start();
	}

}