package hibernate.entitymanager.relations.many_to_one;

import hibernate.entitymanager.EntityManagerUtils;

public class ManyToOneDemo {

	public static void main(String[] args) {
		EntityManagerUtils.doInEntityManagerPersistentContext(em -> {
			Address address = new Address();
			address.setStreet("Nova");
			address.setZip(99999);

			Address address2 = new Address();
			address2.setStreet("Street 1-st");
			address2.setZip(99999);

			Office office1 = new Office();
			office1.setName("EPAM");
			office1.setAddress(address);

			Office office2 = new Office();
			office2.setName("Global Logic");
			office2.setAddress(address);

			Office office3 = new Office();
			office3.setName("IT Company");
			office3.setAddress(address2);

			em.persist(office1);
			em.persist(office2);
			em.persist(office3);
		});
	}
}
