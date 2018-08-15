package skyxplore.domain.character;

import lombok.Data;
import skyxplore.exception.NotEnoughMoneyException;
import skyxplore.exception.base.BadRequestException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unused", "unchecked"})
@Data
//TODO unit test
public class SkyXpCharacter {
    private String characterId;
    private String characterName;
    private String userId;
    private Integer money = 0;
    private ArrayList<String> equipments = new ArrayList<>();

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
        if(this.money < money){
            throw new NotEnoughMoneyException(characterId + " wanted to buy items cost " + money + ", while he had only " + this.money);
        }
        this.money -= money;
    }

    public ArrayList<String> getEquipments() {
        return (ArrayList<String>) equipments.clone();
    }

    private void setEquipments(ArrayList<String> equipments) {
        throw new UnsupportedOperationException("Equipments cannot be set.");
    }

    private void setMoney(Integer money) {
        throw new UnsupportedOperationException("Money cannot be set.");
    }
}
