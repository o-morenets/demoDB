package hibernate.inheritance;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Dog extends Pet {

	// inherited fields

	@ElementCollection
	private Collection<String> dogCompetitions = new ArrayList<>();
}
