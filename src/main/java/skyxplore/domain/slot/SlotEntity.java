package skyxplore.domain.slot;

import lombok.Data;
import org.hibernate.annotations.Type;

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

    @Column(name = "front_slot", nullable = false)
    private String frontSlot;
    @Column(name = "front_equipped", nullable = false)
    @Type(type = "text")
    private String frontEquipped;

    @Column(name = "left_slot", nullable = false)
    private String leftSlot;
    @Column(name = "left_equipped", nullable = false)
    @Type(type = "text")
    private String leftEquipped;

    @Column(name = "right_slot", nullable = false)
    private String rightSlot;
    @Column(name = "right_equipped", nullable = false)
    @Type(type = "text")
    private String rightEquipped;

    @Column(name = "back_slot", nullable = false)
    private String backSlot;
    @Column(name = "back_equipped", nullable = false)
    @Type(type = "text")
    private String backEquipped;
}
