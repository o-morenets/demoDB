package hibernate.runwithxmlconfig;

import hibernate.entity.Actor;
import hibernate.entity.Film;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class RunSessionWithConfig {

	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Actor.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Film film = session.get(Film.class, 17);
		System.out.println(film);
		Film nonExistingFilm = session.get(Film.class, 170000);
		System.out.println(nonExistingFilm);

		Actor actor = new Actor();
		session.load(actor, 17);
		System.out.println(actor);

		Actor nonExistingActor = new Actor();
//		session.load(nonExistingActor, 170000); // throws org.hibernate.ObjectNotFoundException
		System.out.println(nonExistingActor);

		session.getTransaction().commit();
		session.close();

	}
}
