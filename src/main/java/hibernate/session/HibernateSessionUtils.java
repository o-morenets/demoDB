package hibernate.session;

import hibernate.session.entity.Actor;
import hibernate.session.entity.Employee;
import hibernate.session.entity.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;

public class HibernateSessionUtils {

    private static final SessionFactory sakilaSessionFactory = new Configuration()
            .configure() // used `hibernate.cfg.xml` for configuration
            .addAnnotatedClass(Actor.class)
            // another class `Film` added in configuration file `hibernate.cfg.xml`
            .buildSessionFactory();

    public static void doInHibernateSessionSakila(Consumer<Session> sessionConsumer) {
        Session session = sakilaSessionFactory.openSession();
        try {
            session.beginTransaction();
            sessionConsumer.accept(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private static final SessionFactory testDbSessionFactory = new Configuration()
            // here we don't use configure(), so `hibernate.cfg.xml` is ignored
            // only `hibernate.properties` file and programmatic configuration are used for configuration
            .addAnnotatedClass(Employee.class)
            .addAnnotatedClass(Department.class)
            .buildSessionFactory();

    public static void doInHibernateSessionEmployees(Consumer<Session> sessionConsumer) {
        Session session = testDbSessionFactory.openSession();
        try {
            session.beginTransaction();
            sessionConsumer.accept(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
