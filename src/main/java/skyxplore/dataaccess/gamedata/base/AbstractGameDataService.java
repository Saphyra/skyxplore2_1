package skyxplore.dataaccess.gamedata.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
//TODO refactor
public abstract class AbstractGameDataService<V> extends HashMap<String, V> {
    public static final String RESOURCES_DIR = "data/gamedata/";
    public static final String BASE_DIR = "src/main/resources/" + RESOURCES_DIR;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonFileFilter jsonFilter = new JsonFileFilter();

    private final String source;
    private final String path;
    private final String jarPath;

    public AbstractGameDataService(String source) {
        this.source = source;
        this.jarPath = RESOURCES_DIR + source;
        this.path = BASE_DIR + source;
    }

    protected void loadFiles(Class<V> clazz) {
        File root = new File(path);
        if (!root.exists()) {
            loadFromJar(clazz);
        } else {
            loadFromFile(root, clazz);
        }
    }

    private void loadFromJar(Class<V> clazz) {
        try {
            log.info("Loading from JAR... JarPath: {}", jarPath);
            CodeSource src = AbstractGameDataService.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                JarURLConnection urlcon = (JarURLConnection) (jar.openConnection());
                JarFile jarFile = urlcon.getJarFile();
                Enumeration<JarEntry> entries = jarFile.entries();
                while(entries.hasMoreElements()){
                    JarEntry entry = entries.nextElement();
                    String entryName = entry.getName();

                    if(entryName.startsWith(jarPath) && entryName.endsWith(".json")){
                        StringBuilder builder = new StringBuilder();
                        log.info("Matched element: {}", entryName);
                        try(BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(entry)))){
                            String line;
                            while ((line = reader.readLine()) != null){
                                builder.append(line);
                            }
                        }
                        String key = FilenameUtils.removeExtension(entry.getName());
                        String contentString = new String(builder.toString().getBytes(), Charset.forName("UTF-8"));

                        if (clazz == String.class) {
                            String[] splitted = key.split("/");
                            put(splitted[splitted.length - 1], (V) contentString);
                        }else{
                            V content = objectMapper.readValue(contentString, clazz);
                            if (content instanceof GeneralDescription) {
                                GeneralDescription d = (GeneralDescription) content;
                                log.info("Loaded element. Key: {}, Value: {}", key, content);
                                put(d.getId(), content);
                            } else {
                                throw new RuntimeException(path + " cannot be loaded. Unknown data type.");
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadFromFile(File root, Class<V> clazz) {
        log.info("Loading elements from file.");
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
                        throw new RuntimeException(path + " cannot be loaded. Unknown data type.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void init();
}
