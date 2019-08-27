package com.github.saphyra.skyxplore.userdata.ship.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ExtenderService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EquippedShip {
    @NonNull
    private final String shipId;

    @NonNull
    private final String characterId;

    @NonNull
    private String shipType;

    @NonNull
    private Integer coreHull;

    @NonNull
    private Integer connectorSlot;

    @Builder.Default
    @NonNull
    private List<String> connectorEquipped = new ArrayList<>();

    @NonNull
    private final String defenseSlotId;

    @NonNull
    private final String weaponSlotId;

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
