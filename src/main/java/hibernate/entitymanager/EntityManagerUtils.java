package hibernate.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.function.Consumer;

public class EntityManagerUtils {

    public static void doInEntityManager(EntityManagerFactory factory, Consumer<EntityManager> entityManagerConsumer) {
        EntityManager em = factory.createEntityManager();
        try (em) {
            em.getTransaction().begin();
            entityManagerConsumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
	}
}
