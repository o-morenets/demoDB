package hibernate.entitymanager.relations.one_to_many_unidirectional;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL) // CascadeType.PERSIST works only with EntityManager, not with Hibernate Session
    @JoinColumn(name = "customer_id") // without @JoinColumn, one-directional relationship will create join-table 'customer_order_details'
    private Set<Order> orders = new HashSet<>();

    public Customer(String name) {
        this.name = name;
    }
}