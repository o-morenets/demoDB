package jdbc.isolation_levels.dirtyread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Reader thread which selects the data while other thread is updating the data
 *
 * @author Kunaal A Trehan
 */
public class Reader implements Runnable {

	private final Connection conn;

	private static final String QUERY = "select acctBalance from `my-examples`.accountbalance where id = 1";

	public Reader(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " - connection: " + conn + " - requesting balance...");
		try (PreparedStatement stmt = conn.prepareStatement(QUERY); ResultSet rs = stmt.executeQuery()) {
			System.out.println(Thread.currentThread().getName() + " - parsing query result...");
			TimeUnit.SECONDS.sleep(1);
			while (rs.next()) {
				System.out.println(Thread.currentThread().getName() + " - Balance is: " + rs.getDouble(1));
			}
			conn.close();
		} catch (SQLException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}