package ai.turintech.modelcatalog.migrationfilescreator.configuration;

import java.util.concurrent.Executor;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties({LiquibaseProperties.class})
public class LiquibaseConfig {

  private final Logger log = LoggerFactory.getLogger(LiquibaseConfig.class);

  private final Environment env;

  public LiquibaseConfig(Environment env) {
    this.env = env;
  }

  @Bean
  public SpringLiquibase liquibase(
      @Qualifier("taskExecutor") Executor executor,
      LiquibaseProperties liquibaseProperties,
      @Autowired DataSource dataSource) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog("classpath:config/liquibase/master.xml");
    liquibase.setContexts(liquibaseProperties.getContexts());
    liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
    liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
    liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
    liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
    liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
    liquibase.setDropFirst(liquibaseProperties.isDropFirst());
    liquibase.setLabelFilter(liquibaseProperties.getLabelFilter());
    liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
    liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
    liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());

    liquibase.setShouldRun(liquibaseProperties.isEnabled());
    log.debug("Configuring Liquibase");

    return liquibase;
  }
}
