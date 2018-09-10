package skyxplore.dataaccess.gamedata.base.loader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.base.JsonFileFilter;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "unchecked"})
@Slf4j
public class FileLoader<T> extends AbstractLoader<T> {
    private static final JsonFileFilter jsonFilter = new JsonFileFilter();

    private final Class<T> clazz;
    private final File root;
    private final AbstractGameDataService<T> gameDataService;

    public FileLoader(Class<T> clazz, AbstractGameDataService<T> gameDataService) {
        this.clazz = clazz;
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
            loadFile(file);
        }
    }

    private void loadFile(File file) {
        try {
            if (clazz == String.class) {
                String key = FilenameUtils.removeExtension(file.getName());
                gameDataService.put(key, (T) FileUtils.readFileToString(file));
            } else {
                parseFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseFile(File file) throws IOException {
        T content = objectMapper.readValue(file, clazz);
        putGeneralDescription(content, gameDataService, gameDataService.getPath());
    }
}
