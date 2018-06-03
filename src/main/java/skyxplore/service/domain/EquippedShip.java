package skyxplore.service.domain;

import lombok.Data;

import java.util.ArrayList;

@Data
public class EquippedShip {
    private Long shipId;
    private Long characterId;
    private String shipType;
    private Integer coreHull;
    private Integer connectorSlot;
    private ArrayList<String> connectorEquipped = new ArrayList<>();
    private EquippedSlot defenseSlot = new EquippedSlot();
    private EquippedSlot weaponSlot = new EquippedSlot();

    public void addConnector(String element){
        if(connectorSlot.equals(connectorEquipped.size())){
            throw new IllegalStateException("Connector slot is full.");
        }
        connectorEquipped.add(element);
    }
}
