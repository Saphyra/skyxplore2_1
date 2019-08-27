package com.github.saphyra.skyxplore.userdata.settings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettingsEntity, UserSettingsKeyEntity> {
}
