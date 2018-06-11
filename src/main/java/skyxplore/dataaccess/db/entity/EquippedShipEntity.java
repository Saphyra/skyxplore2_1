package skyxplore.dataaccess.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
@Table(name = "equipped_ship")
public class EquippedShipEntity {
    @Id
    @Column(name = "ship_id", length = 50)
    private String shipId;

    @Column(name = "character_id", nullable =  false)
    private String characterId;

    @Column(name = "ship_type", nullable =  false)
    private String shipType;

    @Column(name = "corehull", nullable =  false)
    private Integer coreHull;

    @Column(name = "connector_slot", nullable =  false)
    private Integer connectorSlot;

    @Column(name = "connector_equipepd", nullable = false)
    private ArrayList<String> connectorEquipped;

    @Column(name = "defense_slot_id", nullable = false)
    private String defenseSlotId;

    @Column(name = "weapon_slot_id", nullable = false)
    private String weaponSlotId;
}
