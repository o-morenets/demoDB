package com.kunaal.isolationlevels.nonrepeatableread;

import com.kunaal.isolationlevels.MySqlConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class NonRepeatableExample {

	public static void main(String[] args) {

		Connection connUpdater = MySqlConnection.getConnection();
		Connection connReader = MySqlConnection.getConnection();
		try {
			connUpdater.setAutoCommit(false);
			connUpdater.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

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
