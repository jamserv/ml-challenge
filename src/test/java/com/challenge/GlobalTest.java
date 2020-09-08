package com.challenge;

import com.challenge.helper.CleanUpTest;
import com.challenge.modularTest.ItemTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@RunWith(Suite.class)
@Sql(scripts = "classpath:schema.sql") // to create DB tables and init sample DB data
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.integration.properties")
@Suite.SuiteClasses({
    CleanUpTest.class,
    ItemTest.class
})
public class GlobalTest {
}
