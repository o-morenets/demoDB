package hibernate.entitymanager.relations.one_to_many_bidirectional;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Note> notes = new ArrayList<>();

    /**
     * Creates unidirectional OneToMany relation
     * without related entity, just values
     */
    @ElementCollection
    @CollectionTable(
            name = "person_floats",
            joinColumns = @JoinColumn(name = "p_id"))
    @Column(name = "float_value")
    private Collection<Float> floats = new ArrayList<>();

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addNote(Note note) {
        note.setPerson(this);
        notes.add(note);
    }
}
