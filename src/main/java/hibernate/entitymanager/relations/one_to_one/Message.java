package hibernate.entitymanager.relations.one_to_one;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usr_message")
@Data
public class Message {

	@Id
	@GeneratedValue
	private Long id;

	private String body;
}
