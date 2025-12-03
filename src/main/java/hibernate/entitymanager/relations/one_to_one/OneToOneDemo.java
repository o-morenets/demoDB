package hibernate.entitymanager.relations.one_to_one;


import hibernate.entitymanager.relations.EntityManagerUtilsRelations;

import java.util.List;

public class OneToOneDemo {

	public static void main(String[] args) {
		insertUserWithAddress();
		insertUserWithProfile();
	}

	private static void insertUserWithAddress() {
		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			User user = new User();
			user.setFirstName("Garry");
			user.setLastName("Moore");
			em.persist(user);

			Address address = new Address();
			address.setCity("Lwów");
			address.setStreet("Kłepariwśka");
			address.setNumber("18");
			em.persist(address); // we need to persist because we have no CascadeType

			user.setAddress(address); // user is already in persistent state, so it saves address
		});
	}

	private static void insertUserWithProfile() {
		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			User user = new User();
			user.setFirstName("John");
			user.setLastName("Lennon");
			em.persist(user);

			Profile profile = new Profile();
			profile.setActive(true);
			profile.setPhotoUrl("https://photo/url");
//			em.persist(profile); // don't need it when @OneToOne(..., cascade = CascadeType.ALL) on User's side

			user.setProfile(profile); // user is already in persistent state, so it saves profile

			// Attempt to save Profile without User
			Profile profileWithoutUser = new Profile();
			profileWithoutUser.setActive(false);
			profileWithoutUser.setPhotoUrl("https://stock/photo/profile");
//			em.persist(profileWithoutUser); // can not be persisted, as Profile does not know which is the id (we get id from Person, but here it is not set)

			// Profile with Embedded Address
			User user1 = new User();
			user1.setFirstName("Andrii");
			user1.setLastName("Johansson");
			em.persist(user1);

			AddressEmbeddable address = new AddressEmbeddable();
			address.setCity("Paris");
			address.setStreet("2nd ave.");
			address.setZip("76543");
			address.setHouse("43/12");
			address.setApartment("12-D");

			Profile profileWithEmbeddedAddress = new Profile();
			profileWithEmbeddedAddress.setActive(true);
			profileWithEmbeddedAddress.setPhotoUrl("https://photo/url433");
			profileWithEmbeddedAddress.setAddress(address);

			user1.setProfile(profileWithEmbeddedAddress);
		});
	}
}
