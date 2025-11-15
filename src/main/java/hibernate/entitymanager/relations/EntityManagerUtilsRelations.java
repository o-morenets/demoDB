package hibernate.entitymanager.relations;

import hibernate.entitymanager.EntityManagerUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;

public class EntityManagerUtilsRelations {

    private static final EntityManagerFactory emfRelations = Persistence.createEntityManagerFactory("relations_unit");

    public static void doInEntityManagerRelations(Consumer<EntityManager> entityManagerConsumer) {
        EntityManagerUtils.doInEntityManager(emfRelations, entityManagerConsumer);
    }
}
