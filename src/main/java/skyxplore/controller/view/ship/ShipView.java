package skyxplore.controller.view.ship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import skyxplore.controller.view.slot.SlotView;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipView {
    private String shipId;
    private String characterId;
    private String shipType;
    private Integer coreHull;
    private Integer connectorSlot;
    private List<String> connectorEquipped;
    private SlotView defenseSlot;
    private SlotView weaponSlot;
    private List<String> ability;
}
