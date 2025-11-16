package jdbc.isolation_levels._03_phantom_read;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Reader thread which selects the data while another thread is updating the data
 *
 * @author Kunaal A Trehan
 */
public class Reader implements Runnable {

	private final Connection conn;

	private static final String QUERY = "select count(*) from accounts";

	public Reader(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		try (PreparedStatement stmt = conn.prepareStatement(QUERY)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Accounts count: " +
                        rs.getInt(1)
//                        rs.getString(2) + " - " +
//                        rs.getDouble(3)
                );
            }

            TimeUnit.SECONDS.sleep(3);

			System.out.println("Awoken");
			System.out.println("===============================================");

			rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Accounts count: " +
                        rs.getInt(1)
//                        rs.getString(2) + " - " +
//                        rs.getDouble(3)
                );
            }

			rs.close();
			conn.close();
		} catch (SQLException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}