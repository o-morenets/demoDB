package hibernate.session.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Film {

    @Id
    @Column(name = "film_id")
    private short id;
}
