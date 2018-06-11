package skyxplore.dataaccess.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;

@Entity
@Table(name = "user")
@Data
public class UserEntity {
    @Id
    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(nullable = false, name = "user_name")
    private String username;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "roles", nullable =  false)
    private HashSet<Role> roles;

    @Column(nullable = false)
    private String email;
}
