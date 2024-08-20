package hibernate.entitymanager.relations.many_to_one;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "office_address")
@Data
public class Address {

	@Id
	@GeneratedValue
	private Long id;

	@Basic(optional = false)
	private String street;

	@Basic(optional = false, fetch = FetchType.EAGER)
	private Integer zip;
}
