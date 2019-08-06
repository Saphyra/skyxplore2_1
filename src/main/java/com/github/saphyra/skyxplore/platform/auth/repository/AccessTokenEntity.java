package com.github.saphyra.skyxplore.platform.auth.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "access_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class AccessTokenEntity {
    @Id
    @Column(name = "access_token_id", length = 50)
    private String accessTokenId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "last_access", nullable = false)
    private Long lastAccess;

    @Column(name = "character_id")
    private String characterId;
}
