package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Generator;

public class GeneratorService extends AbstractGameDataService<Generator> {
    public GeneratorService(String source) {
        super(source);
    }

    @Override
    public void init() {
        super.loadFiles(Generator.class);
    }
}
