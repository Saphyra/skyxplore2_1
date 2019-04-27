package skyxplore.dataaccess.gamedata.subservice;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.CoreHull;

import javax.annotation.PostConstruct;

@Component
public class CoreHullService extends AbstractGameDataService<CoreHull> {

    @Override
    @PostConstruct
    public void init() {
        super.load(CoreHull.class);
    }
}
