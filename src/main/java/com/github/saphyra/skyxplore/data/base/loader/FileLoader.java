package com.github.saphyra.skyxplore.data.base.loader;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.TypedItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

import static java.util.Objects.isNull;

@Slf4j
class FileLoader<T> extends AbstractLoader<T> {
    private static final JsonFileFilter jsonFilter = new JsonFileFilter();

    private final File root;
    private final AbstractDataService<T> dataService;

    FileLoader(Class<T> clazz, AbstractDataService<T> dataService) {
        super(clazz);
        this.dataService = dataService;
        this.root = new File(dataService.getPath());
    }

    @Override
    public void load() {
        log.debug("Loading elements from file.");
        if (!root.isDirectory()) {
            throw new IllegalArgumentException("Source must be a directory. Path: " + root.getAbsolutePath());
        }
        File[] files = root.listFiles(jsonFilter);
        for (File file : files) {
            log.debug("Loading item {}", file.getName());
            TypedItem typedItem = getTypedItem(file);
            if (!dataService.isShouldCheckType() || isTypeMatches(typedItem)) {
                loadFile(file);
            } else {
                log.debug("Skipping {}, it is not the type of {}, it is a {}", file, getClassName(), typedItem.getType());
            }
        }
    }

    private TypedItem getTypedItem(File file) {
        TypedItem typedItem = FileUtil.readValue(file, TypedItem.class);
        if (isNull(typedItem.getType())) {
            log.warn("{} has no type.", file.getName());
        }
        return typedItem;
    }

    @SuppressWarnings("unchecked")
    private void loadFile(File file) {
        if (clazz == String.class) {
            String key = FilenameUtils.removeExtension(file.getName());
            dataService.put(key, (T) FileUtil.readFileToString(file));
        } else {
            parseFile(file);
        }
    }

    private void parseFile(File file) {
        T content = FileUtil.readValue(file, clazz);
        putGeneralDescription(content, dataService, file.getName());
    }
}
