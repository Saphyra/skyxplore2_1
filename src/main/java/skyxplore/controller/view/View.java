package skyxplore.controller.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

import java.util.Map;

@Data
@AllArgsConstructor
public class View<T> {
    private T info;
    private Map<String, GeneralDescription> data;
}
