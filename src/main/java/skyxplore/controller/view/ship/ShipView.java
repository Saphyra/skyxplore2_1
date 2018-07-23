package skyxplore.controller.view.ship;

import lombok.Data;
import skyxplore.controller.view.slot.SlotView;

import java.util.ArrayList;

@Data
public class ShipView {
    private String shipId;
    private String characterId;
    private String shipType;
    private Integer coreHull;
    private Integer connectorSlot;
    private ArrayList<String> connectorEquipped;
    private SlotView defenseSlot;
    private SlotView weaponSlot;
    private ArrayList<String> ability;
}
