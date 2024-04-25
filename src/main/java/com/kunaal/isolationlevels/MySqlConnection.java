package com.kunaal.isolationlevels;

import com.kunaal.isolationlevels.dirtyread.Reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class MySqlConnection {

    private static final String url = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "root";

    private MySqlConnection() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Stream.generate(() -> new Reader(MySqlConnection.getConnection()))
				.limit(10)
                .forEach(executorService::execute);
        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.SECONDS);
        MySqlConnection.getConnection().close();
    }
}
