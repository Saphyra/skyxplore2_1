package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;

public class CategoryService  extends AbstractGameDataService<String> {
    public CategoryService(String source) {
        super(source);
    }

    @Override
    public void init() {
        super.loadFiles(String.class);
    }
}
