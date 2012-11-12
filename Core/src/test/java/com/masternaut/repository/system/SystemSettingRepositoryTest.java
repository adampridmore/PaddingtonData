package com.masternaut.repository.system;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.domain.SystemSettings;
import com.masternaut.repository.BaseSystemRepositoryTest;
import org.junit.Before;
import org.junit.Test;

import static com.mongodb.util.MyAsserts.assertEquals;

@PaddingtonDatabase(type = PaddingtonDatabase.DatabaseType.System)
public class SystemSettingRepositoryTest extends BaseSystemRepositoryTest {

    private SystemSettingsRepository systemSettingsRepository;

    @Before
    public void before() {
        systemSettingsRepository = repositoryFactory.createRepository(SystemSettingsRepository.class);
        systemSettingsRepository.deleteAll();
    }

    @Test
    public void loadAndSave(){
        SystemSettings systemSettings = new SystemSettings();
        systemSettings.setSystemName("MySystemName");

        systemSettingsRepository.save(systemSettings);

        SystemSettings loadedSystemSettings = systemSettingsRepository.findById(systemSettings.getId());
        assertEquals(systemSettings.getId(), loadedSystemSettings.getId());
        assertEquals("MySystemName", loadedSystemSettings.getSystemName());
    }
}
