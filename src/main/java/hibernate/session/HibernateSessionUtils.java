package hibernate.session;

import hibernate.entitymanager.relations.one_to_many_bidirectional.Customer;
import hibernate.entitymanager.relations.one_to_many_bidirectional.Order;
import hibernate.session.entity.Actor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;

public class HibernateSessionUtils {

    private static final SessionFactory sakilaSessionFactory = new Configuration()
            .configure()
            .addAnnotatedClass(Actor.class) // class Film added in configuration file (hibernate.cfg.xml)
            .buildSessionFactory();

    private static final SessionFactory testDbSessionFactory = new Configuration()
            .addAnnotatedClass(Customer.class)
            .addAnnotatedClass(Order.class)
            .buildSessionFactory();

    public static void doInHibernateSessionSakila(Consumer<Session> sessionConsumer) {
        Session session = sakilaSessionFactory.openSession();
        try {
            session.beginTransaction();
            sessionConsumer.accept(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
    }

    public static void doInHibernateSessionTestDB(Consumer<Session> sessionConsumer) {
        Session session = testDbSessionFactory.openSession();
        try {
            session.beginTransaction();
            sessionConsumer.accept(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
    }
}
