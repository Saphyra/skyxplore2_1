package skyxplore.domain.slot;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "equipped_slot")
public class SlotEntity {
    @Id
    @Column(name = "slot_id", length = 50)
    private String slotId;

    @Column(name = "ship_id", nullable = false)
    private String shipId;

    @Column(name = "front_slot", nullable =  false)
    private Integer frontSlot;
    @Column(name = "front_equipped", nullable =  false)
    private String frontEquipped;

    @Column(name = "left_slot", nullable =  false)
    private Integer leftSlot;
    @Column(name = "left_equipped", nullable =  false)
    private String leftEquipped;

    @Column(name = "right_slot", nullable =  false)
    private Integer rightSlot;
    @Column(name = "right_equipped", nullable =  false)
    private String rightEquipped;

    @Column(name = "back_slot", nullable =  false)
    private Integer backSlot;
    @Column(name = "back_equipped", nullable =  false)
    private String backEquipped;
}
