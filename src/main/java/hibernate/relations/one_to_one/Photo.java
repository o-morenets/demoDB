package hibernate.relations.one_to_one;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usr_photo")
public class Photo {

	@Id
	private Long id;

	private String photo_url;
}
