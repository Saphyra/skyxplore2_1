package skyxplore.dataaccess.gamedata.subservice;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Extender;

import javax.annotation.PostConstruct;

@Component
public class ExtenderService extends AbstractGameDataService<Extender> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Extender.class);
    }
}
