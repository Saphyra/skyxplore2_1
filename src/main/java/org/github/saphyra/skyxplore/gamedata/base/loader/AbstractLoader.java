package org.github.saphyra.skyxplore.gamedata.base.loader;

import org.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import org.github.saphyra.skyxplore.gamedata.base.ContentLoader;
import org.github.saphyra.skyxplore.gamedata.base.TypedItem;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.GeneralDescription;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractLoader<T> implements ContentLoader {
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected final Class<T> clazz;

    void putGeneralDescription(T content, AbstractGameDataService<T> gameDataService, String path) {
        if (content instanceof GeneralDescription) {
            GeneralDescription d = (GeneralDescription) content;
            log.debug("Loaded element. Key: {}, Value: {}", d.getId(), content);
            gameDataService.put(d.getId(), content);
        } else {
            throw new RuntimeException(path + " cannot be loaded. Unknown data type.");
        }
    }

    String getClassName() {
        return clazz.getSimpleName().toLowerCase();
    }

    boolean isTypeMatches(TypedItem typedItem) {
        return getClassName().equals(typedItem.getType().toLowerCase());
    }
}
