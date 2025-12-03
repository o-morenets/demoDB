package hibernate.entitymanager.relations.one_to_many_unidirectional;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "person_id")
    private List<Note> notes;

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
}
