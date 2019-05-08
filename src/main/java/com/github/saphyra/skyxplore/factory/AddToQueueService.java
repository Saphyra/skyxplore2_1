package com.github.saphyra.skyxplore.factory;

import com.github.saphyra.skyxplore.factory.domain.AddToQueueRequest;

public interface AddToQueueService {
    void addToQueue(String characterId, AddToQueueRequest request);
}
