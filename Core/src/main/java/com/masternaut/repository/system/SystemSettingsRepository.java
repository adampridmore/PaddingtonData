package com.masternaut.repository.system;

import com.masternaut.domain.SystemSettings;
import com.masternaut.repository.BaseSystemRepository;
import org.springframework.stereotype.Component;

@Component
public class SystemSettingsRepository extends BaseSystemRepository<SystemSettings>{
    public SystemSettingsRepository() {
        super(SystemSettings.class);
    }
}
