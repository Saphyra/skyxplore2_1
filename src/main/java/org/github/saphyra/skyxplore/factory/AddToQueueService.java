package org.github.saphyra.skyxplore.factory;

import org.github.saphyra.skyxplore.factory.domain.AddToQueueRequest;

public interface AddToQueueService {
    void addToQueue(String characterId, AddToQueueRequest request);
}
