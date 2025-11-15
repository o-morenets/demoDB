package hibernate.entitymanager.inheritance;

import java.awt.*;
import java.util.List;

public class InheritanceDemo {

	public static void main(String[] args) {

		EntityManagerUtilsInheritance.doInEntityManagerInheritance(em -> {

			Animal animalPet = new Pet();
			animalPet.setColor(Color.GRAY);
			em.persist(animalPet);

			Pet pet = new Pet();
			pet.setColor(Color.WHITE);
			pet.setName("Pet Kitty");
			em.persist(pet);

			Animal animalDog = new Dog();
			animalDog.setColor(Color.BLACK);
			em.persist(animalDog);

			Pet petDog = new Dog();
			petDog.setName("Pet Doggy");
			petDog.setAge((byte) 6);
			em.persist(petDog);

			Dog dog = new Dog();
			dog.setAge((byte) 7);
			dog.setName("My Dog");
			dog.getDogCompetitions().addAll(List.of("Dog Award 2011", "IEAD-54"));
			em.persist(dog);

			Cat cat = new Cat();
			cat.setName("Cat My Kitty");
			cat.setAge((byte) 9);
			cat.setHairless(false);
			cat.setColor(Color.YELLOW);
			em.persist(cat);

			Fish fish = new Fish();
			fish.setSwimmingDepth((short) 43);
			em.persist(fish);
		});
	}
}
