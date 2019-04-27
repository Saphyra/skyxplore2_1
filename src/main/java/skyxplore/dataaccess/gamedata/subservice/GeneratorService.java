package skyxplore.dataaccess.gamedata.subservice;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Generator;

import javax.annotation.PostConstruct;

@Component
public class GeneratorService extends AbstractGameDataService<Generator> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Generator.class);
    }
}
