package com.github.saphyra.skyxplore.data.base.loader;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.TypedItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

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
    private final AbstractDataService<T> dataService;

    JarLoader(Class<T> clazz, AbstractDataService<T> dataService) {
        super(clazz);
        this.dataService = dataService;
        this.jarPath = dataService.getJarPath();
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
            CodeSource src = AbstractDataService.class.getProtectionDomain().getCodeSource();
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
            if (!dataService.isShouldCheckType() || isTypeMatches(typedItem)) {
                log.debug("Matched element: {}", entryName);
                String contentString = readJarEntry(jarFile, entry);
                if (clazz == String.class) {
                    String[] splitted = FilenameUtils.removeExtension(entry.getName()).split("/");
                    dataService.put(splitted[splitted.length - 1], (T) contentString);
                } else {
                    T content = FileUtil.readValue(contentString, clazz);
                    putGeneralDescription(content, dataService, entryName.substring(jarPath.length()));
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
