package hibernate.relations.one_to_one;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usr_profile")
@Data
public class Profile {

	@Id
//	@GeneratedValue // don't need it as we will use User's id. Instead, we use @MapsId on User field
	private Long id;

	private boolean active;

	private String photoUrl;

	@OneToOne
	@MapsId // means that, apart from Foreign Key, it is treated as id
	@JoinColumn(name = "user_id") // default 'user_message_id'
	private User user;

	@Embedded
	private AddressEmbeddable address;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "city", column = @Column(name = "office_city")),
			@AttributeOverride(name = "street", column = @Column(name = "office_street")),
			@AttributeOverride(name = "house", column = @Column(name = "office_house")),
			@AttributeOverride(name = "apartment", column = @Column(name = "office_apartment")),
			@AttributeOverride(name = "zip", column = @Column(name = "office_zip"))
	})
	private AddressEmbeddable officeAddress;

}
