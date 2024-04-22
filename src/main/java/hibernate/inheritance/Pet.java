package hibernate.inheritance;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pet extends Animal {

	// inherited fields

	private String name;

	private Byte age;
}
