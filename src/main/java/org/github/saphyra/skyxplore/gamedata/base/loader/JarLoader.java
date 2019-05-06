package org.github.saphyra.skyxplore.gamedata.base.loader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import org.github.saphyra.skyxplore.gamedata.base.TypedItem;

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

import static java.util.Objects.isNull;

@Slf4j
class JarLoader<T> extends AbstractLoader<T> {
    private final String jarPath;
    private final AbstractGameDataService<T> gameDataService;

    JarLoader(Class<T> clazz, AbstractGameDataService<T> gameDataService) {
        super(clazz);
        this.gameDataService = gameDataService;
        this.jarPath = gameDataService.getJarPath();
    }

    @Override
    public void load() {
        log.debug("Loading from JAR... JarPath: {}", jarPath);

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

    @SuppressWarnings("unchecked")
    private void loadJarEntry(JarFile jarFile, JarEntry entry) {
        String entryName = entry.getName();
        if (entryName.startsWith(jarPath) && entryName.endsWith(".json")) {
            TypedItem typedItem = getTypedItem(jarFile, entry);
            if (isTypeMatches(typedItem)) {
                log.debug("Matched element: {}", entryName);
                String contentString = readJarEntry(jarFile, entry);
                if (clazz == String.class) {
                    String[] splitted = FilenameUtils.removeExtension(entry.getName()).split("/");
                    gameDataService.put(splitted[splitted.length - 1], (T) contentString);
                } else {
                    T content = FileUtil.readValue(contentString, clazz);
                    putGeneralDescription(content, gameDataService, jarPath);
                }
            } else {
                log.debug("Skipping {}, it is not the type of {}, it is a {}", entryName, getClassName(), typedItem.getType());
            }

        }
    }

    private TypedItem getTypedItem(JarFile jarFile, JarEntry entry) {
        String json = readJarEntry(jarFile, entry);
        TypedItem typedItem = FileUtil.readValue(json, TypedItem.class);
        if (isNull(typedItem.getType())) {
            log.warn("{} has no type.", entry.getName());
        }
        return typedItem;
    }

    private String readJarEntry(JarFile jarFile, JarEntry entry) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(entry)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new String(builder.toString().getBytes(), Charset.forName("UTF-8"));
    }
}
