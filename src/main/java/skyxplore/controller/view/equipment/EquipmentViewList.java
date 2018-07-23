package skyxplore.controller.view.equipment;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class EquipmentViewList extends ArrayList<String> {
    public EquipmentViewList(List<String> source){
        super(source);
    }
}
