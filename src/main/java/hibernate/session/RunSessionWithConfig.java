package hibernate.session;

import hibernate.session.entity.Actor;
import hibernate.session.entity.Film;

public class RunSessionWithConfig {

    public static void main(String[] args) {
        HibernateSessionUtils.doInHibernateSessionSakila(session -> {
            Film film = session.get(Film.class, 17);
            System.out.println("film = " + film);
            Film nonExistingFilm = session.get(Film.class, 12345); // no Exception, just returns null
            System.out.println("nonExistingFilm = " + nonExistingFilm);

            Actor actor = new Actor();
            session.load(actor, ((short) 17));
            System.out.println("actor = " + actor);

            Actor nonExistingActor = new Actor();
            session.load(nonExistingActor, ((short) 12345)); // throws org.hibernate.ObjectNotFoundException
            System.out.println("nonExistingActor = " + nonExistingActor);
        });
    }
}
