package hibernate.session.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "departments")
public class Department {

    @Id
    @Column(name = "dept_no", length = 4)
    private String id;

    @Column(name = "dept_name")
    private String name;
}
