package skyxplore.dataaccess.gamedata.subservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonDataQueryService extends AbstractGameDataService<GeneralDescription> {
    private final List<AbstractGameDataService<? extends GeneralDescription>> services;

    @Override
    @PostConstruct
    public void init() {
        services.forEach(this::loadItems);
    }

    private void loadItems(AbstractGameDataService<? extends GeneralDescription> service) {
        service.entrySet().forEach(entry ->{
            log.info("Loaded item: {}", entry.toString());
            put(entry.getKey(), entry.getValue());
        });
    }

    public List<String> getItemsOfCategory(String categoryId) {
        return values().stream()
            .filter(generalDescription -> generalDescription.getCategory().equals(categoryId))
            .map(GeneralDescription::getId)
            .collect(Collectors.toList());
    }
}
