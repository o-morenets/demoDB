package jdbc.isolation_levels.nonrepeatableread;

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

	private static final String QUERY = "select * from accounts where id = 1";

	public Reader(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		try (PreparedStatement stmt = conn.prepareStatement(QUERY)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("Account balance: " + rs.getDouble("balance"));
			}

			TimeUnit.SECONDS.sleep(3);
			System.out.println("AFTER WAKING UP");
			System.out.println("===============================================");

			rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Account balance: " + rs.getDouble("balance"));
            }

			rs.close();
			conn.close();
		} catch (SQLException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}