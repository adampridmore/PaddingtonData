package com.masternaut.repository;

import com.masternaut.factory.RepositoryFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/UnitTest-context.xml")
public abstract class BaseSystemRepositoryTest {
    @Autowired
    protected RepositoryFactory repositoryFactory;
}
