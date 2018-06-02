package skyxplore.dataaccess.equippedship.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "equipped_ship")
public class EquippedShipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ship_id")
    private Long shipId;

    @Column(name = "user_id", nullable =  false)
    private Long userId;

    @Column(name = "ship_name", nullable =  false)
    private String shipName;

    @Column(name = "ship_type", nullable =  false)
    private String type;

    @Column(name = "corehull", nullable =  false)
    private Integer coreHull;

    @Column(name = "connector_slot", nullable =  false)
    private Integer connectorSlot;

    @OneToOne
    @JoinColumn(name = "defense_slot")
    private SlotEntity defenseSlot;

    @OneToOne
    @JoinColumn(name = "weapon_slot")
    private SlotEntity weaponSlot;
}
