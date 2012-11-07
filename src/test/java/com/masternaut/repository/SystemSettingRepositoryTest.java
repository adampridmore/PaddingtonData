package com.masternaut.repository;

import com.masternaut.domain.SystemSettings;
import com.masternaut.factory.RepositoryFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/UnitTest-context.xml")
public class SystemSettingRepositoryTest {
    @Autowired
    private RepositoryFactory repositoryFactory;

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

        systemSettingsRepository.findById(systemSettings.getId());
    }
}
