package skyxplore.dataaccess.gamedata.base;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;

public class JsonFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
        return FilenameUtils.isExtension(file.getName(), "json");
    }
}
