package hibernate.entitymanager.relations.one_to_many_unidirectional;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_details") // 'order' is a reserved word in Postgres
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double amount;

    public Order(String description, Double amount) {
        this.description = description;
        this.amount = amount;
    }
}
