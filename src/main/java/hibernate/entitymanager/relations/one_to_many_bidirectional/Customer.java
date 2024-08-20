package hibernate.entitymanager.relations.one_to_many_bidirectional;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<Order> orders = new HashSet<>();

	// helper method
	public void addOrder(Order order) {
		order.setCustomer(this);
		orders.add(order);
	}
}