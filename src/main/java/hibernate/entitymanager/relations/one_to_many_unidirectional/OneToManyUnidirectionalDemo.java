package hibernate.entitymanager.relations.one_to_many_unidirectional;

import hibernate.entitymanager.relations.EntityManagerUtilsRelations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * One-To-Many
 * Unidirectional One-to-Many association
 * (Note doesn't "know" about Person)
 */
public class OneToManyUnidirectionalDemo {

	public static void main(String[] args) {
        System.out.println("Saving Person with Notes");
		persistPersonWithNotes();

        System.out.println("Orphan removal");
		orphanRemovalDemo();

        System.out.println("1 + N");
        selectFromPersonOnePlusNProblem();
	}

	private static void persistPersonWithNotes() {
		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			Person person = new Person();
			person.setFirstName("John");
			person.setLastName("Bush");
			em.persist(person);

			Note note1 = new Note();
			note1.setBody("Body 1");
//			em.persist(note1); // not needed, as cascade = CascadeType.ALL in Person

			Note note2 = new Note();
			note2.setBody("Body 2");
//			em.persist(note2); // not needed, as cascade = CascadeType.ALL in Person

			person.setNotes(List.of(note1, note2)); // set notes on person's side

			// Person is in persistent state, so it saves all notes, that were already persisted above
		});
	}

	private static void orphanRemovalDemo() {
		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			Person person = new Person();
			person.setFirstName("Orphan");
			person.setLastName("Orphan");
			em.persist(person);

			List<Note> notesList = Stream.generate(() -> {
						Note newNote = new Note();
						newNote.setBody("Body " + UUID.randomUUID());
						return newNote;
					})
					.limit(10)
					.collect(Collectors.toList());

			person.setNotes(notesList);

			for (int i = 0; i < 10; i++) {
				if (i % 3 == 0) {
                    System.out.println("Setting null to person " + i);
					notesList.set(i, null); // <-- should be removed from db table
				}
			}
		});
	}

	private static void selectFromPersonOnePlusNProblem() {
		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			String selectString = "from Person p"; // 1 + N
//			String selectString = "from Person p left join fetch p.notes"; // fix 1 + N

			em.createQuery(selectString, Person.class)
					.getResultStream()
					.forEach(person -> System.out.println(person.getId() + ": " + person.getNotes()));
		});
	}
}
