package skyxplore.dataaccess.gamedata.subservice;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;

import javax.annotation.PostConstruct;

@Component
public class CategoryService extends AbstractGameDataService<String> {

    @Override
    @PostConstruct
    public void init() {
        super.load(String.class);
    }
}
