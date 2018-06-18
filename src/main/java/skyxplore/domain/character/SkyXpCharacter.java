package skyxplore.domain.character;

import lombok.Data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Data
public class SkyXpCharacter {
    private String characterId;
    private String characterName;
    private String userId;
    private String shipId;
    private Integer money;
    private ArrayList<String> equipments;

    public void addMoney(Integer money) {
        this.money += money;
    }

    public void buyEquipments(Map<String, Integer> items) {
        Set<String> keys = items.keySet();
        keys.forEach(key -> {
            for(Integer i = 0; i < items.get(key); i++){
                equipments.add(key);
            }
        });
    }

    public void spendMoney(Integer money) {
        this.money -= money;
    }
}
