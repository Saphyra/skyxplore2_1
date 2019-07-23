package com.github.saphyra.skyxplore.userdata.ship.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "equipped_ship")
@NoArgsConstructor
@AllArgsConstructor
@Builder
class EquippedShipEntity {
    @Id
    @Column(name = "ship_id", length = 50)
    private String shipId;

    @Column(name = "character_id", nullable = false)
    private String characterId;

    @Column(name = "ship_type", nullable = false)
    private String shipType;

    @Column(name = "corehull", nullable = false)
    private String coreHull;

    @Column(name = "connector_slot", nullable = false)
    private String connectorSlot;

    @Column(name = "connector_equipepd", nullable = false)
    @Type(type = "text")
    private String connectorEquipped;

    @Column(name = "defense_slot_id", nullable = false)
    private String defenseSlotId;

    @Column(name = "weapon_slot_id", nullable = false)
    private String weaponSlotId;
}
