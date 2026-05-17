package hibernate.entitymanager.relations.one_to_many_bidirectional;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "person")
@NoArgsConstructor
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "body")
	private String body;

	@ManyToOne(optional = false)
	@JoinColumn(name = "person_id")
	private Person person;

	public Note(String body) {
		this.body = body;
	}
}

