package hibernate.entitymanager.relations.one_to_one;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "usr") // because 'user' is a reserved word in Postgres
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne(mappedBy = "user")
    private Address address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToOne // no 'mappedBy', so this is owning side
    @JoinColumn(name = "msg_id") // name of FK, default - 'message_id'
    private Message message;

    // helper method for address
    public void setAddress(Address address) {
        address.setUser(this);
        this.address = address;
    }

    // helper method for profile
    public void setProfile(Profile profile) {
        profile.setUser(this);
        this.profile = profile;
    }
}
