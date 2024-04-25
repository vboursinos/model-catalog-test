package configuration;

import database.dtoentitymapper.ModelCatalogDtoEntityMapperPackage;
import database.entity.ModelCatalogEntityPackage;
import database.repository.ModelCatalogRepositoryPackage;
import database.service.ModelCatalogServicePackage;
import migration_files_creator.MigrationFilesCreatorPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(
    basePackageClasses = {
      ModelCatalogRepositoryPackage.class,
      MigrationFilesCreatorPackage.class,
      ModelCatalogServicePackage.class,
      ModelCatalogDtoEntityMapperPackage.class
    })
@EnableJpaRepositories(basePackageClasses = {ModelCatalogRepositoryPackage.class})
@EntityScan(basePackageClasses = {ModelCatalogEntityPackage.class})
public class PackageScanningConfig {}
