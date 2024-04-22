package hibernate.relations.one_to_many_bidirectional;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "order_details") // 'order' is a reserved word in Postgres
@Data
@ToString(exclude = "customer")
@EqualsAndHashCode(exclude = "customer")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	private Double amount;

	@ManyToOne
	@JoinColumn(name = "cust_id")
	private Customer customer;
}
