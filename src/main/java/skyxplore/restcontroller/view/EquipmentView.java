package skyxplore.restcontroller.view;

import lombok.Data;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

import java.util.Set;

@Data
public class EquipmentView<T> {
    private T info;
    private Set<GeneralDescription> data;
}
