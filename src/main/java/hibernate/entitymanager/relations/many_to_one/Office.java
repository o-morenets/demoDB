package hibernate.entitymanager.relations.many_to_one;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Office {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumns({
			@JoinColumn(name="addr_street", referencedColumnName="street"),
			@JoinColumn(name="addr_zip", referencedColumnName="zip")
	})
	private Address address;

	public Office(String name, Address address) {
		this.name = name;
		this.address = address;
	}
}
