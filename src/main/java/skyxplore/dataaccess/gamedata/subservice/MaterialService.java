package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Material;

import javax.annotation.PostConstruct;

public class MaterialService extends AbstractGameDataService<Material> {
    public MaterialService(String source) {
        super(source);
    }

    @Override
    public void init() {
        super.load(Material.class);
    }
}
