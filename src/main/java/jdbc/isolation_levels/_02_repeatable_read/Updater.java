package jdbc.isolation_levels._02_repeatable_read;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Kunaal A Trehan
 */
public class Updater implements Runnable {

	private final Connection conn;

	private static final String QUERY = "update accounts set balance = balance + 500 where id = 1";

	public Updater(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		try (PreparedStatement stmt = conn.prepareStatement(QUERY)) {
			stmt.executeUpdate();
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}