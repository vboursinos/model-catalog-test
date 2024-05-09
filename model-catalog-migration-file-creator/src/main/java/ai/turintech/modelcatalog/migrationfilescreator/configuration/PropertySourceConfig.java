package ai.turintech.modelcatalog.migrationfilescreator.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:configuration.properties", "classpath:application.properties"})
public class PropertySourceConfig {}
