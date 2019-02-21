package skyxplore.dataaccess.gamedata.base.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.base.ContentLoader;
import skyxplore.dataaccess.gamedata.base.TypedItem;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

@SuppressWarnings("WeakerAccess")
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractLoader<T> implements ContentLoader {
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected final Class<T> clazz;

    protected void putGeneralDescription(T content, AbstractGameDataService<T> gameDataService, String path) {
        if (content instanceof GeneralDescription) {
            GeneralDescription d = (GeneralDescription) content;
            log.debug("Loaded element. Key: {}, Value: {}", d.getId(), content);
            gameDataService.put(d.getId(), content);
        } else {
            throw new RuntimeException(path + " cannot be loaded. Unknown data type.");
        }
    }

    protected String getClassName() {
        return clazz.getSimpleName().toLowerCase();
    }

    protected boolean isTypeMatches(TypedItem typedItem) {
        return getClassName().equals(typedItem.getType().toLowerCase());
    }
}
