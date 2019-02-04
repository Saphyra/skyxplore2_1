package skyxplore.dataaccess.gamedata.base;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.gamedata.base.loader.ContentLoaderFactory;

import java.util.HashMap;

@Slf4j
public abstract class AbstractGameDataService<V> extends HashMap<String, V> {
    private static final String RESOURCES_DIR = "public/gamedata/items";
    private static final String BASE_DIR = "src/main/resources/" + RESOURCES_DIR;

    private final ContentLoaderFactory<V> contentLoaderFactory;

    @Getter
    private final String path = BASE_DIR;

    @Getter
    private final String jarPath = RESOURCES_DIR;

    public AbstractGameDataService() {
        this.contentLoaderFactory = new ContentLoaderFactory<>();
    }

    protected void load(Class<V> clazz) {
        contentLoaderFactory.getInstance(clazz, this).load();
    }

    public abstract void init();
}
