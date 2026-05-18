package hibernate.entitymanager.relations.one_to_one;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class AddressEmbeddable {

	private String city;

	private String street;

	private String house;

	private String apartment;

	private String zip;

	public AddressEmbeddable(String city, String street, String house, String apartment, String zip) {
		this.city = city;
		this.street = street;
		this.house = house;
		this.apartment = apartment;
		this.zip = zip;
	}
}
