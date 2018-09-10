package skyxplore.dataaccess.gamedata.base.loader;

import java.io.File;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.base.ContentLoader;

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

