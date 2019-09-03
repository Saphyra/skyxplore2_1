package com.github.saphyra.skyxplore.data.base;

import com.github.saphyra.skyxplore.common.OptionalMap;
import com.github.saphyra.skyxplore.data.base.loader.ContentLoaderFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractDataService<V> extends OptionalMap<String, V> {
    private static final String DEFAULT_PATH = "public/gamedata/items";
    private static final String BASE_DIR = "src/main/resources/";

    private final ContentLoaderFactory<V> contentLoaderFactory;

    @Getter
    private final String path;

    @Getter
    private final String jarPath;

    @Getter
    private final boolean shouldCheckType;

    public AbstractDataService() {
        path = BASE_DIR + DEFAULT_PATH;
        jarPath = BASE_DIR;
        shouldCheckType = true;
        this.contentLoaderFactory = new ContentLoaderFactory<>();
    }

    public AbstractDataService(String path) {
        this.path = BASE_DIR + path;
        jarPath = BASE_DIR;
        shouldCheckType = false;
        this.contentLoaderFactory = new ContentLoaderFactory<>();
    }

    protected void load(Class<V> clazz) {
        contentLoaderFactory.getInstance(clazz, this).load();
    }

    public abstract void init();
}
