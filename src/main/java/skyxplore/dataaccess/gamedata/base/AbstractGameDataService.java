package skyxplore.dataaccess.gamedata.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
public abstract class AbstractGameDataService<V> extends HashMap<String, V> {
    public static final String BASE_DIR = "src/main/resources/data/gamedata/";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonFileFilter jsonFilter = new JsonFileFilter();

    private final String source;

    public AbstractGameDataService(String source) {
        this.source = BASE_DIR + source;
    }

    protected void loadFiles(Class<V> clazz) {
        File root = new File(source);
        if (!root.exists()) {
            throw new IllegalStateException("Source directory does not exists. Path: " + root.getAbsolutePath());
        }
        if (!root.isDirectory()) {
            throw new IllegalArgumentException("Source must be a directory. Path: " + root.getAbsolutePath());
        }
        File[] files = root.listFiles(jsonFilter);
        for (File file : files) {
            String key = FilenameUtils.removeExtension(file.getName());
            try {
                if (clazz == String.class) {
                     put(key, (V) FileUtils.readFileToString(file));
                } else {
                    V content = objectMapper.readValue(file, clazz);
                    if (content instanceof GeneralDescription) {
                        GeneralDescription d = (GeneralDescription) content;
                        log.info("Loaded element. Key: {}, Value: {}", key, content);
                        put(d.getId(), content);
                    } else {
                        throw new RuntimeException(source + " cannot be loaded. Unknown data type.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void init();
}
