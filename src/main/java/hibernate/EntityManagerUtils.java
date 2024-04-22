package hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;

public class EntityManagerUtils {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

	public static void doInPersistentContext(Consumer<EntityManager> entityManagerConsumer) {
		EntityManager em = emf.createEntityManager();
		try (em) {
			em.getTransaction().begin();
			entityManagerConsumer.accept(em);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public static void main(String[] args) {
		// only for creation/update of tables
	}
}
