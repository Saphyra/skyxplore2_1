package skyxplore.restcontroller.view;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ShipView {
    private Long shipId;
    private Long characterId;
    private String shipType;
    private Integer coreHull;
    private Integer connectorSlot;
    private ArrayList<String> connectorEquipped;
    private SlotView defenseSlot;
    private SlotView weaponSlot;
}
