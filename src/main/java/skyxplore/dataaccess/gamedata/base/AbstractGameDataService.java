package skyxplore.dataaccess.gamedata.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@SuppressWarnings({"unchecked", "ConstantConditions"})
@Slf4j
//TODO refactor
public abstract class AbstractGameDataService<V> extends HashMap<String, V> {
    private static final String RESOURCES_DIR = "data/gamedata/";
    private static final String BASE_DIR = "src/main/resources/" + RESOURCES_DIR;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonFileFilter jsonFilter = new JsonFileFilter();

    private final String path;
    private final String jarPath;

    public AbstractGameDataService(String source) {
        this.jarPath = RESOURCES_DIR + source;
        this.path = BASE_DIR + source;
    }

    protected void load(Class<V> clazz) {
        File root = new File(path);
        if (!root.exists()) {
            loadFromJar(clazz);
        } else {
            loadFromFile(root, clazz);
        }
    }

    private void loadFromJar(Class<V> clazz) {
        log.info("Loading from JAR... JarPath: {}", jarPath);

        JarFile jarFile = getJarEntries();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            loadJarEntry(clazz, jarFile, entries.nextElement());
        }
    }

    private JarFile getJarEntries() {
        try {
            CodeSource src = AbstractGameDataService.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                JarURLConnection urlcon = (JarURLConnection) (jar.openConnection());
                return urlcon.getJarFile();
            } else {
                throw new IllegalStateException("CodeSource cannot be resolved");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadJarEntry(Class<V> clazz, JarFile jarFile, JarEntry entry) {
        String entryName = entry.getName();
        try {
            if (entryName.startsWith(jarPath) && entryName.endsWith(".json")) {
                log.info("Matched element: {}", entryName);
                String contentString = readJarEntry(jarFile, entry);
                if (clazz == String.class) {
                    String[] splitted = FilenameUtils.removeExtension(entry.getName()).split("/");
                    put(splitted[splitted.length - 1], (V) contentString);
                } else {
                    V content = objectMapper.readValue(contentString, clazz);
                    putGeneralDescription(content);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readJarEntry(JarFile jarFile, JarEntry entry) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(entry)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }

        return new String(builder.toString().getBytes(), Charset.forName("UTF-8"));
    }

    private void loadFromFile(File root, Class<V> clazz) {
        log.info("Loading elements from file.");
        if (!root.isDirectory()) {
            throw new IllegalArgumentException("Source must be a directory. Path: " + root.getAbsolutePath());
        }
        File[] files = root.listFiles(jsonFilter);
        for (File file : files) {
            loadFile(clazz, file);
        }
    }

    private void loadFile(Class<V> clazz, File file) {
        try {
            if (clazz == String.class) {
                String key = FilenameUtils.removeExtension(file.getName());
                put(key, (V) FileUtils.readFileToString(file));
            } else {
                parseFile(clazz, file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseFile(Class<V> clazz, File file) throws IOException {
        V content = objectMapper.readValue(file, clazz);
        putGeneralDescription(content);
    }

    private void putGeneralDescription(V content) {
        if (content instanceof GeneralDescription) {
            GeneralDescription d = (GeneralDescription) content;
            log.info("Loaded element. Key: {}, Value: {}", d.getId(), content);
            put(d.getId(), content);
        } else {
            throw new RuntimeException(path + " cannot be loaded. Unknown data type.");
        }
    }

    public abstract void init();
}
