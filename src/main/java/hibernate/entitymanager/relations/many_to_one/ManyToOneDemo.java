package hibernate.entitymanager.relations.many_to_one;


import hibernate.entitymanager.relations.EntityManagerUtilsRelations;

public class ManyToOneDemo {

	public static void main(String[] args) {
		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			Address address = new Address("Nova", 12345);

			Address address2 = new Address("Street 1-st", 99999);

			Office office1 = new Office("EPAM", address);
			Office office2 = new Office("Global Logic", address);
			Office office3 = new Office("IT Company", address2);

			em.persist(office1);
			em.persist(office2);
			em.persist(office3);
		});
	}
}
