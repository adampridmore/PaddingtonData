package com.masternaut.repository.system;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.domain.SystemSettings;
import com.masternaut.repository.BaseSystemRepository;
import org.springframework.stereotype.Component;

import static com.masternaut.PaddingtonDatabase.DatabaseType;

@PaddingtonDatabase(type = DatabaseType.System)
@Component
public class SystemSettingsRepository extends BaseSystemRepository<SystemSettings>{
    public SystemSettingsRepository() {
        super(SystemSettings.class);
    }
}
