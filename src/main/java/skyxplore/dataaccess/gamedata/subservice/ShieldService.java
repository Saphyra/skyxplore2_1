package skyxplore.dataaccess.gamedata.subservice;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Shield;

import javax.annotation.PostConstruct;

@Component
public class ShieldService extends AbstractGameDataService<Shield> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Shield.class);
    }
}
