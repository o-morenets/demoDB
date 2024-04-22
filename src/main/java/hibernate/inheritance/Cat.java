package hibernate.inheritance;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cat extends Pet {

	// inherited fields

	private Boolean hairless;
}
