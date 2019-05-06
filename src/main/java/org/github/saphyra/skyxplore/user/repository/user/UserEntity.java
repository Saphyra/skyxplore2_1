package org.github.saphyra.skyxplore.user.repository.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String roles;

    @Column(nullable = false)
    private String email;
}
