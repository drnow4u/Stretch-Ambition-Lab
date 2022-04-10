package com.gitlab.marnow.stretchambitionlab.springpostgresbase;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class SpringPostgresApplicationTests {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = PostgresqlContainer.getInstance();

    @Test
    void contextLoads() {
    }

}
