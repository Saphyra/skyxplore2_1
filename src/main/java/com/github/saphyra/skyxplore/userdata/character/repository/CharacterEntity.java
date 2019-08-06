package com.github.saphyra.skyxplore.userdata.character.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skyxp_character")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class CharacterEntity {
    @Id
    @Column(name = "character_id", length = 50)
    private String characterId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "character_name", nullable = false)
    private String characterName;

    @Column(name = "money", nullable = false)
    private String money;

    @Column(name = "equipments", nullable = false)
    @Type(type = "text")
    private String equipments;
}
