package hibernate.inheritance;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Fish extends Pet {

	// inherited fields

	private Short swimmingDepth;
}
