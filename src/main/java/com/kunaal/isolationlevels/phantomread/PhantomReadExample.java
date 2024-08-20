package com.kunaal.isolationlevels.phantomread;

import jdbc.MySqlConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class PhantomReadExample {

	public static void main(String[] args) {

		Connection connInsert = MySqlConnection.getConnection();
		Connection connReader = MySqlConnection.getConnection();
		try {
			connInsert.setAutoCommit(false);
			connInsert.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			connReader.setAutoCommit(false);
//			connReader.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//			connReader.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); // prevents phantom read
			connReader.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ); // prevents, but should not
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		new Thread(new Reader(connReader)).start();
		new Thread(new Inserter(connInsert)).start();
	}
}
