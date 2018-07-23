package skyxplore.domain.ship;

import lombok.Data;
import skyxplore.exception.base.BadRequestException;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings({"unchecked", "unused", "WeakerAccess"})
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

    public void addConnector(String element) {
        if (connectorSlot.equals(connectorEquipped.size())) {
            throw new BadRequestException("Connector slot is full.");
        }
        connectorEquipped.add(element);
    }

    public void addConnectors(Collection<String> connectors) {
        connectors.forEach(this::addConnector);
    }

    public void removeConnector(String connector) {
        if (!connectorEquipped.remove(connector)) {
            throw new BadRequestException(connector + " is not equipped currently.");
        }
    }

    public void addConnectorSlot(Integer extraSlot){
        connectorSlot += extraSlot;
    }

    public ArrayList<String> getConnectorEquipped() {
        return (ArrayList<String>) connectorEquipped.clone();
    }

    private void setConnectorEquipped(ArrayList<String> a) {
        throw new UnsupportedOperationException("Setting equipped connectors is not supported.");
    }
}
