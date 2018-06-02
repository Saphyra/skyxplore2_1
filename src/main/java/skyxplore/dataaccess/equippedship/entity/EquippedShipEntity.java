package skyxplore.dataaccess.equippedship.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
@Table(name = "equipped_ship")
public class EquippedShipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ship_id")
    private Long shipId;

    @Column(name = "character_id", nullable =  false)
    private Long characterId;

    @Column(name = "ship_type", nullable =  false)
    private String shipType;

    @Column(name = "corehull", nullable =  false)
    private Integer coreHull;

    @Column(name = "connector_slot", nullable =  false)
    private Integer connectorSlot;

    @Column(name = "connector_equipepd", nullable = false)
    private ArrayList<String> connectorEquipped;

    @OneToOne
    @JoinColumn(name = "defense_slot", nullable = false)
    private SlotEntity defenseSlot;

    @OneToOne
    @JoinColumn(name = "weapon_slot", nullable = false)
    private SlotEntity weaponSlot;
}
