package jdbc.isolation_levels.phantomread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Kunaal A Trehan
 */
public class Inserter implements Runnable {

	private final Connection conn;

	private static final String QUERY = "insert into accounts VALUES (default,?,?)";

	public Inserter(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		System.out.println("Inserting values...");
		try (PreparedStatement stmt = conn.prepareStatement(QUERY)) {
            stmt.setString(1, "CHECKING");
            stmt.setDouble(2, 5000.00d);

			stmt.execute();
			conn.commit();

			conn.close();
			System.out.println("Row added successfully.");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}