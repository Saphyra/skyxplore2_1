package skyxplore.domain.character;

import lombok.Data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
@Data
public class SkyXpCharacter {
    private String characterId;
    private String characterName;
    private String userId;
    private String shipId;
    private Integer money;
    private ArrayList<String> equipments;

    public void addEquipment(String equipmentId){
        equipments.add(equipmentId);
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

    public void addEquipments(String elementId, Integer amount){
        for(int i = 0; i < amount; i++){
            addEquipment(elementId);
        }
    }

    public void spendMoney(Integer money) {
        this.money -= money;
    }
}
