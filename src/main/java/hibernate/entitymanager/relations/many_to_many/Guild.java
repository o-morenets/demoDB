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
public class Guild {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Setter(AccessLevel.PRIVATE)
	@ManyToMany(mappedBy = "guilds", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	List<Employee> employees = new ArrayList<>();

	// helper method
	public void addEmployee(Employee employee) {
		employees.add(employee);
        employee.getGuilds().add(this);
    }
}
