package com.github.saphyra.skyxplore.userdata.factory;

import com.github.saphyra.skyxplore.userdata.factory.domain.AddToQueueRequest;

public interface AddToQueueService {
    void addToQueue(String characterId, AddToQueueRequest request);
}
