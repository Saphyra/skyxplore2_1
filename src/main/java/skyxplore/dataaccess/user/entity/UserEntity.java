package skyxplore.dataaccess.user.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "user_name")
    private String username;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false)
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<GrantedRole> roles;

    @Column(nullable = false)
    private String email;
}
