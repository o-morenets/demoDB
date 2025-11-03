package hibernate.entitymanager.inheritance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass // entity is not created, only common fields used
@Getter
@Setter
public abstract class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Color color;
}
