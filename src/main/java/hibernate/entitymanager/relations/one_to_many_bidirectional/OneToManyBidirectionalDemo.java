package hibernate.entitymanager.relations.one_to_many_bidirectional;

import hibernate.entitymanager.relations.EntityManagerUtilsRelations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * One-To-Many
 * Bidirectional One-to-Many association
 */
public class OneToManyBidirectionalDemo {

    public static void main(String[] args) {

        /* https://youtu.be/sOTONItpMe8?list=PLqt5_5aU1KQLFZH-Rltag_AvHtQvDHhzG&t=2603 */
        System.out.println("Saving Person with Notes (using cascade operation)");
        persistNewPersonWithNewNote();

        System.out.println("Saving new Note linked to the existing Person");
        persistNewNote();

        System.out.println("Saving new Note linked to the existing Person - no persist()");
        addNewNote();

        System.out.println("Saving new Note by personId without loading Person to the session");
        persistNewNoteUsingProxy();
        /* --------------------------------------------------------------------------- */

        System.out.println("ElementCollection example");
        persistPersonWithElementCollection();

        System.out.println("Orphan removal example");
        orphanRemovalDemo();

        System.out.println("1 + N problem example");
        selectFromPersonOnePlusNProblem();
    }

    private static void persistNewPersonWithNewNote() {
        EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
            Person person = new Person("John", "Doe");
            em.persist(person);

            Note note = new Note("Hello!");
            person.addNote(note); // use helper method
//			em.persist(note); // not needed, as cascade = CascadeType.PERSIST in Person

//            person.setNotes(List.of(note1, note)); // DON'T DO THIS !!!
//            person.getNotes().addAll(List.of(note1, note)); // when no helper method

            // Person is in persistent state, so it saves all notes, that were already persisted above
        });
    }

    private static void persistNewNote() {
        EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
            Person person = em.find(Person.class, 1L);
            Note note = new Note("This one requires Person entity instance");
            note.setPerson(person);
            em.persist(note);
        });
    }

    private static void addNewNote() {
        EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
            Person person = em.find(Person.class, 1L);
            Note note = new Note("I was added via addNote() method");
            person.addNote(note);
        });
    }

    private static void persistNewNoteUsingProxy() {
        EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
            Person personRef = em.getReference(Person.class, 1L);
            Note note = new Note("Added using Person proxy");
            note.setPerson(personRef);
            em.persist(note);
        });
    }

    private static void persistPersonWithElementCollection() {
        EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
            Person person = new Person("Martin", "Fowler");
            person.getFloats().addAll(List.of(123.453f, -987.65f, 0.00054f));
            em.persist(person);
        });
    }

    private static void orphanRemovalDemo() {
        EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
            // 1. Create Person
            Person person = new Person("Orphan", "Orphan");

            // 2. Generate and add 10 Notes (WITHOUT null elements)
            List<Note> transientNotes = Stream.generate(() -> {
                        Note newNote = new Note();
                        newNote.setBody("Body " + UUID.randomUUID());
                        return newNote;
                    })
                    .limit(10)
                    .toList();

            // Add Notes using helper method
            transientNotes.forEach(person::addNote);

            // Save person with Notes (due to CascadeType.ALL)
            em.persist(person);

            // Synchronize state with DB, so Hibernate fixes inserts
            em.flush();
            System.out.println("--- Firstly, save 10 notes ---");

            // 3. orphanRemoval
            // Get collection which is already managed by Hibernate (PersistentBag)
            List<Note> managedNotes = person.getNotes();

            // Remove each 3rd Note from the end of list, to not corrupt indexes while removing
            for (int i = managedNotes.size() - 1; i >= 0; i--) {
                if (i % 3 == 0) {
                    System.out.println("Removing note at index " + i + " from person");

                    // Remove from entities!
                    managedNotes.remove(i);
                }
            }

            // 4. Invoke flush again to see SQL DELETE in console
            System.out.println("--- Invoke flush for orphanRemoval ---");
            em.flush();

            // Here, Hibernate automatically performs DELETE for Notes that were removed from entities-collection
        });
    }

    private static void selectFromPersonOnePlusNProblem() {
        EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
            String selectString = "from Person p"; // 1 + N
//			String selectString = "from Person p left join fetch p.notes"; // fix 1 + N
            // we can omit 'left' if we don't need persons without notes
            // (or we are sure that all persons have at least one note)

            System.out.println("Selecting Person from Person p...");
            List<Person> resultList = em.createQuery(selectString, Person.class).getResultList();
            System.out.println("ResultList of Person was loaded");
            resultList.forEach(person -> System.out.println(person.getId() + ": " + person.getNotes()));

            // ---------------------------------------------------------------------------------------------------------
            // if fetchType=EAGER AND hibernate creates SQL query itself (like for find() method),
            // then it fetches all in one query using left join
            em.clear();
            System.out.println("------------------------------ Notes for Person with ID=1:");
            Person person = em.find(Person.class, 1L);
            person.getNotes().forEach(note -> System.out.println(note.getId() + ": " + note.getBody()));
        });
    }
}
