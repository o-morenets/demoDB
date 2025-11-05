package hibernate.session;

import hibernate.session.entity.Actor;
import hibernate.session.entity.Employee;
import hibernate.session.entity.Film;
import hibernate.session.entity.Department;
import org.hibernate.ObjectNotFoundException;

public class RunSessionWithConfig {

    public static void main(String[] args) {
        doSmthSakila();
        doSmthEmployees();
    }

    private static void doSmthSakila() {
        HibernateSessionUtils.doInHibernateSessionSakila(session -> {
            Film film = session.get(Film.class, 17);
            System.out.println("film = " + film);

            Film nonExistingFilm = session.get(Film.class, 12345); // no Exception, just returns null
            System.out.println("nonExistingFilm = " + nonExistingFilm);

            Actor actor = new Actor();
            session.load(actor, ((short) 17));
            System.out.println("actor = " + actor);

            try {
                Actor nonExistingActor = new Actor();
                session.load(nonExistingActor, ((short) 12345)); // throws org.hibernate.ObjectNotFoundException
                System.out.println("nonExistingActor = " + nonExistingActor);
            } catch (ObjectNotFoundException e) {
                System.out.println("ObjectNotFoundException: " + e.getMessage());
            }
        });
    }

    private static void doSmthEmployees() {
        HibernateSessionUtils.doInHibernateSessionEmployees(session -> {
            Employee employee = session.get(Employee.class, 10001);
            System.out.println("employee = " + employee);

            Employee nonExistentEmployee = session.get(Employee.class, 999999); // no Exception, just returns null
            System.out.println("nonExistentEmployee = " + nonExistentEmployee);

            Department department = new Department();
            session.load(department, "d001");
            System.out.println("department = " + department);

            try {
                Department nonExistingDepartment = new Department();
                session.load(nonExistingDepartment, "d999999"); // throws org.hibernate.ObjectNotFoundException
                System.out.println("nonExistingDepartment = " + nonExistingDepartment);
            } catch (ObjectNotFoundException e) {
                System.out.println("ObjectNotFoundException: " + e.getMessage());
            }
        });
    }
}
