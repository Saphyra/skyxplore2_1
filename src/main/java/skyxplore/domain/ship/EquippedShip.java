package skyxplore.domain.ship;

import lombok.Data;

import java.util.ArrayList;

@Data
public class EquippedShip {
    private String shipId;
    private String characterId;
    private String shipType;
    private Integer coreHull;
    private Integer connectorSlot;
    private ArrayList<String> connectorEquipped = new ArrayList<>();
    private String defenseSlotId;
    private String weaponSlotId;

    public void addConnector(String element){
        if(connectorSlot.equals(connectorEquipped.size())){
            throw new IllegalStateException("Connector slot is full.");
        }
        connectorEquipped.add(element);
    }
}
