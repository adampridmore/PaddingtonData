package com.masternaut.repository;

import com.masternaut.BaseSystemRepository;
import com.masternaut.domain.SystemSettings;
import org.springframework.data.mongodb.core.MongoTemplate;

public class SystemSettingsRepository extends BaseSystemRepository<SystemSettings>{
    public SystemSettingsRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate, SystemSettings.class);
    }
}
