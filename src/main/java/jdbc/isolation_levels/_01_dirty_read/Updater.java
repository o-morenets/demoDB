package jdbc.isolation_levels._01_dirty_read;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

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
            System.out.println(Thread.currentThread().getName() + " - updating balance...");
            stmt.execute();
            System.out.println(Thread.currentThread().getName() + " - updated balance, sleep for 3 sec...");
            TimeUnit.SECONDS.sleep(3);
            System.out.println(Thread.currentThread().getName() + " - awoken");
            conn.rollback();
            System.out.println(Thread.currentThread().getName() + " - ROLLED BACK.");
            conn.close();
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}