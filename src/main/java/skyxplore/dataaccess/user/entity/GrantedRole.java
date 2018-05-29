package skyxplore.dataaccess.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class GrantedRole  {

    @Id
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role", unique = true)
    @Enumerated(EnumType.STRING)
    private Role role;
}
