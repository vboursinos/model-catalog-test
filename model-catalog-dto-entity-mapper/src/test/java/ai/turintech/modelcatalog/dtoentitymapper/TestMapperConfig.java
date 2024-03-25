package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.EnableIgvMap;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {ModelCatalogDtoEntityMapperPackage.class})
@EnableIgvMap
public class TestMapperConfig {}
