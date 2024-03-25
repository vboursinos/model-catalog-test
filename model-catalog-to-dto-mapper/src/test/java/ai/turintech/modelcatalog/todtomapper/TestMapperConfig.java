package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.EnableIgvMap;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {ModelCatalogToDtoMapperPackage.class})
@EnableIgvMap
public class TestMapperConfig {}
