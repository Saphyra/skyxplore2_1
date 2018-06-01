package skyxplore.dataaccess.gamedata.subservice;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.Ability;

public class AbilityService extends AbstractGameDataService<Ability> {
    public AbilityService(String source){
        super(source);
    }

    @Override
    public void init(){
        super.loadFiles(Ability.class);
    }
}
