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
            Person person = new Person();
            person.setFirstName("John");
            person.setLastName("Bush");
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
            Person person = new Person();
            person.setFirstName("Martin");
            person.setLastName("Wood");
            person.getFloats().addAll(List.of(123.453f, -987.65f, 0.00054f));
            em.persist(person);
        });
    }

    private static void orphanRemovalDemo() {
        EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
            // 1. Створюємо Person
            Person person = new Person();
            person.setFirstName("Orphan");
            person.setLastName("Orphan");

            // 2. Генеруємо та додаємо 10 нотаток (БЕЗ null елементів)
            List<Note> transientNotes = Stream.generate(() -> {
                        Note newNote = new Note();
                        newNote.setBody("Body " + UUID.randomUUID());
                        return newNote;
                    })
                    .limit(10)
                    .toList();

            // Безпечно додаємо через хелпер-метод
            transientNotes.forEach(person::addNote);

            // Зберігаємо person разом з нотатками (завдяки CascadeType.ALL)
            em.persist(person);

            // Синхронізуємо стан з базою даних, щоб Hibernate зафіксував insert-и
            em.flush();
            System.out.println("--- Спочатку збережено 10 нотаток ---");

            // 3. Демонструємо orphanRemoval
            // Отримуємо колекцію, яка вже контролюється Hibernate (PersistentBag)
            List<Note> managedNotes = person.getNotes();

            // Видаляємо кожну третю нотатку з КІНЦЯ списку,
            // щоб не зламати індекси під час видалення
            for (int i = managedNotes.size() - 1; i >= 0; i--) {
                if (i % 3 == 0) {
                    System.out.println("Removing note at index " + i + " from person");

                    // Видаляємо саме з колекції сутності!
                    managedNotes.remove(i);
                }
            }

            // 4. Знову викликаємо flush, щоб побачити SQL DELETE в консолі
            System.out.println("--- Викликаємо flush для orphanRemoval ---");
            em.flush();

            // Тут Hibernate автоматично виконає DELETE для видалених з колекції нотаток
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
