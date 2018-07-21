package skyxplore.domain.character;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
@Data
public class SkyXpCharacter {
    private String characterId;
    private String characterName;
    private String userId;
    private String shipId;
    private Integer money = 0;
    private ArrayList<String> equipments = new ArrayList<>();

    public void addEquipment(String equipmentId){
        equipments.add(equipmentId);
    }

    public void addEquipments(Collection<String> equipments){
        equipments.forEach(this::addEquipment);
    }

    public void addEquipments(String elementId, Integer amount){
        for(int i = 0; i < amount; i++){
            addEquipment(elementId);
        }
    }

    public void addMoney(Integer money) {
        this.money += money;
    }

    public void buyEquipments(Map<String, Integer> items) {
        Set<String> keys = items.keySet();
        keys.forEach(key -> {
            addEquipments(key, items.get(key));
        });
    }

    public void spendMoney(Integer money) {
        this.money -= money;
    }

    private void setEquipments(ArrayList<String> equipments){
        throw new UnsupportedOperationException("Equipments cannot be set.");
    }

    private void setMoney(Integer money){
        throw new UnsupportedOperationException("Money cannot be set.");
    }
}
