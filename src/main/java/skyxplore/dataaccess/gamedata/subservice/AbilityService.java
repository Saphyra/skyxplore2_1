package skyxplore.dataaccess.gamedata.subservice;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Ability;

import javax.annotation.PostConstruct;

@Component
public class AbilityService extends AbstractGameDataService<Ability> {
    @Override
    @PostConstruct
    public void init() {
        super.load(Ability.class);
    }
}
