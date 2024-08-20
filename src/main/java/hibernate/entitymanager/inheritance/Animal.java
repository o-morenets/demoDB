package hibernate.entitymanager.inheritance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

//@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
public abstract class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Color color;
}
