package jdbc.isolation_levels.dirtyread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Kunaal A Trehan
 */
public class Payment implements Runnable {

    private final Connection conn;

    private static final String QUERY = "update `my-examples`.accountbalance set acctBalance = acctBalance - 10 where id = 1";

    public Payment(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void run() {
        try (PreparedStatement stmt = conn.prepareStatement(QUERY)) {
            System.out.println(Thread.currentThread().getName() + " - updating balance...");
            stmt.execute();
            System.out.println(Thread.currentThread().getName() + " - updated balance, sleep for 3 sec...");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " - awoken");
            conn.rollback();
            System.out.println(Thread.currentThread().getName() + " - ROLLED BACK.");
            conn.close();
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}