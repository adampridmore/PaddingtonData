package com.masternaut.repository.system;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.domain.SystemSettings;
import com.masternaut.repository.BaseSystemRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import static com.masternaut.PaddingtonDatabase.*;

@PaddingtonDatabase(type = DatabaseType.System)
public class SystemSettingsRepository extends BaseSystemRepository<SystemSettings>{
    public SystemSettingsRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate, SystemSettings.class);
    }
}
