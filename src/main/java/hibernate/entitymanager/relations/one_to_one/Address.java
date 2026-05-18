package hibernate.entitymanager.relations.one_to_one;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usr_address")
@Data
@NoArgsConstructor
public class Address {

	@Id
	@GeneratedValue
	private Long id;

	private String city;

	private String street;

	private String number;

	private String apt;

	@OneToOne
    @JoinColumn(name = "usr_fk")
	private User user;

	public Address(String city, String street, String number, String apt) {
		this.city = city;
		this.street = street;
		this.number = number;
		this.apt = apt;
	}
}
