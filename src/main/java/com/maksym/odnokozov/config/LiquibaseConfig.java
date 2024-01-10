package com.maksym.odnokozov.config;

import java.util.Map;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfig {

  private static final String PARAMETER_ROOT_EMAIL = "rootEmail";
  private static final String PARAMETER_ROOT_PASSWORD_HASH = "rootPasswordHash";
  private static final String CHANGELOG_PATH = "classpath:/db/changelog/db.changelog-master.yaml";

  @Value("${app.root-email}")
  private String rootEmail;

  @Value("${app.root-password-hash}")
  private String rootPasswordHash;

  @Bean
  public SpringLiquibase liquibase(DataSource dataSource) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog(CHANGELOG_PATH);

    var parameters =
        Map.of(PARAMETER_ROOT_EMAIL, rootEmail, PARAMETER_ROOT_PASSWORD_HASH, rootPasswordHash);

    liquibase.setChangeLogParameters(parameters);

    return liquibase;
  }
}
