package skyxplore.dataaccess.gamedata.subservice;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Battery;

import javax.annotation.PostConstruct;

@Component
public class BatteryService extends AbstractGameDataService<Battery> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Battery.class);
    }
}
