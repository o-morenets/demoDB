package com.kunaal.isolationlevels.dirtyread;

import com.kunaal.isolationlevels.MySqlConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Kunaal A Trehan
 */
public class DirtyReadExample {

	public static void main(String[] args) {
		MySqlConnection conn = new MySqlConnection();

		Connection connPayment = conn.getConnection();
		Connection connReader = conn.getConnection();
		try {
			connPayment.setAutoCommit(false);
			connPayment.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

			connReader.setAutoCommit(false);
			connReader.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//			connReader.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // prevents dirty read
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}


		Thread paymentThread = new Thread(new Payment(connPayment));
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