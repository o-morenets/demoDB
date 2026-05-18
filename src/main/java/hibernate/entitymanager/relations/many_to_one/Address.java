package hibernate.entitymanager.relations.many_to_one;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "office_address")
@Data
@NoArgsConstructor
public class Address {

	@Id
	@GeneratedValue
	private Long id;

	@Basic(optional = false)
	private String street;

	@Basic(optional = false, fetch = FetchType.EAGER)
	private Integer zip;

	public Address(String street, Integer zip) {
		this.street = street;
		this.zip = zip;
	}
}
