package jdbc.isolation_levels.phantomread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reader thread which selects the data while other thread is updating the data
 *
 * @author Kunaal A Trehan
 */
public class Reader implements Runnable {

	private final Connection conn;

	private static final String QUERY = "select * from `my-examples`.employee";

	public Reader(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		try (PreparedStatement stmt = conn.prepareStatement(QUERY)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("Employee details: " + rs.getInt(1) + " - " + rs.getString(2));
			}

			Thread.sleep(3000);
			System.out.println("AFTER WAKING UP");
			System.out.println("===============================================");

			rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("Employee details: " + rs.getInt(1) + " - " + rs.getString(2) );
			}

			rs.close();
			conn.close();
		} catch (SQLException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}