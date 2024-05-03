package ai.turintech.modelcatalog.migrationfilescreator.configuration;

import ai.turintech.components.architecture.ArchitectureServicePackage;
import ai.turintech.components.jpa.search.JpaSearchPackage;
import ai.turintech.modelcatalog.dtoentitymapper.ModelCatalogDtoEntityMapperPackage;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.QueryCreatorPackage;
import ai.turintech.modelcatalog.migrationfilescreator.service.ServicePackage;
import ai.turintech.modelcatalog.repository.ModelCatalogRepositoryPackage;
import ai.turintech.modelcatalog.service.ModelCatalogServicePackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    basePackageClasses = {
      ModelCatalogRepositoryPackage.class,
      ModelCatalogDtoEntityMapperPackage.class,
      ModelCatalogServicePackage.class,
      JpaSearchPackage.class,
      ArchitectureServicePackage.class,
      /* Configuration for the Model Catalog Migration File Generator*/
      QueryCreatorPackage.class,
      ServicePackage.class
    })
public class PackageScanningConfig {}
