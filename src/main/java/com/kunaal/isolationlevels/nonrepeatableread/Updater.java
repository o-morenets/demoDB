package com.kunaal.isolationlevels.nonrepeatableread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Kunaal A Trehan
 */
public class Updater implements Runnable {

	private final Connection conn;

	private static final String QUERY = "update `my-examples`.employee set empCtry = 'USA' where id = 1";

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