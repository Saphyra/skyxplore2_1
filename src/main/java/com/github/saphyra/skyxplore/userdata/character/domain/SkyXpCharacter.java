package com.github.saphyra.skyxplore.userdata.character.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.common.exception.NotEnoughMoneyException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SkyXpCharacter {
    @NonNull
    private final String characterId;

    @NonNull
    private String characterName;

    @NonNull
    private final String userId;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @NonNull
    private Integer money = 0;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @NonNull
    private List<String> equipments = new ArrayList<>();

    public void addEquipment(String equipmentId) {
        equipments.add(equipmentId);
    }

    public void addEquipments(Collection<String> equipments) {
        equipments.forEach(this::addEquipment);
    }

    public void addEquipments(String elementId, Integer amount) {
        for (int i = 0; i < amount; i++) {
            addEquipment(elementId);
        }
    }

    public void removeEquipment(String equipment) {
        if (!equipments.remove(equipment)) {
            throw new BadRequestException("Character's storage does not contain " + equipment);
        }
    }

    public void addMoney(Integer money) {
        this.money += money;
    }

    public void buyEquipments(Map<String, Integer> items, Integer cost) {
        spendMoney(cost);
        Set<String> keys = items.keySet();
        keys.forEach(key -> addEquipments(key, items.get(key)));
    }

    public void spendMoney(Integer money) {
        if (this.money < money) {
            throw new NotEnoughMoneyException(characterId + " wanted to buy items cost " + money + ", while he had only " + this.money);
        }
        this.money -= money;
    }

    public ArrayList<String> getEquipments() {
        return new ArrayList<>(equipments);
    }
}
