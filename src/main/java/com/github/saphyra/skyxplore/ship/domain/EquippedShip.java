package com.github.saphyra.skyxplore.ship.domain;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EquippedShip {
    private String shipId;
    private String characterId;
    private String shipType;
    private Integer coreHull;
    private Integer connectorSlot;

    @Builder.Default
    private List<String> connectorEquipped = new ArrayList<>();

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
            throw new BadRequestException(connector + " is not equipped currently. Equipped elements: " + connectorEquipped.toString());
        }
    }

    public void addConnectorSlot(Integer extraSlot) {
        connectorSlot += extraSlot;
    }

    public void removeConnectorSlot(Integer slot, SkyXpCharacter character, ExtenderService extenderService) {
        connectorSlot -= slot;

        while (connectorSlot < connectorEquipped.size()) {
            for (String item : connectorEquipped) {
                if (extenderService.get(item) == null) {
                    removeConnector(item);
                    character.addEquipment(item);
                    break;
                }
            }
        }
    }

    public List<String> getConnectorEquipped() {
        return new ArrayList<>(connectorEquipped);
    }

    @SuppressWarnings("unused")
    private void setConnectorEquipped(ArrayList<String> a) {
        throw new UnsupportedOperationException("Setting equipped connectors is not supported.");
    }
}