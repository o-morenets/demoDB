package com.kunaal.isolationlevels;

import com.kunaal.isolationlevels.dirtyread.Reader;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MySqlConnection {

	private static final String url = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
	private static final String user = "root";
	private static final String password = "root";

	public MySqlConnection() {
	}

	public java.sql.Connection getConnection() {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws SQLException, InterruptedException {
		MySqlConnection mySqlConnection = new MySqlConnection();

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		IntStream.rangeClosed(1, 10)
				.mapToObj(value -> new Reader(mySqlConnection.getConnection()))
				.forEach(executorService::execute);
		executorService.shutdown();
		executorService.awaitTermination(2, TimeUnit.SECONDS);
		mySqlConnection.getConnection().close();
	}
}
