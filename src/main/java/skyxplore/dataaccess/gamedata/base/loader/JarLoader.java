package skyxplore.dataaccess.gamedata.base.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FilenameUtils;

import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;

@SuppressWarnings({"unchecked", "WeakerAccess"})
@Slf4j
public class JarLoader<T> extends AbstractLoader<T> {
    private final Class<T> clazz;
    private final String jarPath;
    private final AbstractGameDataService<T> gameDataService;

    public JarLoader(Class<T> clazz, AbstractGameDataService<T> gameDataService) {
        this.clazz = clazz;
        this.gameDataService = gameDataService;
        this.jarPath = gameDataService.getJarPath();
    }


    @Override
    public void load() {
        log.info("Loading from JAR... JarPath: {}", jarPath);

        JarFile jarFile = getJarEntries();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            loadJarEntry(jarFile, entries.nextElement());
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

    private void loadJarEntry(JarFile jarFile, JarEntry entry) {
        String entryName = entry.getName();
        try {
            if (entryName.startsWith(jarPath) && entryName.endsWith(".json")) {
                log.info("Matched element: {}", entryName);
                String contentString = readJarEntry(jarFile, entry);
                if (clazz == String.class) {
                    String[] splitted = FilenameUtils.removeExtension(entry.getName()).split("/");
                    gameDataService.put(splitted[splitted.length - 1], (T) contentString);
                } else {
                    T content = objectMapper.readValue(contentString, clazz);
                    putGeneralDescription(content, gameDataService, jarPath);
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
}
