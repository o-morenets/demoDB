package hibernate.entitymanager.relations.many_to_many;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;

	private String lastName;

	@Setter(AccessLevel.PRIVATE)
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "employee_guilds",
			joinColumns = @JoinColumn(name = "employee_id"),
			inverseJoinColumns = @JoinColumn(name = "guild_id"))
	private List<Guild> guilds = new ArrayList<>();
}
