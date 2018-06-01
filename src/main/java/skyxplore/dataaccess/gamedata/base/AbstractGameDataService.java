package skyxplore.dataaccess.gamedata.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
@Slf4j
@Getter
public abstract class AbstractGameDataService<V> extends HashMap<String, V> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonFileFilter jsonFilter = new JsonFileFilter();

    private final String source;

    protected void loadFiles(Class<V> clazz){
        File root = new File(source);
        if(!root.exists()){
            throw new IllegalStateException("Source directory does not exists. Path: " + root.getAbsolutePath());
        }
        if(!root.isDirectory()){
            throw new IllegalArgumentException("Source must be a directory. Path: " + root.getAbsolutePath());
        }
        File[] files = root.listFiles(jsonFilter);
        for(File file : files){
            try {
                String key = FilenameUtils.removeExtension(file.getName());
                V content = objectMapper.readValue(file, clazz);
                log.info("Loaded element. Key: {}, Value: {}", key, content);
                put(key, content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void init();
}
