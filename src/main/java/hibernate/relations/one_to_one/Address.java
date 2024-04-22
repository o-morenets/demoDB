package hibernate.relations.one_to_one;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usr_address")
@Data
public class Address {

	@Id
	@GeneratedValue
	private Long id;

	private String city;

	private String street;

	private String number;

	private String apt;

	@OneToOne
	@JoinColumn(name = "user_id") // default 'user_message_id'
	private User user;
}
