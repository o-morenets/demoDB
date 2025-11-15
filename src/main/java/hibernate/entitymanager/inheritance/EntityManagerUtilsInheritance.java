package hibernate.entitymanager.inheritance;

import hibernate.entitymanager.EntityManagerUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;

public class EntityManagerUtilsInheritance {

    private static final EntityManagerFactory emfInheritance = Persistence.createEntityManagerFactory("inheritance_unit");

    public static void doInEntityManagerInheritance(Consumer<EntityManager> entityManagerConsumer) {
        EntityManagerUtils.doInEntityManager(emfInheritance, entityManagerConsumer);
    }
}
