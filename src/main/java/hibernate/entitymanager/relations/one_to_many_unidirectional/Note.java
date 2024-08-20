package hibernate.entitymanager.relations.one_to_many_unidirectional;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "body")
	private String body;
}

