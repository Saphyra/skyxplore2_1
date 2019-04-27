package org.github.saphyra.skyxplore.user.repository.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.github.saphyra.skyxplore.user.domain.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;

@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class UserEntity {
    @Id
    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "roles", nullable = false)
    private HashSet<Role> roles;

    @Column(nullable = false)
    private String email;

    HashSet<Role> getRoles() {
        return new HashSet<>(roles);
    }
}
