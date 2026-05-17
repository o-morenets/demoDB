package hibernate.entitymanager.relations.one_to_one;


import hibernate.entitymanager.relations.EntityManagerUtilsRelations;

public class OneToOneDemo {

	public static void main(String[] args) {

		System.out.println("Saving User with Address");
		insertUserWithAddress();

		System.out.println("Saving User with profile");
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

			// without CascadeType another UPDATE command will be invoked for next line:
			user.setAddress(address); // user is already in persistent state, so it saves address
		});
	}

	private static void insertUserWithProfile() {
		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {

			// User <-> Profile

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
			profileWithoutUser.setPhotoUrl("https://stock/photo/profile/invalid");
//			em.persist(profileWithoutUser); // can not be persisted, as Profile does not know which is the id
//			(we get id from Person, but here it is not set)


			// User <-> Profile with Embedded address

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


			// User <-> Profile with Embedded office address (overridden fields)

			User user2 = em.find(User.class, 1L);

			AddressEmbeddable address2 = new AddressEmbeddable();
			address2.setCity("New York");
			address2.setStreet("Noname street");
			address2.setZip("87676");
			address2.setHouse("12");
			address2.setApartment("145");

			Profile profileWithEmbeddedAddress2 = new Profile();
			profileWithEmbeddedAddress2.setActive(true);
			profileWithEmbeddedAddress2.setPhotoUrl("https://photo/office");
			profileWithEmbeddedAddress2.setOfficeAddress(address2);

			user2.setProfile(profileWithEmbeddedAddress2);
		});
	}
}
