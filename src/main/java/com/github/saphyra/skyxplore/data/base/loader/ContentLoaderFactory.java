package com.github.saphyra.skyxplore.data.base.loader;

import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.base.ContentLoader;

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

