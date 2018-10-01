package skyxplore.dataaccess.gamedata.base.loader;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.base.ContentLoader;

import java.io.File;

public class ContentLoaderFactory<T> {
    public ContentLoader getInstance(Class<T> clazz, AbstractGameDataService<T> gameDataService) {
        File root = new File(gameDataService.getPath());
        if (root.exists()) {
            return new FileLoader<>(clazz, gameDataService);
        } else {
            return new JarLoader<>(clazz, gameDataService);
        }
    }
}

