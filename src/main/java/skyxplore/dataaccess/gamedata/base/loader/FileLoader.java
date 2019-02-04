package skyxplore.dataaccess.gamedata.base.loader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.base.TypedItem;
import skyxplore.util.FileUtil;

import java.io.File;

import static java.util.Objects.isNull;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "unchecked"})
@Slf4j
public class FileLoader<T> extends AbstractLoader<T> {
    private static final JsonFileFilter jsonFilter = new JsonFileFilter();

    private final File root;
    private final AbstractGameDataService<T> gameDataService;

    public FileLoader(Class<T> clazz, AbstractGameDataService<T> gameDataService) {
        super(clazz);
        this.gameDataService = gameDataService;
        this.root = new File(gameDataService.getPath());
    }

    @Override
    public void load() {
        log.info("Loading elements from file.");
        if (!root.isDirectory()) {
            throw new IllegalArgumentException("Source must be a directory. Path: " + root.getAbsolutePath());
        }
        File[] files = root.listFiles(jsonFilter);
        for (File file : files) {
            TypedItem typedItem = getTypedItem(file);
            if (isTypeMatches(typedItem)) {
                loadFile(file);
            } else {
                log.debug("Skipping {}, it is not the type of {}, it is a {}", file, getClassName(), typedItem.getType());
            }
        }
    }

    private TypedItem getTypedItem(File file) {
        TypedItem typedItem = FileUtil.readValue(objectMapper, file, TypedItem.class);
        if (isNull(typedItem.getType())) {
            log.warn("{} has no type.", file.getName());
        }
        return typedItem;
    }

    private void loadFile(File file) {
        if (clazz == String.class) {
            String key = FilenameUtils.removeExtension(file.getName());
            gameDataService.put(key, (T) FileUtil.readFileToString(file));
        } else {
            parseFile(file);
        }
    }

    private void parseFile(File file) {
        T content = FileUtil.readValue(objectMapper, file, clazz);
        putGeneralDescription(content, gameDataService, gameDataService.getPath());
    }
}
