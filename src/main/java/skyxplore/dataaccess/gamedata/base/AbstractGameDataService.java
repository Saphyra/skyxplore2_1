package skyxplore.dataaccess.gamedata.base;

import java.util.HashMap;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.gamedata.base.loader.ContentLoaderFactory;

@Slf4j
public abstract class AbstractGameDataService<V> extends HashMap<String, V> {
    private static final String RESOURCES_DIR = "data/gamedata/";
    private static final String BASE_DIR = "src/main/resources/" + RESOURCES_DIR;

    private final ContentLoaderFactory<V> contentLoaderFactory;

    @Getter
    private final String path;

    @Getter
    private final String jarPath;

    public AbstractGameDataService(String source) {
        this.jarPath = RESOURCES_DIR + source;
        this.path = BASE_DIR + source;
        this.contentLoaderFactory = new ContentLoaderFactory<>();
    }

    protected void load(Class<V> clazz) {
        contentLoaderFactory.getInstance(clazz, this).load();
    }

    public abstract void init();
}
