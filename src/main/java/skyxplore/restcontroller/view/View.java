package skyxplore.restcontroller.view;

import lombok.Data;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

import java.util.Map;

@Data
public class View<T> {
    private T info;
    private Map<String, GeneralDescription> data;
}
