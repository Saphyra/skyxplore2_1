package skyxplore.dataaccess.gamedata.subservice;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Material;

import javax.annotation.PostConstruct;

@Component
public class MaterialService extends AbstractGameDataService<Material> {

    @Override
    @PostConstruct
    public void init() {
        super.load(Material.class);
    }
}
