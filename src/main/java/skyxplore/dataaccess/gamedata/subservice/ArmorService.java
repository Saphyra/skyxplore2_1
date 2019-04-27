package skyxplore.dataaccess.gamedata.subservice;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Armor;

import javax.annotation.PostConstruct;

@Component
public class ArmorService extends AbstractGameDataService<Armor> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Armor.class);
    }
}
