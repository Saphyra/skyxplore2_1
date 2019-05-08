package com.github.saphyra.skyxplore.gamedata.base.loader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

class FileUtil {
    static <T> T readValue(File source, Class<T> clazz) {
        try {
            return AbstractLoader.objectMapper.readValue(source, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static <T> T readValue(String json, Class<T> clazz) {
        try {
            return AbstractLoader.objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String readFileToString(File file) {
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
