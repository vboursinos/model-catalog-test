package configuration;

import ai.turintech.components.jpa.search.JpaSearchPackage;import ai.turintech.components.jpa.search.data.entity.JpaSearchEntityPackage;import ai.turintech.components.jpa.search.repository.JpaSearchRepositoryPackage;import ai.turintech.modelcatalog.dtoentitymapper.ModelCatalogDtoEntityMapperPackage;
import ai.turintech.modelcatalog.entity.ModelCatalogEntityPackage;
import ai.turintech.modelcatalog.repository.ModelCatalogRepositoryPackage;
import ai.turintech.modelcatalog.service.ModelCatalogServicePackage;import migration_files_creator.MigrationFilesCreatorPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(
        basePackageClasses = {
                ModelCatalogRepositoryPackage.class,
                MigrationFilesCreatorPackage.class,
                ModelCatalogDtoEntityMapperPackage.class,
                ModelCatalogServicePackage.class,
                JpaSearchPackage.class
        })
@EnableJpaRepositories(
        basePackageClasses = {JpaSearchRepositoryPackage.class, ModelCatalogRepositoryPackage.class})
@EntityScan(basePackageClasses = {ModelCatalogEntityPackage.class, JpaSearchEntityPackage.class})
@PropertySource(value = {"/configuration.properties", "/application.properties"})
public class PackageScanningConfig {}
