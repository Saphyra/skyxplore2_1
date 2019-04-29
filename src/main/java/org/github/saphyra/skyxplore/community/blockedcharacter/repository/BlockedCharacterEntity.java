package org.github.saphyra.skyxplore.community.blockedcharacter.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Table(name = "blocked_character")
@Entity
@NoArgsConstructor
@AllArgsConstructor
class BlockedCharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "blocked_character_entity_id")
    private Long blockedCharacterEntityId;

    @Column(name = "character_id")
    private String characterId;

    @Column(name = "blocked_character_id")
    private String blockedCharacterId;
}
