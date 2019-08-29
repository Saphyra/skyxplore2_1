package com.github.saphyra.selenium.logic.util;

import com.github.saphyra.selenium.logic.domain.localization.ErrorCodes;
import com.github.saphyra.selenium.logic.domain.localization.StringStringMap;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static com.github.saphyra.selenium.SeleniumTestApplication.OBJECT_MAPPER;
import static java.util.Objects.isNull;
import static org.apache.commons.io.FilenameUtils.removeExtension;

@UtilityClass
@Slf4j
public class ErrorCodeLoader {
    private static final String RESOURCE_PATH = "public/i18n/error_code/";
    private static final String BASE_PATH = "src/main/resources/";
    private static final File BASE_DIR = new File(BASE_PATH + RESOURCE_PATH);

    public static ErrorCodes loadErrorCodes() {
        File[] errorCodeFiles = BASE_DIR.listFiles(new JsonFileFilter());
        log.info("errorCodeFiles: " + Arrays.toString(errorCodeFiles));
        if (isNull(errorCodeFiles)) {
            throw new RuntimeException("ErrorCode localization not found");
        }

        ErrorCodes result = new ErrorCodes();
        for (File errorCodeFile : errorCodeFiles) {
            log.info("Loading errorCodeFile {}", errorCodeFile.getName());
            result.put(removeExtension(errorCodeFile.getName()), readFile(errorCodeFile));
        }
        return result;
    }

    private static StringStringMap readFile(File errorCodeFile) {
        URL source = PageLocalizationLoader.class.getClassLoader().getResource(RESOURCE_PATH + errorCodeFile.getName());
        try {
            return OBJECT_MAPPER.readValue(source, StringStringMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class JsonFileFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            return pathname.isFile() && FilenameUtils.isExtension(pathname.getName(), "json");
        }
    }
}
