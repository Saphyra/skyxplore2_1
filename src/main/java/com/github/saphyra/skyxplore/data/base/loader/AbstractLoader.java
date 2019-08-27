package com.github.saphyra.skyxplore.data.base.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.ContentLoader;
import com.github.saphyra.skyxplore.data.base.TypedItem;
import com.github.saphyra.skyxplore.data.gamedata.entity.GeneralDescription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static org.apache.commons.io.FilenameUtils.removeExtension;

@Slf4j
@RequiredArgsConstructor
abstract class AbstractLoader<T> implements ContentLoader {
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected final Class<T> clazz;

    void putGeneralDescription(T content, AbstractDataService<T> dataService, String fileName) {
        if (content instanceof GeneralDescription) {
            GeneralDescription d = (GeneralDescription) content;
            log.debug("Loaded element. Key: {}, Value: {}", d.getId(), content);
            dataService.put(d.getId(), content);
        } else {
            String itemId = removeExtension(fileName);
            log.debug("ItemId: {}", itemId);
            dataService.put(itemId, content);
        }
    }

    String getClassName() {
        return clazz.getSimpleName().toLowerCase();
    }

    boolean isTypeMatches(TypedItem typedItem) {
        return Optional.ofNullable(typedItem.getType())
            .map(String::toLowerCase)
            .filter(type -> getClassName().equals(type))
            .isPresent();
    }
}
