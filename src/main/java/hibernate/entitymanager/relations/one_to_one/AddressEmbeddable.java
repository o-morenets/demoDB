package hibernate.entitymanager.relations.one_to_one;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class AddressEmbeddable {

	private String city;

	private String street;

	private String house;

	private String apartment;

	private String zip;
}
